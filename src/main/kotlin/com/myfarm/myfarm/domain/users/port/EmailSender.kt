package com.myfarm.myfarm.domain.users.port

interface EmailSender {
    fun sendVerificationEmail(email: String, code: String)
    fun sendWelcomeEmail(email: String)
    fun sendFindIdVerificationEmail(email: String, verificationCode: String)
    fun sendFindPasswordVerificationEmail(email: String, verificationCode: String)
}
