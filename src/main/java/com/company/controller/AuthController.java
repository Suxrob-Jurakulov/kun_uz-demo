package com.company.controller;

import com.company.dto.*;
import com.company.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Api(tags = "Authorization and Registration")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @ApiOperation(value = "Registration", notes = "Method for registration")
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody @Valid RegistrationDTO dto) {
        log.info("Request for registration {}", dto);
        String response = authService.registration(dto);
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value = "Login", notes = "Method for authorization")
    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> login(@RequestBody AuthDTO dto) {
        log.info("Request for login {}", dto);

        ProfileDTO profileDto = authService.login(dto);
        return ResponseEntity.ok(profileDto);
    }

    @ApiOperation(value = "SMS verification", notes = "Method for SMS verification")
    @PostMapping("/verification")
    public ResponseEntity<String> smsVerification(@RequestBody VerificationDTO dto) {
        log.info("Request for sms verification {}", dto);
        String response = authService.verification(dto);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Email verification", notes = "Method for Email verification")
    @GetMapping("/email/verification/{id}")
    public ResponseEntity<String> login(@PathVariable("id") Integer id) {
        log.info("Request for email verification {}", id);
        String response = authService.emailVerification(id);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Resend SMS", notes = "Method for resend sms")
    @GetMapping("/resend/{phone}")
    public ResponseEntity<ResponseInfoDTO> resendSms(@PathVariable("phone") String phone) {
        log.info("Request for resend SMS {}", phone);
        ResponseInfoDTO response = authService.resendSms(phone);
        return ResponseEntity.ok(response);
    }
}
