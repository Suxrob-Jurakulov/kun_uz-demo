package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column
    private String content;

    @Column
    private Boolean visible;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "reply_id")
    private Integer replyId;

    @JoinColumn(name = "article_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ArticleEntity article;

    @JoinColumn(name = "profile_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProfileEntity profile;

    public CommentEntity() {
    }

    public CommentEntity(Integer id) {
        this.id = id;
    }
}
