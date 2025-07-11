package com.myfarm.myfarm.domain.products.service

import com.myfarm.myfarm.adapter.`in`.web.products.message.CreateProduct
import com.myfarm.myfarm.domain.categories.port.CategoriesRepository
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
    private val categoriesRepository: CategoriesRepository
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

        val thumbnailCount = request.images.count { it.isThumbnail }
        if (thumbnailCount > 1) {
            throw IllegalArgumentException("썸네일 이미지는 1개만 설정할 수 있습니다")
        }

        val defaultOptionCount = request.options.count { it.isDefault }
        if (defaultOptionCount > 1) {
            throw IllegalArgumentException("기본 옵션은 1개만 설정할 수 있습니다")
        }

        val now = LocalDateTime.now()
        val productId = UUID.randomUUID()

        val product = Products(
            id = productId,
            name = request.name,
            description = request.description,
            price = request.price,
            status = request.status,
            sellerId = userId,
            categoryId = request.categoryId,
            thumbnailUrl = findThumbnailUrl(request.images),
            origin = request.origin,
            harvestDate = request.harvestDate,
            storageMethod = request.storageMethod,
            isOrganic = request.isOrganic,
            createdAt = now,
            updatedAt = now
        )

        val savedProduct = productsRepository.save(product)

        if (request.images.isNotEmpty()) {
            val productImages = request.images.mapIndexed { index, imageRequest ->
                ProductImages(
                    id = UUID.randomUUID(),
                    productId = productId,
                    imageUrl = imageRequest.imageUrl,
                    isThumbnail = imageRequest.isThumbnail,
                    sortOrder = imageRequest.sortOrder.takeIf { it > 0 } ?: index,
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

    private fun findThumbnailUrl(images: List<CreateProduct.ProductImageRequest>): String? {
        return images.firstOrNull { it.isThumbnail }?.imageUrl
            ?: images.firstOrNull()?.imageUrl
    }
}
