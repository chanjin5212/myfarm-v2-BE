package com.myfarm.myfarm.adapter.`in`.web.categories

import com.myfarm.myfarm.adapter.`in`.web.categories.message.CreateCategory
import com.myfarm.myfarm.adapter.`in`.web.config.security.MyfarmAuthentication
import com.myfarm.myfarm.domain.categories.service.CreateCategoryService
import com.myfarm.myfarm.domain.common.checkAdminPermission
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/categories/v1")
class CategoriesController(
    private val createCategoryService: CreateCategoryService
) {

    @PostMapping
    fun createCategory(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        @RequestBody request: CreateCategory.Request
    ): CreateCategory.Response {
        myfarmAuth.checkAdminPermission()
        return createCategoryService.createCategory(request)
    }
}