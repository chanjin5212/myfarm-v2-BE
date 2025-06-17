package com.myfarm.myfarm.domain.categories.service

import com.myfarm.myfarm.adapter.`in`.web.categories.message.CreateCategory
import com.myfarm.myfarm.domain.categories.entity.Categories
import com.myfarm.myfarm.domain.categories.port.CategoriesRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class CreateCategoryService(
    private val categoriesRepository: CategoriesRepository
) {

    @Transactional
    fun createCategory(request: CreateCategory.Request): CreateCategory.Response {
        if (categoriesRepository.existsByName(request.name)) {
            throw IllegalArgumentException("이미 존재하는 카테고리 이름입니다")
        }

        // 부모 카테고리가 있다면 존재하는지 확인
        request.parentId?.let { parentId ->
            categoriesRepository.findById(parentId)
                ?: throw IllegalArgumentException("존재하지 않는 부모 카테고리입니다")
        }

        val now = LocalDateTime.now()
        val category = Categories(
            id = UUID.randomUUID(),
            name = request.name,
            parentId = request.parentId,
            description = request.description,
            isActive = true,
            createdAt = now,
            updatedAt = now
        )

        categoriesRepository.save(category)

        return CreateCategory.Response(success = true)
    }
}