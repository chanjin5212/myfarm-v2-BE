package com.myfarm.myfarm.adapter.`in`.web.categories

import com.myfarm.myfarm.adapter.`in`.web.categories.message.CreateCategory
import com.myfarm.myfarm.adapter.`in`.web.categories.message.DeleteCategory
import com.myfarm.myfarm.adapter.`in`.web.categories.message.GetCategory
import com.myfarm.myfarm.adapter.`in`.web.categories.message.GetTreeCategory
import com.myfarm.myfarm.adapter.`in`.web.categories.message.UpdateCategory
import com.myfarm.myfarm.adapter.`in`.web.config.security.MyfarmAuthentication
import com.myfarm.myfarm.domain.categories.service.CreateCategoryService
import com.myfarm.myfarm.domain.categories.service.DeleteCategoryService
import com.myfarm.myfarm.domain.categories.service.GetCategoryService
import com.myfarm.myfarm.domain.categories.service.GetTreeCategoryService
import com.myfarm.myfarm.domain.categories.service.UpdateCategoryService
import com.myfarm.myfarm.domain.common.checkAdminPermission
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/categories/v1")
class CategoriesController(
    private val createCategoryService: CreateCategoryService,
    private val getCategoryService: GetCategoryService,
    private val updateCategoryService: UpdateCategoryService,
    private val deleteCategoryService: DeleteCategoryService,
    private val getTreeCategoryService: GetTreeCategoryService
) {

    @PostMapping
    fun createCategory(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        @RequestBody request: CreateCategory.Request
    ): CreateCategory.Response {
        myfarmAuth.checkAdminPermission()
        return createCategoryService.createCategory(request)
    }

    @GetMapping("/{id}")
    fun getCategory(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        request: GetCategory.PathVariable
    ): GetCategory.Response {
        myfarmAuth.checkAdminPermission()
        return getCategoryService.getCategory(request.id)
    }

    @PutMapping("/{id}")
    fun updateCategory(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        request: UpdateCategory.PathVariable,
        @RequestBody updateRequest: UpdateCategory.Request
    ): UpdateCategory.Response {
        myfarmAuth.checkAdminPermission()
        return updateCategoryService.updateCategory(request.id, updateRequest)
    }

    @DeleteMapping("/{id}")
    fun deleteCategory(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        request: DeleteCategory.PathVariable
    ): DeleteCategory.Response {
        myfarmAuth.checkAdminPermission()
        return deleteCategoryService.deleteCategory(request.id)
    }

    @GetMapping("/tree")
    fun getTreeCategory(
        @RequestParam(required = false) isActive: Boolean?
    ): GetTreeCategory.Response {
        return getTreeCategoryService.getTreeCategory(GetTreeCategory.Request(isActive = isActive))
    }
}
