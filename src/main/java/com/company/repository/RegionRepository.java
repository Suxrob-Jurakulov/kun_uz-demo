package com.company.repository;

import com.company.entity.ProfileEntity;
import com.company.entity.RegionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RegionRepository extends CrudRepository<RegionEntity, Integer> {

    Optional<RegionEntity> findByKey(String key);

    void deleteByKey(String key);
}
