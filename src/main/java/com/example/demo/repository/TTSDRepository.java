package com.example.demo.repository;

import com.example.demo.entity.ThanhVien;
import com.example.demo.entity.ThietBi;
import com.example.demo.entity.ThongTinSD;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface TTSDRepository extends JpaRepository<ThongTinSD, Integer> {
    @Query("select distinct tb from ThongTinSD ttsd right outer join ttsd.thietBi tb where ttsd.tgDatCho is null")
    Page<ThietBi> getAll(Pageable pageable);

    List<ThongTinSD> findByThanhVienMaTV(int maTV);

    @Query("SELECT t FROM ThongTinSD t WHERE t.thietBi.maTB = :maTB AND ((t.tgMuon <= :tgTra AND t.tgTra >= :tgMuon))")
    List<ThongTinSD> findOverlappingBookings(@Param("maTB") int maTB, @Param("tgMuon") LocalDateTime tgMuon, @Param("tgTra") LocalDateTime tgTra);
}
