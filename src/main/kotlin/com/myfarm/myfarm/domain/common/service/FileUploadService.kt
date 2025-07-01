package com.myfarm.myfarm.domain.common.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.util.UUID

@Service
class FileUploadService(
    @Value("\${supabase.url}") private val supabaseUrl: String,
    @Value("\${supabase.anon.key}") private val supabaseAnonKey: String,
    @Value("\${supabase.storage.bucket}") private val bucketName: String
) {

    private val httpClient = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(30))
        .build()

    fun uploadProductImage(file: MultipartFile, productId: UUID): String {
        if (file.isEmpty) {
            throw IllegalArgumentException("파일이 비어있습니다")
        }

        val allowedTypes = listOf("image/jpeg", "image/jpg", "image/png", "image/webp")
        if (file.contentType !in allowedTypes) {
            throw IllegalArgumentException("지원하지 않는 파일 형식입니다. (jpg, png, webp만 지원)")
        }

        val maxSize = 5 * 1024 * 1024 // 5MB
        if (file.size > maxSize) {
            throw IllegalArgumentException("파일 크기가 너무 큽니다. (최대 5MB)")
        }

        val fileName = generateFileName(file.originalFilename ?: "image", productId)
        val filePath = "products/$productId/$fileName"

        return uploadToSupabase(file, filePath)
    }

    private fun generateFileName(originalFileName: String, productId: UUID): String {
        val extension = originalFileName.substringAfterLast(".", "jpg")
        val timestamp = System.currentTimeMillis()
        val randomId = UUID.randomUUID().toString().substring(0, 8)
        return "${productId}_${timestamp}_$randomId.$extension"
    }

    private fun uploadToSupabase(file: MultipartFile, filePath: String): String {
        val uploadUrl = "$supabaseUrl/storage/v1/object/$bucketName/$filePath"

        val request = HttpRequest.newBuilder()
            .uri(URI.create(uploadUrl))
            .header("Authorization", "Bearer $supabaseAnonKey")
            .header("Content-Type", file.contentType ?: "application/octet-stream")
            .POST(HttpRequest.BodyPublishers.ofByteArray(file.bytes))
            .build()

        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() !in 200..299) {
            throw RuntimeException("파일 업로드 실패: ${response.body()}")
        }

        // Supabase public URL 반환
        return "$supabaseUrl/storage/v1/object/public/$bucketName/$filePath"
    }

    fun deleteFile(imageUrl: String): Boolean {
        return try {
            val filePath = extractFilePathFromUrl(imageUrl)
            val deleteUrl = "$supabaseUrl/storage/v1/object/$bucketName/$filePath"

            val request = HttpRequest.newBuilder()
                .uri(URI.create(deleteUrl))
                .header("Authorization", "Bearer $supabaseAnonKey")
                .DELETE()
                .build()

            val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
            response.statusCode() in 200..299
        } catch (e: Exception) {
            false
        }
    }

    private fun extractFilePathFromUrl(imageUrl: String): String {
        // URL에서 파일 경로 추출: .../public/bucket/products/uuid/filename -> products/uuid/filename
        return imageUrl.substringAfter("/public/$bucketName/")
    }
}
