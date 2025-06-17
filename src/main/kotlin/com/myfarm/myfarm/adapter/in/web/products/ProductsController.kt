package com.myfarm.myfarm.adapter.`in`.web.products

import com.myfarm.myfarm.adapter.`in`.web.config.security.MyfarmAuthentication
import com.myfarm.myfarm.adapter.`in`.web.products.message.CreateProduct
import com.myfarm.myfarm.adapter.`in`.web.products.message.DeleteProduct
import com.myfarm.myfarm.adapter.`in`.web.products.message.GetProduct
import com.myfarm.myfarm.adapter.`in`.web.products.message.ListProduct
import com.myfarm.myfarm.adapter.`in`.web.products.message.UpdateProduct
import com.myfarm.myfarm.domain.common.checkAdminPermission
import com.myfarm.myfarm.domain.products.service.CreateProductService
import com.myfarm.myfarm.domain.products.service.DeleteProductService
import com.myfarm.myfarm.domain.products.service.GetProductService
import com.myfarm.myfarm.domain.products.service.ListProductService
import com.myfarm.myfarm.domain.products.service.UpdateProductService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/products/v1")
class ProductsController(
    private val createProductService: CreateProductService,
    private val getProductService: GetProductService,
    private val listProductService: ListProductService,
    private val updateProductService: UpdateProductService,
    private val deleteProductService: DeleteProductService
) {

    @PostMapping
    fun createProduct(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        @RequestBody request: CreateProduct.Request
    ): CreateProduct.Response {
        myfarmAuth.checkAdminPermission()
        return createProductService.createProduct(request, myfarmAuth.userId)
    }

    @GetMapping("/{id}")
    fun getProduct(
        request: GetProduct.PathVariable
    ): GetProduct.Response {
        return getProductService.getProduct(request.id)
    }

    @GetMapping
    fun listProducts(
        request: ListProduct.Request
    ): ListProduct.Response {
        return listProductService.listProducts(request)
    }

    @PutMapping("/{id}")
    fun updateProduct(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        request: UpdateProduct.PathVariable,
        @RequestBody updateRequest: UpdateProduct.Request
    ): UpdateProduct.Response {
        myfarmAuth.checkAdminPermission()
        return updateProductService.updateProduct(request.id, updateRequest)
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        request: DeleteProduct.PathVariable
    ): DeleteProduct.Response {
        myfarmAuth.checkAdminPermission()
        return deleteProductService.deleteProduct(request.id)
    }
}
