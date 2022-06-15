package com.company.repository;

import com.company.entity.ArticleEntity;
import com.company.enums.ArticleStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ArticleRepository extends PagingAndSortingRepository<ArticleEntity, String> {
    List<ArticleEntity> findByStatusAndVisibleAndCategoryId(ArticleStatus status, Boolean visible, Integer cId);

    List<ArticleEntity> findByStatusAndVisibleAndModeratorId(ArticleStatus status, Boolean visible, Integer mId);

    List<ArticleEntity> findAllByVisibleAndStatus(Boolean visible, ArticleStatus status);
    boolean existsByIdAndVisible(String id, Boolean visible);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set visible = false where id = ?1")
    void changeStatus(String id);
}
