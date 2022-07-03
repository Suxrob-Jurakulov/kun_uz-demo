package com.company.repository;

import com.company.entity.SmsEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SmsRepository extends CrudRepository<SmsEntity, Integer> {

    Optional<SmsEntity> findTopByPhoneOrderByCreatedDateDesc(String phone);


    List<SmsEntity> findAllByStatusTrue(Pageable pageable);

    @Query(value = "select count(*) from sms where phone =:phone and created_date > now() - INTERVAL '1 MINUTE' ",
            nativeQuery = true)
    Long getSmsCount(@Param("phone") String phone);
}
