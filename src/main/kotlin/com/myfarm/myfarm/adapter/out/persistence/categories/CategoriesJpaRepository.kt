package com.myfarm.myfarm.adapter.out.persistence.categories

import com.myfarm.myfarm.domain.categories.entity.Categories
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CategoriesJpaRepository : JpaRepository<Categories, UUID> {

    fun findByParentId(parentId: UUID?): List<Categories>

    fun findByIsActive(isActive: Boolean): List<Categories>

    fun findByParentIdAndIsActive(parentId: UUID?, isActive: Boolean): List<Categories>

    fun existsByName(name: String): Boolean
}