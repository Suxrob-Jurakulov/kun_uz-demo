package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SmsDTO {
    private Integer id;
    private String phone;
    private String code;
    private LocalDateTime createdDate;
    private Boolean status;
}
