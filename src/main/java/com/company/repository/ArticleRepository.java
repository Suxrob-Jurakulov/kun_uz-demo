package com.company.repository;

import com.company.entity.ArticleEntity;
import com.company.entity.CategoryEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ArticleStatus;
import com.company.mapper.ArticleShortInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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

    @Query(value = "select a.id, a.title, a.description from article a where a.id not in(?1)\n" +
            "order by a.created_date desc limit 8", nativeQuery = true)
    List<ArticleEntity> getLastArticle(List<String> list);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set status = ?1, publisher = ?2, publishDate = ?3 where id = ?4")
    void changeStatus(ArticleStatus status, ProfileEntity profile, LocalDateTime time, String articleId);

    @Query(value = "select a.id as id, a.title as title, a.description as description, a.publish_date as publishDate " +
            "from article_types art " +
            "join article a on a.id = art.article_id " +
            "join types t on t.id = art.types_id " +
            "where t.key = :key and a.status = 'PUBLISHED' and a.visible = true " +
            "order by a.created_date desc limit :limit",
            nativeQuery = true)
    List<ArticleShortInfo> getByType(@Param("key") String key, @Param("limit") Integer limit);

    @Query(value = "select a.id as id, a.title as title, a.description as description, a.publish_date as publishDate " +
            "from article_types art " +
            "join article a on a.id = art.article_id " +
            "join types t on t.id = art.types_id " +
            "where t.key = :key and a.status = 'PUBLISHED' and a.visible = true and a.id not in (:idList) " +
            "order by a.created_date desc limit :limit",
            nativeQuery = true)
    List<ArticleShortInfo> getLastArticleByTypeByLimitNotInId(@Param("key") String key, @Param("limit") Integer limit,
                                                              @Param("idList") List<String> idList);

    @Query("SELECT new ArticleEntity(art.id,art.title,art.description,art.publishDate) " +
            " from ArticleEntity  as art where art.category.key =:categoryKey and art.status =:status " +
            " and art.visible = true " +
            " order by art.publishDate ")
    Page<ArticleEntity> findLast5ByCategory(@Param("categoryKey") String categoryKey,
                                            @Param("status") ArticleStatus status, Pageable pageable);

    @Query(value = "select  art.id as id ,art.title as title, art.description as description,art.publish_date as publishDate" +
            "   from article as art " +
            "   inner join category as cat on art.category_id = cat.id " +
            " where cat.key=:key and art.status='PUBLISHED' and art.visible=true  " +
            " order by art.publish_date limit 5 ",
            nativeQuery = true)
    List<ArticleShortInfo> findTop5ByArticleByCategory2(@Param("key") String key);

    @Query("SELECT new ArticleEntity(art.id,art.title,art.description,art.publishDate) " +
            " from ArticleTypeEntity as a " +
            " inner join a.article as art " +
            " inner join a.types as t " +
            " Where t.key =:typeKey and art.visible = true and art.status = 'PUBLISHED' " +
            " order by art.publishDate ")
    Page<ArticleEntity> findLast5ByType(@Param("typeKey") String typeKey, Pageable pageable);

    @Query("SELECT new ArticleEntity(art.id,art.title,art.description,art.publishDate) " +
            "from ArticleTagEntity  as a " +
            "join a.article as art " +
            "Where a.tag.name =:key and art.visible = true and art.status = 'PUBLISHED' " +
            "order by art.publishDate ")
    Page<ArticleEntity> getLast4ArticleByTag(@Param("key") String tagKey, Pageable pageable);

    @Query(value = "SELECT art.* " +
            " FROM article as art " +
            " inner join article_type as a on a.article_id = art.id " +
            " inner join types as t on t.id = a.types_id " +
            " where  t.key =:key  " +
            " order by art.publish_date " +
            " limit 5 ",
            nativeQuery = true)
    List<ArticleEntity> findTop5ByArticleNative(@Param("key") String key);

    List<ArticleEntity> findTop5ByCategoryAndStatusAndVisibleTrueOrderByCreatedDateDesc(CategoryEntity category,
                                                                                        ArticleStatus status);

    List<ArticleEntity> findTop5ByCategoryAndStatusAndVisibleOrderByCreatedDateDesc(CategoryEntity category,
                                                                                    ArticleStatus status, Boolean visible);

    @Query(value = "select  a.id as id ,a.title as title, a.description as description,a.publish_date as publishDate " +
            "from article as a where a.status = 'PUBLISHED' and a.visible = true and a.id not in (:idList) " +
            "order by a.publish_date desc limit 8", nativeQuery = true)
    List<ArticleShortInfo> getLast8ArticleByNotInIdList(@Param("idList") List<String> idList);

    @Query(value = "select  a.id as id ,a.title as title, a.description as description,a.publish_date as publishDate " +
            "from article as a where a.status = 'PUBLISHED' and a.visible = true order by a.view_count desc limit :limit", nativeQuery = true)
    List<ArticleShortInfo> get4MostArticle(@Param("limit") Integer limit);


    List<ArticleEntity> findTop4ByStatusAndVisibleTrueOrderByViewCountDesc(ArticleStatus status);

    @Query(value = "select new ArticleEntity(a.id, a.title,a.description,a.publishDate) " +
            "from ArticleTypeEntity art join ArticleEntity a on art.article = a where art.types.key = ?1 and a.region.key = ?2 " +
            "and a.status = 'PUBLISHED' and a.visible = true order by a.publishDate desc")
    Page<ArticleEntity> getLast4ByTypeByRegion(String type, String region, Pageable pageable);

    @Query(value = "select new ArticleEntity(a.id, a.title,a.description,a.publishDate) " +
            "from ArticleEntity a  where  a.region.key = ?1 " +
            "and a.status = 'PUBLISHED' and a.visible = true order by a.publishDate desc")
    Page<ArticleEntity> getListByByRegionByPagination(String region, Pageable pageable);

    @Query(value = "select new ArticleEntity(a.id, a.title,a.description,a.publishDate) " +
            "from ArticleEntity a  where  a.category.key = ?1 " +
            "and a.status = 'PUBLISHED' and a.visible = true order by a.publishDate desc")
    Page<ArticleEntity> getListByByCategory(String key, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "update ArticleEntity set viewCount = viewCount + 1 where id = ?1")
    void increaseViewCount(String id);
}
