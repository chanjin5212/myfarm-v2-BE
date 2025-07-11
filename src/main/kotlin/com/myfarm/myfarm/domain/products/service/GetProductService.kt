package com.myfarm.myfarm.domain.products.service

import com.myfarm.myfarm.adapter.`in`.web.products.message.GetProduct
import com.myfarm.myfarm.domain.productattributes.port.ProductAttributesRepository
import com.myfarm.myfarm.domain.productimages.port.ProductImagesRepository
import com.myfarm.myfarm.domain.productoptions.port.ProductOptionsRepository
import com.myfarm.myfarm.domain.products.port.ProductsRepository
import com.myfarm.myfarm.domain.producttags.port.ProductTagsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class GetProductService(
    private val productsRepository: ProductsRepository,
    private val productImagesRepository: ProductImagesRepository,
    private val productOptionsRepository: ProductOptionsRepository,
    private val productAttributesRepository: ProductAttributesRepository,
    private val productTagsRepository: ProductTagsRepository
) {

    @Transactional(readOnly = true)
    fun getProduct(id: UUID): GetProduct.Response {
        val product = productsRepository.findById(id)
            ?: throw IllegalArgumentException("존재하지 않는 상품입니다")

        val images = productImagesRepository.findByProductId(id)
            .sortedBy { it.sortOrder }
            .map { image ->
                GetProduct.ProductImageInfo(
                    id = image.id,
                    imageUrl = image.imageUrl,
                    isThumbnail = image.isThumbnail,
                    sortOrder = image.sortOrder
                )
            }

        val options = productOptionsRepository.findByProductId(id)
            .map { option ->
                GetProduct.ProductOptionInfo(
                    id = option.id,
                    optionName = option.optionName,
                    optionValue = option.optionValue,
                    additionalPrice = option.additionalPrice,
                    stock = option.stock,
                    isDefault = option.isDefault
                )
            }

        val attributes = productAttributesRepository.findByProductId(id)
            .map { attribute ->
                GetProduct.ProductAttributeInfo(
                    id = attribute.id,
                    attributeName = attribute.attributeName,
                    attributeValue = attribute.attributeValue
                )
            }

        val tags = productTagsRepository.findByProductId(id)
            .map { it.tag }

        return GetProduct.Response(
            id = product.id,
            name = product.name,
            description = product.description,
            price = product.price,
            status = product.status,
            sellerId = product.sellerId,
            categoryId = product.categoryId,
            thumbnailUrl = product.thumbnailUrl,
            origin = product.origin,
            harvestDate = product.harvestDate,
            storageMethod = product.storageMethod,
            isOrganic = product.isOrganic,
            createdAt = product.createdAt,
            updatedAt = product.updatedAt,
            images = images,
            options = options,
            attributes = attributes,
            tags = tags
        )
    }
}
