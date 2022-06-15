package com.company.dto.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleLikeDTO {

    private String articleId;
}
