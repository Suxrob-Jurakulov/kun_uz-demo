package com.company.repository;

import com.company.entity.TypesEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TypesRepository extends CrudRepository<TypesEntity, Integer> {

    Optional<TypesEntity> findByKey(String key);

    void deleteByKey(String key);
}
