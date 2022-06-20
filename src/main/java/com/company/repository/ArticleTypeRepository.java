package com.company.repository;

import com.company.entity.ArticleEntity;
import com.company.entity.ArticleTypeEntity;
import com.company.entity.TypesEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity, Integer> {

//    List<ArticleTypeEntity> findByTypesKey(String key);

    @Query("select a.article from ArticleTypeEntity a where a.types = ?1")
    List<ArticleEntity> getEntity(TypesEntity entity);


    @Query(value = "select a from ArticleTypeEntity art join ArticleEntity a on a = art.article " +
            "where art.types = :ty and a.status = 'PUBLISHED' and a.visible = true " +
            "order by a.createdDate desc")
    List<ArticleEntity> byType(TypesEntity ty);


}
