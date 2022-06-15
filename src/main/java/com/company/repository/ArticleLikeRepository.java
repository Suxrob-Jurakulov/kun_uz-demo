package com.company.repository;

import com.company.entity.ArticleLikeEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleLikeRepository extends PagingAndSortingRepository<ArticleLikeEntity, Integer> {

}
