package com.company.dto.article;

import com.company.entity.CategoryEntity;
import com.company.entity.ProfileEntity;
import com.company.entity.RegionEntity;
import com.company.enums.ArticleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {
    private String id;
    private String title;
    private String content;
    private String description;
    private Integer viewCount;
    private Integer sharedCount;
    private ArticleStatus status;
    private Boolean visible = Boolean.TRUE;
    private LocalDateTime createdDate;
    private LocalDateTime publishDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProfileEntity moderator;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProfileEntity publisher;
    private RegionEntity region;
    private CategoryEntity category;
}
