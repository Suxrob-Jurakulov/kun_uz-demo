package com.company.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {
    private Integer id;
    private String content;
    private LocalDateTime createdDate;
    private Integer profileId;
    private String articleId;
    private Integer replyId;
}
