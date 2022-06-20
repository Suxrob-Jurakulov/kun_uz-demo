package com.company.dto.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleResponseDTO {
    private String id;
    private String title;
    private String description;
    private String type;
    private LocalDateTime publishDate;
}
