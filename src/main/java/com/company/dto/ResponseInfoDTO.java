package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseInfoDTO {
    private int status;
    private String message;

    public ResponseInfoDTO(int status) {
        this.status = status;
    }

    public ResponseInfoDTO(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
