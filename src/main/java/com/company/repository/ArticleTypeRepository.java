package com.company.repository;

import com.company.entity.ArticleEntity;
import com.company.entity.ArticleTypeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity, Integer> {

    List<ArticleTypeEntity> findByTypesId(Integer id);


}
