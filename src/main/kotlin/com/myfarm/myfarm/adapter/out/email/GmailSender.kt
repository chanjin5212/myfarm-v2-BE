package com.myfarm.myfarm.adapter.out.email

import com.myfarm.myfarm.domain.users.port.EmailSender
import jakarta.mail.internet.MimeMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component

@Component
class GmailSender(
    private val javaMailSender: JavaMailSender
) : EmailSender {

    override fun sendVerificationEmail(email: String, code: String) {
        val message: MimeMessage = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "UTF-8")

        helper.setTo(email)
        helper.setSubject("강원찐농부 이메일 인증")
        helper.setText(createVerificationEmailHtml(code), true)

        javaMailSender.send(message)
    }

    override fun sendWelcomeEmail(email: String) {
        val message: MimeMessage = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "UTF-8")

        helper.setTo(email)
        helper.setSubject("강원찐농부에 오신 것을 환영합니다!")
        helper.setText(createWelcomeEmailHtml(), true)

        javaMailSender.send(message)
    }

    // 추가된 메서드 1: 아이디 찾기 인증
    override fun sendFindIdVerificationEmail(email: String, verificationCode: String) {
        val message: MimeMessage = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "UTF-8")

        helper.setTo(email)
        helper.setSubject("강원찐농부 아이디 찾기 인증")
        helper.setText(createFindIdVerificationEmailHtml(verificationCode), true)

        javaMailSender.send(message)
    }

    // 추가된 메서드 2: 비밀번호 찾기 인증
    override fun sendFindPasswordVerificationEmail(email: String, verificationCode: String) {
        val message: MimeMessage = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "UTF-8")

        helper.setTo(email)
        helper.setSubject("강원찐농부 비밀번호 찾기 인증")
        helper.setText(createFindPasswordVerificationEmailHtml(verificationCode), true)

        javaMailSender.send(message)
    }

    private fun createVerificationEmailHtml(code: String): String {
        val content = """
            <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; background-color: #f8fffe;">
                <div style="background: linear-gradient(135deg, #4CAF50, #81C784); padding: 30px; text-align: center; border-radius: 8px 8px 0 0;">
                    <h1 style="color: white; margin: 0; font-size: 28px;">🌾 강원찐농부</h1>
                    <p style="color: white; margin: 10px 0 0 0; opacity: 0.9;">신선한 농산물의 시작</p>
                </div>
                <div style="padding: 30px; background-color: white;">
                    <h2 style="color: #2E7D32; text-align: center;">이메일 인증번호</h2>
                    <p>안녕하세요! 강원찐농부입니다.</p>
                    <p>회원가입을 위해 아래 인증번호를 입력해주세요:</p>
                    
                    <div style="background: linear-gradient(135deg, #E8F5E8, #C8E6C9); padding: 25px; border-radius: 12px; text-align: center; margin: 25px 0; border-left: 4px solid #4CAF50;">
                        <h1 style="color: #2E7D32; margin: 0; font-size: 36px; letter-spacing: 6px; font-weight: bold;">$code</h1>
                    </div>
                    
                    <div style="background-color: #FFF3E0; padding: 15px; border-radius: 8px; border-left: 4px solid #FF9800;">
                        <p style="color: #E65100; margin: 0; font-size: 14px;">
                            🕒 <strong>인증번호는 5분간 유효합니다.</strong><br>
                            🔒 본인이 요청하지 않았다면 이 메일을 무시해주세요.
                        </p>
                    </div>
                </div>
                <div style="background-color: #2E7D32; padding: 20px; text-align: center; border-radius: 0 0 8px 8px;">
                    <p style="color: white; margin: 0; font-size: 12px; opacity: 0.8;">
                        © 2025 강원찐농부. 신선함을 전하는 믿을 수 있는 농산물 쇼핑몰
                    </p>
                </div>
            </div>
        """.trimIndent()

        return content
    }

    private fun createWelcomeEmailHtml(): String {
        val content = """
            <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; background-color: #f8fffe;">
                <div style="background: linear-gradient(135deg, #4CAF50, #81C784); padding: 30px; text-align: center; border-radius: 8px 8px 0 0;">
                    <h1 style="color: white; margin: 0; font-size: 28px;">🌾 강원찐농부</h1>
                    <p style="color: white; margin: 10px 0 0 0; opacity: 0.9;">신선한 농산물의 시작</p>
                </div>
                <div style="padding: 30px; background-color: white;">
                    <h2 style="color: #2E7D32; text-align: center;">🎉 환영합니다!</h2>
                    <p>안녕하세요! <strong>강원찐농부</strong>에 가입해주셔서 감사합니다.</p>
                    
                    <div style="background: linear-gradient(135deg, #E8F5E8, #C8E6C9); padding: 20px; border-radius: 12px; margin: 20px 0;">
                        <h3 style="color: #2E7D32; margin-top: 0;">🥕 이제 이런 것들을 즐기실 수 있어요:</h3>
                        <ul style="color: #388E3C; line-height: 1.6;">
                            <li>🌿 강원도 직송 신선한 채소</li>
                            <li>🍎 당일 수확한 과일</li>
                            <li>🌾 우리 농부들이 직접 기른 곡물</li>
                            <li>📦 신선도를 보장하는 당일 배송</li>
                        </ul>
                    </div>
                    
                    <div style="text-align: center; margin: 25px 0;">
                        <a href="http://localhost:3000" style="background: linear-gradient(135deg, #4CAF50, #66BB6A); color: white; padding: 15px 30px; text-decoration: none; border-radius: 25px; font-weight: bold; display: inline-block;">
                            🛒 쇼핑 시작하기
                        </a>
                    </div>
                    
                    <p style="color: #666; font-size: 14px;">
                        궁금한 점이 있으시면 언제든지 고객센터로 문의해주세요.<br>
                        정성스럽게 기른 농산물로 건강한 식탁을 만들어드리겠습니다! 🌱
                    </p>
                </div>
                <div style="background-color: #2E7D32; padding: 20px; text-align: center; border-radius: 0 0 8px 8px;">
                    <p style="color: white; margin: 0; font-size: 12px; opacity: 0.8;">
                        © 2025 강원찐농부. 신선함을 전하는 믿을 수 있는 농산물 쇼핑몰
                    </p>
                </div>
            </div>
        """.trimIndent()

        return content
    }

    // 추가된 템플릿 1: 아이디 찾기
    private fun createFindIdVerificationEmailHtml(code: String): String {
        val content = """
            <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; background-color: #f8fffe;">
                <div style="background: linear-gradient(135deg, #4CAF50, #81C784); padding: 30px; text-align: center; border-radius: 8px 8px 0 0;">
                    <h1 style="color: white; margin: 0; font-size: 28px;">🌾 강원찐농부</h1>
                    <p style="color: white; margin: 10px 0 0 0; opacity: 0.9;">신선한 농산물의 시작</p>
                </div>
                <div style="padding: 30px; background-color: white;">
                    <h2 style="color: #2E7D32; text-align: center;">아이디 찾기 인증번호</h2>
                    <p>안녕하세요! 강원찐농부입니다.</p>
                    <p>아이디 찾기를 위해 아래 인증번호를 입력해주세요:</p>
                    
                    <div style="background: linear-gradient(135deg, #E8F5E8, #C8E6C9); padding: 25px; border-radius: 12px; text-align: center; margin: 25px 0; border-left: 4px solid #4CAF50;">
                        <h1 style="color: #2E7D32; margin: 0; font-size: 36px; letter-spacing: 6px; font-weight: bold;">$code</h1>
                    </div>
                    
                    <div style="background-color: #FFF3E0; padding: 15px; border-radius: 8px; border-left: 4px solid #FF9800;">
                        <p style="color: #E65100; margin: 0; font-size: 14px;">
                            🕒 <strong>인증번호는 5분간 유효합니다.</strong><br>
                            🔒 본인이 요청하지 않았다면 이 메일을 무시해주세요.
                        </p>
                    </div>
                </div>
                <div style="background-color: #2E7D32; padding: 20px; text-align: center; border-radius: 0 0 8px 8px;">
                    <p style="color: white; margin: 0; font-size: 12px; opacity: 0.8;">
                        © 2025 강원찐농부. 신선함을 전하는 믿을 수 있는 농산물 쇼핑몰
                    </p>
                </div>
            </div>
        """.trimIndent()

        return content
    }

    // 추가된 템플릿 2: 비밀번호 찾기
    private fun createFindPasswordVerificationEmailHtml(code: String): String {
        val content = """
            <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; background-color: #f8fffe;">
                <div style="background: linear-gradient(135deg, #4CAF50, #81C784); padding: 30px; text-align: center; border-radius: 8px 8px 0 0;">
                    <h1 style="color: white; margin: 0; font-size: 28px;">🌾 강원찐농부</h1>
                    <p style="color: white; margin: 10px 0 0 0; opacity: 0.9;">신선한 농산물의 시작</p>
                </div>
                <div style="padding: 30px; background-color: white;">
                    <h2 style="color: #2E7D32; text-align: center;">비밀번호 찾기 인증번호</h2>
                    <p>안녕하세요! 강원찐농부입니다.</p>
                    <p>비밀번호 찾기를 위해 아래 인증번호를 입력해주세요:</p>
                    
                    <div style="background: linear-gradient(135deg, #E8F5E8, #C8E6C9); padding: 25px; border-radius: 12px; text-align: center; margin: 25px 0; border-left: 4px solid #4CAF50;">
                        <h1 style="color: #2E7D32; margin: 0; font-size: 36px; letter-spacing: 6px; font-weight: bold;">$code</h1>
                    </div>
                    
                    <div style="background-color: #FFF3E0; padding: 15px; border-radius: 8px; border-left: 4px solid #FF9800;">
                        <p style="color: #E65100; margin: 0; font-size: 14px;">
                            🕒 <strong>인증번호는 5분간 유효합니다.</strong><br>
                            🔒 본인이 요청하지 않았다면 이 메일을 무시해주세요.
                        </p>
                    </div>
                </div>
                <div style="background-color: #2E7D32; padding: 20px; text-align: center; border-radius: 0 0 8px 8px;">
                    <p style="color: white; margin: 0; font-size: 12px; opacity: 0.8;">
                        © 2025 강원찐농부. 신선함을 전하는 믿을 수 있는 농산물 쇼핑몰
                    </p>
                </div>
            </div>
        """.trimIndent()

        return content
    }
}