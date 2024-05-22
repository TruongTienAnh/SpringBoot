package com.example.demo.utilities;

import com.example.demo.entity.ThanhVien;
import org.springframework.data.domain.Page;

import java.util.List;


public interface ThanhVienService {
    Page<ThanhVien> listAll(int numPage);

    List<ThanhVien> searchList(String keyword);

    void SaveThanhVien(ThanhVien tv);

    void DelteThanhVien(Integer id);

    ThanhVien getByMaTV(Integer id);

    boolean existsByMaTVAndPassword(int id, String password);
}
