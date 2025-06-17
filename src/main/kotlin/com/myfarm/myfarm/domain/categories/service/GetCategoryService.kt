package com.myfarm.myfarm.domain.categories.service

import com.myfarm.myfarm.adapter.`in`.web.categories.message.GetCategory
import com.myfarm.myfarm.domain.categories.port.CategoriesRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class GetCategoryService(
    private val categoriesRepository: CategoriesRepository
) {

    @Transactional(readOnly = true)
    fun getCategory(id: UUID): GetCategory.Response {
        val category = categoriesRepository.findById(id)
            ?: throw IllegalArgumentException("존재하지 않는 카테고리입니다")

        return GetCategory.Response(
            id = category.id,
            name = category.name,
            parentId = category.parentId,
            description = category.description,
            isActive = category.isActive,
            createdAt = category.createdAt,
            updatedAt = category.updatedAt
        )
    }
}
