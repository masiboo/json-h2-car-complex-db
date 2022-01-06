package com.example.jsonh2carcomplexdb.repository;

import com.example.jsonh2carcomplexdb.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

}
