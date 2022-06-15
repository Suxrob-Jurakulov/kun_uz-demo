package com.company.repository;

import com.company.entity.ArticleEntity;
import com.company.entity.CommentEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends CrudRepository<CommentEntity, Integer> {

    List<CommentEntity> findByArticle(ArticleEntity article);

    Optional<CommentEntity> findByIdAndProfileId(Integer id, Integer profileId);
}
