package com.myfarm.myfarm.domain.productreviews.service

import com.myfarm.myfarm.adapter.`in`.web.productreviews.message.CreateProductReview
import com.myfarm.myfarm.domain.orderitems.port.OrderItemsRepository
import com.myfarm.myfarm.domain.orders.port.OrdersRepository
import com.myfarm.myfarm.domain.productreviews.entity.ProductReviews
import com.myfarm.myfarm.domain.productreviews.port.ProductReviewsRepository
import com.myfarm.myfarm.domain.products.port.ProductsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class CreateProductReviewService(
    private val productReviewsRepository: ProductReviewsRepository,
    private val productsRepository: ProductsRepository,
    private val ordersRepository: OrdersRepository,
    private val orderItemsRepository: OrderItemsRepository
) {

    @Transactional
    fun createProductReview(userId: UUID, request: CreateProductReview.Request): CreateProductReview.Response {
        productsRepository.findById(request.productId)
            ?: throw IllegalArgumentException("존재하지 않는 상품입니다")

        if (request.orderId != null) {
            val order = ordersRepository.findById(request.orderId)
                ?: throw IllegalArgumentException("존재하지 않는 주문입니다")

            if (order.userId != userId) {
                throw IllegalArgumentException("해당 주문에 대한 권한이 없습니다")
            }

            if (order.status !in listOf("delivered", "confirmed")) {
                throw IllegalArgumentException("완료된 주문에 대해서만 리뷰를 작성할 수 있습니다")
            }

            orderItemsRepository.findByOrderIdAndProductId(request.orderId, request.productId)

            val existingOrderReview = productReviewsRepository.findByOrderIdAndProductId(request.orderId, request.productId)
            if (existingOrderReview != null) {
                throw IllegalArgumentException("이미 해당 주문 상품에 대한 리뷰가 작성되었습니다")
            }
        } else {
            val userOrders = ordersRepository.findByUserId(userId)
            val completedOrderIds = userOrders
                .filter { it.status in listOf("delivered", "confirmed") }
                .map { it.id }

            if (completedOrderIds.isEmpty()) {
                throw IllegalArgumentException("구매한 상품에 대해서만 리뷰를 작성할 수 있습니다")
            }

            val hasPurchasedProduct = orderItemsRepository.existsByOrderIdsAndProductId(completedOrderIds, request.productId)
            if (!hasPurchasedProduct) {
                throw IllegalArgumentException("구매한 상품에 대해서만 리뷰를 작성할 수 있습니다")
            }

            val existingReviews = productReviewsRepository.findByProductIdAndUserId(request.productId, userId)
            if (existingReviews.isNotEmpty()) {
                throw IllegalArgumentException("이미 해당 상품에 대한 리뷰를 작성하셨습니다")
            }
        }

        if (request.rating < 1 || request.rating > 5) {
            throw IllegalArgumentException("평점은 1~5 사이의 값이어야 합니다")
        }

        if (request.content.isBlank()) {
            throw IllegalArgumentException("리뷰 내용을 입력해주세요")
        }

        val now = LocalDateTime.now()
        val productReview = ProductReviews(
            id = UUID.randomUUID(),
            productId = request.productId,
            userId = userId,
            orderId = request.orderId,
            rating = request.rating,
            content = request.content.trim(),
            status = "active",
            imageUrl = request.imageUrl,
            createdAt = now,
            updatedAt = now
        )

        val savedReview = productReviewsRepository.save(productReview)

        return CreateProductReview.Response(
            success = true,
            reviewId = savedReview.id,
            message = "리뷰가 작성되었습니다"
        )
    }
}
