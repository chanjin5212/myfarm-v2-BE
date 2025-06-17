package com.myfarm.myfarm.domain.categories.service

import com.myfarm.myfarm.adapter.`in`.web.categories.message.UpdateCategory
import com.myfarm.myfarm.domain.categories.port.CategoriesRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class UpdateCategoryService(
    private val categoriesRepository: CategoriesRepository
) {

    @Transactional
    fun updateCategory(id: UUID, request: UpdateCategory.Request): UpdateCategory.Response {
        val category = categoriesRepository.findById(id)
            ?: throw IllegalArgumentException("존재하지 않는 카테고리입니다")

        if (category.name != request.name && categoriesRepository.existsByName(request.name)) {
            throw IllegalArgumentException("이미 존재하는 카테고리 이름입니다")
        }

        request.parentId?.let { parentId ->
            if (parentId == id) {
                throw IllegalArgumentException("자기 자신을 부모로 설정할 수 없습니다")
            }
            categoriesRepository.findById(parentId)
                ?: throw IllegalArgumentException("존재하지 않는 부모 카테고리입니다")
        }

        val updatedCategory = category.copy(
            name = request.name,
            parentId = request.parentId,
            description = request.description,
            isActive = request.isActive,
            updatedAt = LocalDateTime.now()
        )

        categoriesRepository.save(updatedCategory)

        return UpdateCategory.Response(success = true)
    }
}
