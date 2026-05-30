package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    JavaMailSender javaMailSender;

    // Stores email → OTP temporarily (use Redis/DB in production)
    private Map<String, String> otpStore = new HashMap<>();

    // ─── Generate 6-digit OTP ─────────────────────────────────────
    public String generateOtp() {
        int otp = 100000 + new Random().nextInt(900000); // always 6 digits
        return String.valueOf(otp);
    }

    // ─── Send OTP via HTML Email ───────────────────────────────────
    public void sendOtpEmail(String toEmail, String otp) throws Exception {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("vinaykumarbaratam1580@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject("Your OTP Verification Code");
        helper.setText(buildHtmlEmail(otp), true); // true = isHtml

        javaMailSender.send(message);

        // Save OTP mapped to email
        otpStore.put(toEmail, otp);
    }

    // ─── Verify OTP ───────────────────────────────────────────────
    public boolean verifyOtp(String email, String enteredOtp) {
        String storedOtp = otpStore.get(email);
        if (storedOtp != null && storedOtp.equals(enteredOtp)) {
            otpStore.remove(email); // OTP used, remove it
            return true;
        }
        return false;
    }

    // ─── HTML Email Template (Inline CSS) ─────────────────────────
    private String buildHtmlEmail(String otp) {
        return """
            <!DOCTYPE html>
            <html>
            <body style="margin:0; padding:0; background-color:#f4f6f9; font-family:Arial,sans-serif;">

              <table width="100%" cellpadding="0" cellspacing="0">
                <tr>
                  <td align="center" style="padding:40px 0;">

                    <table width="480" cellpadding="0" cellspacing="0"
                           style="background:#ffffff; border-radius:12px;
                                  box-shadow:0 4px 20px rgba(0,0,0,0.1);">

                      <!-- HEADER -->
                      <tr>
                        <td align="center"
                            style="background:linear-gradient(135deg,#667eea,#764ba2);
                                   padding:36px 40px; border-radius:12px 12px 0 0;">
                          <h1 style="color:#ffffff; margin:0; font-size:26px;
                                     letter-spacing:1px;">🔐 OTP Verification</h1>
                        </td>
                      </tr>

                      <!-- BODY -->
                      <tr>
                        <td style="padding:40px;">
                          <p style="color:#555; font-size:15px; margin:0 0 20px;">
                            Hello! Use the OTP below to verify your identity.
                            This code is valid for <strong>5 minutes</strong>.
                          </p>

                          <!-- OTP BOX -->
                          <div style="text-align:center; margin:30px 0;">
                            <span style="display:inline-block;
                                         background:#f0f0ff;
                                         border:2px dashed #667eea;
                                         border-radius:10px;
                                         padding:18px 48px;
                                         font-size:38px;
                                         font-weight:bold;
                                         color:#667eea;
                                         letter-spacing:10px;">
                              """ + otp + """
                            </span>
                          </div>

                          <p style="color:#888; font-size:13px; text-align:center;">
                            Do not share this OTP with anyone.
                          </p>
                        </td>
                      </tr>

                      <!-- FOOTER -->
                      <tr>
                        <td align="center"
                            style="background:#f9f9f9; padding:20px;
                                   border-radius:0 0 12px 12px;
                                   border-top:1px solid #eee;">
                          <p style="color:#aaa; font-size:12px; margin:0;">
                            © 2025 JMS App · If you didn't request this, ignore this email.
                          </p>
                        </td>
                      </tr>

                    </table>
                  </td>
                </tr>
              </table>

            </body>
            </html>
        """;
    }
}
