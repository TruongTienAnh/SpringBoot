package com.example.demo.utilities;

import com.example.demo.entity.ThietBi;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ThietBiService {
    Page<ThietBi> listAll(int pageNum);

    List<ThietBi> getAllSearch(String keyword);

    List<ThietBi> getAllThietBi();
    ThietBi saveThietBi(ThietBi thietBi);
    void deleteThietBiById(int id);

}
