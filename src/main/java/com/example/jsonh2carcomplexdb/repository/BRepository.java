package com.example.jsonh2carcomplexdb.repository;

import com.example.jsonh2carcomplexdb.model.B;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BRepository extends JpaRepository<B, Long> {

}
