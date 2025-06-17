package com.myfarm.myfarm.domain.categories.service

import com.myfarm.myfarm.adapter.`in`.web.categories.message.GetTreeCategory
import com.myfarm.myfarm.domain.categories.entity.Categories
import com.myfarm.myfarm.domain.categories.port.CategoriesRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class GetTreeCategoryService(
    private val categoriesRepository: CategoriesRepository
) {

    @Transactional(readOnly = true)
    fun getTreeCategory(request: GetTreeCategory.Request): GetTreeCategory.Response {
        val allCategories = when (request.isActive) {
            null -> categoriesRepository.findAll()
            true -> categoriesRepository.findByIsActive(true)
            false -> categoriesRepository.findByIsActive(false)
        }

        val treeItems = buildTree(allCategories, null)

        return GetTreeCategory.Response(categories = treeItems)
    }

    private fun buildTree(categories: List<Categories>, parentId: UUID?): List<GetTreeCategory.CategoryTreeItem> {
        return categories
            .filter { it.parentId == parentId }
            .map { category ->
                GetTreeCategory.CategoryTreeItem(
                    id = category.id,
                    name = category.name,
                    description = category.description,
                    isActive = category.isActive,
                    children = buildTree(categories, category.id)
                )
            }
    }
}
