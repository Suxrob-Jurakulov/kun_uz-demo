package com.company.dto.article;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleListDTO {
    private List<String> idList;
    private String key;
}
