package com.company.service;

import com.company.dto.SmsDTO;
import com.company.dto.integration.SmsRequestDTO;
import com.company.dto.integration.SmsResponseDTO;
import com.company.entity.SmsEntity;
import com.company.repository.SmsRepository;
import com.company.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SmsService {
    @Value("${sms.url}")
    private static String smsUrl;
    @Value("${sms.key}")
    private static String key;

    @Autowired
    private SmsRepository smsRepository;

    public void sendRegistrationSms(String phone) {
        String code = RandomUtil.getRandomSmsCode();
        String message = "Kun.uz Test partali uchun\n registratsiya kodi: " + code;

        SmsResponseDTO responseDTO = send(phone, message);
//        SmsResponseDTO responseDTO = new SmsResponseDTO();
        responseDTO.setSuccess(Boolean.TRUE);

        SmsEntity entity = new SmsEntity();
        entity.setPhone(phone);
        entity.setCode(code);
        entity.setStatus(responseDTO.getSuccess());

        smsRepository.save(entity);
    }


    public Long getSmsCount(String phone){
        return smsRepository.getSmsCount(phone);
    }

    public boolean verifySms(String phone, String code) {
        Optional<SmsEntity> optional = smsRepository.findTopByPhoneOrderByCreatedDateDesc(phone);
        if (optional.isEmpty()) {
            return false;
        }
        SmsEntity sms = optional.get();
        LocalDateTime validDate = sms.getCreatedDate().plusMinutes(1);

        return sms.getCode().equals(code) && validDate.isAfter(LocalDateTime.now());
    }

    private SmsResponseDTO send(String phone, String message) {
        SmsRequestDTO requestDTO = new SmsRequestDTO();
        requestDTO.setKey(key);
        requestDTO.setPhone(phone);
        requestDTO.setMessage(message);
        System.out.println("Sms Request: message " + message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SmsRequestDTO> entity = new HttpEntity<>(requestDTO, headers);

        RestTemplate restTemplate = new RestTemplate();
        SmsResponseDTO response = restTemplate.postForObject(smsUrl, entity, SmsResponseDTO.class);
        System.out.println("Sms Response  " + response);
        return response;
    }


    public List<SmsDTO> list(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        List<SmsEntity> entityList = smsRepository.findAllByStatusTrue(pageable);
        List<SmsDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> {
            SmsDTO dto = new SmsDTO();
            dto.setId(entity.getId());
            dto.setPhone(entity.getPhone());
            dto.setCode(entity.getCode());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setStatus(entity.getStatus());
            dtoList.add(dto);
        });
        return dtoList;
    }
}
