package com.company.dto.article;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleCreateDTO {

    private String title;
    private String content;
    private String description;

    private Integer regionId;
    private Integer categoryId;

    private List<Integer> typesList;
    private List<String> tagList;

}
