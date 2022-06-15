package com.company.dto.comment;

import com.company.entity.ArticleEntity;
import com.company.entity.ProfileEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {
    private Integer id;
    private String content;
    private LocalDateTime createdDate;
    private ProfileEntity profile;
    private ArticleEntity article;
    private Integer replyId;
}
