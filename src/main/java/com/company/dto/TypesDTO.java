package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TypesDTO {
    private Integer id;
    private LocalDateTime createdDate;
    @NotNull
    private String key;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String name;
}
