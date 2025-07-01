package com.myfarm.myfarm.domain.products.service

import com.myfarm.myfarm.adapter.`in`.web.products.message.UpdateProduct
import com.myfarm.myfarm.domain.categories.port.CategoriesRepository
import com.myfarm.myfarm.domain.common.service.FileUploadService
import com.myfarm.myfarm.domain.productattributes.entity.ProductAttributes
import com.myfarm.myfarm.domain.productattributes.port.ProductAttributesRepository
import com.myfarm.myfarm.domain.productimages.entity.ProductImages
import com.myfarm.myfarm.domain.productimages.port.ProductImagesRepository
import com.myfarm.myfarm.domain.productoptions.entity.ProductOptions
import com.myfarm.myfarm.domain.productoptions.port.ProductOptionsRepository
import com.myfarm.myfarm.domain.products.entity.Products
import com.myfarm.myfarm.domain.products.port.ProductsRepository
import com.myfarm.myfarm.domain.producttags.entity.ProductTags
import com.myfarm.myfarm.domain.producttags.port.ProductTagsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class UpdateProductService(
    private val productsRepository: ProductsRepository,
    private val productImagesRepository: ProductImagesRepository,
    private val productOptionsRepository: ProductOptionsRepository,
    private val productAttributesRepository: ProductAttributesRepository,
    private val productTagsRepository: ProductTagsRepository,
    private val categoriesRepository: CategoriesRepository,
    private val fileUploadService: FileUploadService
) {

    @Transactional
    fun updateProduct(id: UUID, request: UpdateProduct.Request): UpdateProduct.Response {
        val existingProduct = productsRepository.findById(id)
            ?: throw IllegalArgumentException("존재하지 않는 상품입니다")

        validateUpdateProductRequest(request)

        val now = LocalDateTime.now()

        val updatedProduct = Products(
            id = existingProduct.id,
            name = request.name,
            description = request.description,
            price = request.price,
            status = request.status,
            sellerId = existingProduct.sellerId,
            categoryId = request.categoryId,
            thumbnailUrl = findThumbnailUrl(request.images),
            origin = request.origin,
            harvestDate = request.harvestDate,
            storageMethod = request.storageMethod,
            isOrganic = request.isOrganic,
            orderCount = existingProduct.orderCount,
            createdAt = existingProduct.createdAt,
            updatedAt = now
        )

        productsRepository.save(updatedProduct)

        // 2. 연관 데이터 업데이트 (id 기반)
        updateProductImages(id, request.images, now)
        updateProductOptions(id, request.options, now)
        updateProductAttributes(id, request.attributes, now)
        updateProductTags(id, request.tags, now)

        return UpdateProduct.Response(success = true)
    }

    private fun validateUpdateProductRequest(request: UpdateProduct.Request) {
        if (request.name.isBlank()) {
            throw IllegalArgumentException("상품명은 필수입니다")
        }

        if (request.price < 0) {
            throw IllegalArgumentException("가격은 0원 이상이어야 합니다")
        }

        // 카테고리 존재 여부 확인
        categoriesRepository.findById(request.categoryId)
            ?: throw IllegalArgumentException("존재하지 않는 카테고리입니다")

        // 썸네일 이미지 검증
        val thumbnailCount = request.images.count { it.isThumbnail }
        if (thumbnailCount > 1) {
            throw IllegalArgumentException("썸네일 이미지는 1개만 설정할 수 있습니다")
        }

        // 기본 옵션 검증
        val defaultOptionCount = request.options.count { it.isDefault }
        if (defaultOptionCount > 1) {
            throw IllegalArgumentException("기본 옵션은 1개만 설정할 수 있습니다")
        }
    }

    private fun findThumbnailUrl(images: List<UpdateProduct.ProductImageRequest>): String? {
        return images.firstOrNull { it.isThumbnail }?.imageUrl
            ?: images.firstOrNull()?.imageUrl
    }

    private fun updateProductImages(
        productId: UUID,
        imageRequests: List<UpdateProduct.ProductImageRequest>,
        now: LocalDateTime
    ) {
        val existingImages = productImagesRepository.findByProductId(productId)
        val requestIds = imageRequests.mapNotNull { it.id }.toSet()
        val existingIds = existingImages.map { it.id }.toSet()

        // 삭제할 이미지들 (요청에 없는 기존 이미지들)
        val imagesToDelete = existingImages.filter { it.id !in requestIds }
        if (imagesToDelete.isNotEmpty()) {
            // Supabase에서 파일 삭제
            imagesToDelete.forEach { image ->
                fileUploadService.deleteFile(image.imageUrl)
            }
            // DB에서 삭제
            productImagesRepository.deleteAllById(imagesToDelete.map { it.id })
        }

        imageRequests.forEach { imageRequest ->
            if (imageRequest.id != null) {
                // 기존 이미지 업데이트
                if (imageRequest.id in existingIds) {
                    val updatedImage = ProductImages(
                        id = imageRequest.id,
                        productId = productId,
                        imageUrl = imageRequest.imageUrl,
                        isThumbnail = imageRequest.isThumbnail,
                        sortOrder = imageRequest.sortOrder,
                        createdAt = existingImages.first { it.id == imageRequest.id }.createdAt
                    )
                    productImagesRepository.save(updatedImage)
                }
            } else {
                // 새 이미지 생성
                val newImage = ProductImages(
                    id = UUID.randomUUID(),
                    productId = productId,
                    imageUrl = imageRequest.imageUrl,
                    isThumbnail = imageRequest.isThumbnail,
                    sortOrder = imageRequest.sortOrder,
                    createdAt = now
                )
                productImagesRepository.save(newImage)
            }
        }
    }

    private fun updateProductOptions(
        productId: UUID,
        optionRequests: List<UpdateProduct.ProductOptionRequest>,
        now: LocalDateTime
    ) {
        val existingOptions = productOptionsRepository.findByProductId(productId)
        val requestIds = optionRequests.mapNotNull { it.id }.toSet()
        val existingIds = existingOptions.map { it.id }.toSet()

        // 삭제할 옵션들
        val optionsToDelete = existingOptions.filter { it.id !in requestIds }
        if (optionsToDelete.isNotEmpty()) {
            productOptionsRepository.deleteAllById(optionsToDelete.map { it.id })
        }

        optionRequests.forEach { optionRequest ->
            if (optionRequest.id != null) {
                // 기존 옵션 업데이트
                if (optionRequest.id in existingIds) {
                    val existingOption = existingOptions.first { it.id == optionRequest.id }
                    val updatedOption = ProductOptions(
                        id = optionRequest.id,
                        productId = productId,
                        optionName = optionRequest.optionName,
                        optionValue = optionRequest.optionValue,
                        additionalPrice = optionRequest.additionalPrice,
                        stock = optionRequest.stock,
                        isDefault = optionRequest.isDefault,
                        createdAt = existingOption.createdAt,
                        updatedAt = now
                    )
                    productOptionsRepository.save(updatedOption)
                }
            } else {
                // 새 옵션 생성
                val newOption = ProductOptions(
                    id = UUID.randomUUID(),
                    productId = productId,
                    optionName = optionRequest.optionName,
                    optionValue = optionRequest.optionValue,
                    additionalPrice = optionRequest.additionalPrice,
                    stock = optionRequest.stock,
                    isDefault = optionRequest.isDefault,
                    createdAt = now,
                    updatedAt = now
                )
                productOptionsRepository.save(newOption)
            }
        }
    }

    private fun updateProductAttributes(
        productId: UUID,
        attributeRequests: List<UpdateProduct.ProductAttributeRequest>,
        now: LocalDateTime
    ) {
        val existingAttributes = productAttributesRepository.findByProductId(productId)
        val requestIds = attributeRequests.mapNotNull { it.id }.toSet()
        val existingIds = existingAttributes.map { it.id }.toSet()

        // 삭제할 속성들
        val attributesToDelete = existingAttributes.filter { it.id !in requestIds }
        if (attributesToDelete.isNotEmpty()) {
            productAttributesRepository.deleteAllById(attributesToDelete.map { it.id })
        }

        attributeRequests.forEach { attributeRequest ->
            if (attributeRequest.id != null) {
                // 기존 속성 업데이트
                if (attributeRequest.id in existingIds) {
                    val updatedAttribute = ProductAttributes(
                        id = attributeRequest.id,
                        productId = productId,
                        attributeName = attributeRequest.attributeName,
                        attributeValue = attributeRequest.attributeValue,
                        createdAt = existingAttributes.first { it.id == attributeRequest.id }.createdAt
                    )
                    productAttributesRepository.save(updatedAttribute)
                }
            } else {
                // 새 속성 생성
                val newAttribute = ProductAttributes(
                    id = UUID.randomUUID(),
                    productId = productId,
                    attributeName = attributeRequest.attributeName,
                    attributeValue = attributeRequest.attributeValue,
                    createdAt = now
                )
                productAttributesRepository.save(newAttribute)
            }
        }
    }

    private fun updateProductTags(
        productId: UUID,
        tagNames: List<String>,
        now: LocalDateTime
    ) {
        val existingTags = productTagsRepository.findByProductId(productId)
        val existingTagNames = existingTags.map { it.tag }.toSet()
        val newTagNames = tagNames.toSet()

        // 삭제할 태그들
        val tagsToDelete = existingTags.filter { it.tag !in newTagNames }
        if (tagsToDelete.isNotEmpty()) {
            productTagsRepository.deleteByIds(tagsToDelete.map { it.id })
        }

        // 새로 추가할 태그들 (태그는 id가 없으므로 기존 방식 유지)
        val tagsToAdd = tagNames.filter { it !in existingTagNames }
        if (tagsToAdd.isNotEmpty()) {
            val productTags = tagsToAdd.map { tag ->
                ProductTags(
                    id = UUID.randomUUID(),
                    productId = productId,
                    tag = tag,
                    createdAt = now
                )
            }
            productTagsRepository.saveAll(productTags)
        }
    }
}
