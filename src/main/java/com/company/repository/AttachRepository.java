package com.company.repository;

import com.company.dto.AttachDTO;
import com.company.entity.AttachEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachRepository extends PagingAndSortingRepository<AttachEntity, String> {

    @Query("from AttachEntity")
    Page<AttachEntity> getAttachByPage(Pageable pageable);

}
