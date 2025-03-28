package com.ml_service.repository;

import com.ml_service.entity.MlProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MlProductsRepository extends JpaRepository<MlProductsEntity,Integer> {
}
