package com.company.controller;

import com.company.dto.SmsDTO;
import com.company.enums.ProfileRole;
import com.company.service.SmsService;
import com.company.util.HttpHeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/sms")
public class SmsController {
    @Autowired
    private SmsService smsService;

//    @GetMapping("/list")
//    public ResponseEntity<?> list(@RequestParam(name = "page") Integer page,
//                                  @RequestParam(name = "size") Integer size, HttpServletRequest request) {
//        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
//        List<SmsDTO> response = smsService.list(page, size);
//        return ResponseEntity.ok().body(response);
//    }
}
