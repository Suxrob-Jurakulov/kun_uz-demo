package com.company.dto.integration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsRequestDTO {
    private String key;
    private String phone;
    private String message;

}
