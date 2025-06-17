package com.myfarm.myfarm.domain.categories.port

import com.myfarm.myfarm.adapter.out.persistence.categories.CategoriesJpaRepository
import com.myfarm.myfarm.domain.categories.entity.Categories
import org.springframework.stereotype.Repository
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Repository
class CategoriesRepository(
    private val categoriesJpaRepository: CategoriesJpaRepository
) {

    fun findById(id: UUID): Categories? {
        return categoriesJpaRepository.findById(id).getOrNull()
    }

    fun findAll(): List<Categories> {
        return categoriesJpaRepository.findAll()
    }

    fun findByParentId(parentId: UUID?): List<Categories> {
        return categoriesJpaRepository.findByParentId(parentId)
    }

    fun findByIsActive(isActive: Boolean): List<Categories> {
        return categoriesJpaRepository.findByIsActive(isActive)
    }

    fun findByParentIdAndIsActive(parentId: UUID?, isActive: Boolean): List<Categories> {
        return categoriesJpaRepository.findByParentIdAndIsActive(parentId, isActive)
    }

    fun existsByName(name: String): Boolean {
        return categoriesJpaRepository.existsByName(name)
    }

    fun save(category: Categories): Categories {
        return categoriesJpaRepository.save(category)
    }

    fun deleteById(id: UUID) {
        categoriesJpaRepository.deleteById(id)
    }
}