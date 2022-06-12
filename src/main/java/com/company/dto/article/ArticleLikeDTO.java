package com.company.dto.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleLikeDTO {

    private Boolean status;
    private Integer profileId;
    private String articleId;
}
