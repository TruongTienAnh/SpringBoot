package com.example.demo.repository;

import com.example.demo.entity.ThongTinSD;
import com.example.demo.entity.xuly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
@Component
@Repository
public interface xulyRepository extends JpaRepository<xuly,Integer> {
    xuly findByMaXL(int MaXL);
    @Query("SELECT x FROM xuly x WHERE CAST(x.maTV AS string) LIKE CONCAT('%', :maTV, '%')")
    List<xuly> searchMaTV(String maTV);
    List<xuly> findByMaTV(int maTV);

}

