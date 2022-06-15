package com.company.entity;

import com.company.enums.LikeStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "article_like")
public class ArticleLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column
    @Enumerated(EnumType.STRING)
    private LikeStatus status;

    @JoinColumn(name = "profile_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProfileEntity profile;

    @JoinColumn(name = "article_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ArticleEntity article;
}
