package com.company.repository;

import com.company.entity.ArticleTagEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleTagRepository extends CrudRepository<ArticleTagEntity, Integer> {

    @Query(value = "select t.name from ArticleTagEntity art join TagEntity t on art.tag = t " +
            "where art.article.id = ?1")
    List<String> getTagListByArticleId(String articleId);

}
