package com.myfarm.myfarm.domain.products.service

import com.myfarm.myfarm.adapter.`in`.web.products.message.CreateProduct
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
class CreateProductService(
    private val productsRepository: ProductsRepository,
    private val productImagesRepository: ProductImagesRepository,
    private val productOptionsRepository: ProductOptionsRepository,
    private val productAttributesRepository: ProductAttributesRepository,
    private val productTagsRepository: ProductTagsRepository,
    private val categoriesRepository: CategoriesRepository,
    private val fileUploadService: FileUploadService
) {

    @Transactional
    fun createProduct(userId: UUID, request: CreateProduct.Request): CreateProduct.Response {
        if (request.name.isBlank()) {
            throw IllegalArgumentException("상품명은 필수입니다")
        }

        if (request.price < 0) {
            throw IllegalArgumentException("가격은 0원 이상이어야 합니다")
        }

        categoriesRepository.findById(request.categoryId)
            ?: throw IllegalArgumentException("존재하지 않는 카테고리입니다")

        val defaultOptionCount = request.options.count { it.isDefault }
        if (defaultOptionCount > 1) {
            throw IllegalArgumentException("기본 옵션은 1개만 설정할 수 있습니다")
        }

        val now = LocalDateTime.now()
        val productId = UUID.randomUUID()

        // 파일 업로드 처리
        val uploadedImages = if (request.imageFiles.isNotEmpty()) {
            request.imageFiles.mapIndexed { index, file ->
                val imageUrl = fileUploadService.uploadProductImage(file, productId)
                CreateProduct.ProductImageInfo(
                    imageUrl = imageUrl,
                    isThumbnail = index == 0, // 첫 번째 이미지를 썸네일로 설정
                    sortOrder = index
                )
            }
        } else {
            emptyList()
        }

        val product = Products(
            id = productId,
            name = request.name,
            description = request.description,
            price = request.price,
            status = request.status,
            sellerId = userId,
            categoryId = request.categoryId,
            thumbnailUrl = uploadedImages.firstOrNull()?.imageUrl,
            origin = request.origin,
            harvestDate = request.harvestDate,
            storageMethod = request.storageMethod,
            isOrganic = request.isOrganic,
            createdAt = now,
            updatedAt = now
        )

        val savedProduct = productsRepository.save(product)

        // 업로드된 이미지들을 DB에 저장
        if (uploadedImages.isNotEmpty()) {
            val productImages = uploadedImages.map { imageInfo ->
                ProductImages(
                    id = UUID.randomUUID(),
                    productId = productId,
                    imageUrl = imageInfo.imageUrl,
                    isThumbnail = imageInfo.isThumbnail,
                    sortOrder = imageInfo.sortOrder,
                    createdAt = now
                )
            }
            productImagesRepository.saveAll(productImages)
        }

        if (request.options.isNotEmpty()) {
            val productOptions = request.options.map { optionRequest ->
                ProductOptions(
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
            }
            productOptionsRepository.saveAll(productOptions)
        }

        if (request.attributes.isNotEmpty()) {
            val productAttributes = request.attributes.map { attributeRequest ->
                ProductAttributes(
                    id = UUID.randomUUID(),
                    productId = productId,
                    attributeName = attributeRequest.attributeName,
                    attributeValue = attributeRequest.attributeValue,
                    createdAt = now
                )
            }
            productAttributesRepository.saveAll(productAttributes)
        }

        if (request.tags.isNotEmpty()) {
            val productTags = request.tags.map { tag ->
                ProductTags(
                    id = UUID.randomUUID(),
                    productId = productId,
                    tag = tag,
                    createdAt = now
                )
            }
            productTagsRepository.saveAll(productTags)
        }

        return CreateProduct.Response(
            success = true,
            productId = savedProduct.id
        )
    }
}
