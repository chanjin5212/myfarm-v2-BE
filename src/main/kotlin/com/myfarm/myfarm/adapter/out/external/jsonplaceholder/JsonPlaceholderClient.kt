package com.myfarm.myfarm.adapter.out.external.jsonplaceholder

import com.myfarm.myfarm.adapter.out.external.jsonplaceholder.message.GetPost
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange

@HttpExchange
interface JsonPlaceholderClient {

    @GetExchange("/posts/{postId}")
    fun getPost(@PathVariable postId: Long): GetPost.Response
}
