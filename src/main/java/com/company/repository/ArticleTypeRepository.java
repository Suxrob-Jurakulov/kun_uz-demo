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

}
