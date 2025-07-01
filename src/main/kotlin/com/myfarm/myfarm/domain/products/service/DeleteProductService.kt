package com.myfarm.myfarm.domain.products.service

import com.myfarm.myfarm.adapter.`in`.web.products.message.DeleteProduct
import com.myfarm.myfarm.domain.common.service.FileUploadService
import com.myfarm.myfarm.domain.productattributes.port.ProductAttributesRepository
import com.myfarm.myfarm.domain.productimages.port.ProductImagesRepository
import com.myfarm.myfarm.domain.productoptions.port.ProductOptionsRepository
import com.myfarm.myfarm.domain.products.port.ProductsRepository
import com.myfarm.myfarm.domain.producttags.port.ProductTagsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class DeleteProductService(
    private val productsRepository: ProductsRepository,
    private val productImagesRepository: ProductImagesRepository,
    private val productOptionsRepository: ProductOptionsRepository,
    private val productAttributesRepository: ProductAttributesRepository,
    private val productTagsRepository: ProductTagsRepository,
    private val fileUploadService: FileUploadService
) {

    @Transactional
    fun deleteProduct(id: UUID): DeleteProduct.Response {
        productsRepository.findById(id)
            ?: throw IllegalArgumentException("존재하지 않는 상품입니다")

        val productImages = productImagesRepository.findByProductId(id)
        productImages.forEach { image ->
            fileUploadService.deleteFile(image.imageUrl)
        }

        productImagesRepository.deleteByProductId(id)
        productOptionsRepository.deleteByProductId(id)
        productAttributesRepository.deleteByProductId(id)
        productTagsRepository.deleteByProductId(id)

        productsRepository.deleteById(id)

        return DeleteProduct.Response(success = true)
    }
}
