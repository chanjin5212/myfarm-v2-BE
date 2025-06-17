package com.myfarm.myfarm.domain.categories.service

import com.myfarm.myfarm.adapter.`in`.web.categories.message.DeleteCategory
import com.myfarm.myfarm.domain.categories.port.CategoriesRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class DeleteCategoryService(
    private val categoriesRepository: CategoriesRepository
) {

    @Transactional
    fun deleteCategory(id: UUID): DeleteCategory.Response {
        categoriesRepository.findById(id)
            ?: throw IllegalArgumentException("존재하지 않는 카테고리입니다")

        // 자식 카테고리가 있는지 확인
        val childCategories = categoriesRepository.findByParentId(id)
        if (childCategories.isNotEmpty()) {
            throw IllegalArgumentException("하위 카테고리가 존재하여 삭제할 수 없습니다")
        }

        // TODO: 해당 카테고리를 사용하는 상품이 있는지 확인
        // if (productRepository.existsByCategoryId(id)) {
        //     throw IllegalArgumentException("해당 카테고리를 사용하는 상품이 존재하여 삭제할 수 없습니다")
        // }

        categoriesRepository.deleteById(id)

        return DeleteCategory.Response(success = true)
    }
}
