package com.example.demo.utilities;

import com.example.demo.entity.ThanhVien;
import com.example.demo.entity.ThietBi;
import com.example.demo.entity.ThongTinSD;
import com.example.demo.repository.TTSDRepository;
import com.example.demo.repository.ThietBiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ThietBiServiceImpl implements ThietBiService{

    @Autowired
    private ThietBiRepository ThietBiRepository;

    @Override
    public Page<ThietBi> listAll(int pageNum) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return ThietBiRepository.findAll(pageable);
    }

    @Override
    public List<ThietBi> getAllSearch(String keyword) {
        return ThietBiRepository.findByTenTBContaining(keyword);
    }

    @Override
    public List<ThietBi> getAllThietBi() {
        return ThietBiRepository.findAll();
    }
    @Override
    public ThietBi saveThietBi(ThietBi thietBi) {
        return ThietBiRepository.save(thietBi);
    }

    @Override
    public void deleteThietBiById(int id) {
        ThietBiRepository.deleteById(id);
    }



}
