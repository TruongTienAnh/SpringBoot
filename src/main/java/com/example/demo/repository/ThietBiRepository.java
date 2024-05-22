package com.example.demo.repository;

import com.example.demo.entity.ThietBi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ThietBiRepository extends JpaRepository<ThietBi, Integer> {

    List<ThietBi> findByTenTBContaining(String keyword);

    @Query("SELECT tb FROM ThietBi tb WHERE tb.tenTB LIKE %:keyword%")
    List<ThietBi> findByKeyword(String keyword);
}
