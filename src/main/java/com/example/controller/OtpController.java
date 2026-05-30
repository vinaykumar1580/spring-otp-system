package com.example.controller;

import com.example.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    @Autowired
    OtpService otpService;

    // ─── Send OTP ─────────────────────────────────────────────────
    @PostMapping("/send")
    public Map<String, String> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        try {
            String otp = otpService.generateOtp();
            otpService.sendOtpEmail(email, otp);
            return Map.of("status", "success", "message", "OTP sent to " + email);
        } catch (Exception e) {
            return Map.of("status", "error", "message", e.getMessage());
        }
    }

    // ─── Verify OTP ───────────────────────────────────────────────
    @PostMapping("/verify")
    public Map<String, String> verifyOtp(@RequestBody Map<String, String> request) {
        String email   = request.get("email");
        String otp     = request.get("otp");
        boolean valid  = otpService.verifyOtp(email, otp);

        if (valid) {
            return Map.of("status", "success", "message", "✅ OTP Verified! Welcome.");
        } else {
            return Map.of("status", "error", "message", "❌ Invalid or expired OTP.");
        }
    }
}
