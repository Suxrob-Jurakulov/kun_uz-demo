package com.company.dto.article;

import com.company.dto.CategoryDTO;
import com.company.dto.ProfileDTO;
import com.company.dto.RegionDTO;
import com.company.enums.ArticleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {
    private String id;
    private String title;
    private String content;
    private String description;
    private Integer viewCount;
    private Integer sharedCount;
    private Integer likeCount;
    private ArticleStatus status;
    private Boolean visible;
    private LocalDateTime createdDate;
    private LocalDateTime publishDate;
    private ProfileDTO moderator;
    private ProfileDTO publisher;
    private RegionDTO region;
    private CategoryDTO category;
    private List<String> tagList;
}
