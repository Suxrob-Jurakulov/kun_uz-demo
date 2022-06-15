package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "article_types", uniqueConstraints = @UniqueConstraint(columnNames = {"article_id", "types_id"}))
public class ArticleTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "article_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ArticleEntity article;

    @JoinColumn(name = "types_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TypesEntity types;

}
