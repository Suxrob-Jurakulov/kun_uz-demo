package com.company.dto.comment;

import com.company.dto.ProfileDTO;
import com.company.dto.article.ArticleDTO;
import com.company.entity.ArticleEntity;
import com.company.entity.ProfileEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {
    private Integer id;
    @NotNull
    private String content;
    private LocalDateTime createdDate;
    @NotNull
    private ProfileDTO profile;
    @NotNull
    private ArticleDTO article;
    private Integer replyId;
}
