package com.company.dto.article;

import lombok.Data;

import java.util.List;

@Data
public class ArticleCreateDTO {

    private String title;
    private String content;
    private String description;

    private Integer regionId;
    private Integer categoryId;

    private List<Integer> typesList;
    private List<String> tagList;

}
