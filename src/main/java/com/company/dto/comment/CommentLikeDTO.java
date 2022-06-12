package com.company.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentLikeDTO {
    private Boolean status;
    private Integer profileId;
    private Integer commentId;
}
