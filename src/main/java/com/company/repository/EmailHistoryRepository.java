package com.company.repository;

import com.company.entity.EmailHistoryEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmailHistoryRepository extends PagingAndSortingRepository<EmailHistoryEntity, Integer> {

}
