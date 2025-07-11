package com.myfarm.myfarm.adapter.`in`.web.productreviews

import com.myfarm.myfarm.adapter.`in`.web.config.security.MyfarmAuthentication
import com.myfarm.myfarm.adapter.`in`.web.productreviews.message.CreateProductReview
import com.myfarm.myfarm.adapter.`in`.web.productreviews.message.DeleteProductReview
import com.myfarm.myfarm.adapter.`in`.web.productreviews.message.GetProductReview
import com.myfarm.myfarm.adapter.`in`.web.productreviews.message.ListProductReview
import com.myfarm.myfarm.adapter.`in`.web.productreviews.message.UpdateProductReview
import com.myfarm.myfarm.domain.productreviews.service.CreateProductReviewService
import com.myfarm.myfarm.domain.productreviews.service.DeleteProductReviewService
import com.myfarm.myfarm.domain.productreviews.service.GetProductReviewService
import com.myfarm.myfarm.domain.productreviews.service.ListProductReviewService
import com.myfarm.myfarm.domain.productreviews.service.UpdateProductReviewService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/product-reviews/v1")
class ProductReviewsController(
    private val createProductReviewService: CreateProductReviewService,
    private val getProductReviewService: GetProductReviewService,
    private val updateProductReviewService: UpdateProductReviewService,
    private val deleteProductReviewService: DeleteProductReviewService,
    private val listProductReviewService: ListProductReviewService
) {

    @PostMapping
    fun createProductReview(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        @RequestBody request: CreateProductReview.Request
    ): CreateProductReview.Response {
        return createProductReviewService.createProductReview(myfarmAuth.userId, request)
    }

    @GetMapping("/{id}")
    fun getProductReview(
        request: GetProductReview.PathVariable
    ): GetProductReview.Response {
        return getProductReviewService.getProductReview(request.id)
    }

    @PutMapping("/{id}")
    fun updateProductReview(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        request: UpdateProductReview.PathVariable,
        @RequestBody updateRequest: UpdateProductReview.Request
    ): UpdateProductReview.Response {
        return updateProductReviewService.updateProductReview(myfarmAuth.userId, request.id, updateRequest)
    }

    @DeleteMapping("/{id}")
    fun deleteProductReview(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        request: DeleteProductReview.PathVariable
    ): DeleteProductReview.Response {
        return deleteProductReviewService.deleteProductReview(myfarmAuth.userId, request.id)
    }

    @GetMapping
    fun listProductReviews(
        request: ListProductReview.Request
    ): ListProductReview.Response {
        return listProductReviewService.listProductReviews(request)
    }
}
