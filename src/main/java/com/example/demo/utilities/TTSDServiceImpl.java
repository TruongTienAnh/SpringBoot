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
public class TTSDServiceImpl implements TTSDService{
    @Autowired
    private TTSDRepository TTSDRepository;
    @Autowired
    private ThietBiRepository thietBiRepository;

    @Override
    public Page<ThietBi> listAll(int PageNum) {
        int pageSize = 5;

        Pageable pageable = PageRequest.of(PageNum - 1, pageSize);

        return TTSDRepository.getAll(pageable);
    }
    public ThongTinSD datThietBi(int maTB, ThanhVien tv, LocalDateTime tgMuon, LocalDateTime tgTra) throws Exception {
        List<ThongTinSD> overlappingBookings = TTSDRepository.findOverlappingBookings(maTB, tgMuon, tgTra);
        if (!overlappingBookings.isEmpty()) {
            throw new Exception("Thiết bị này đã được đặt trong khoảng thời gian bạn chọn.");
        }

        ThietBi thietBi = thietBiRepository.findById(maTB).orElse(null);
        if (thietBi != null) {
            // Set các thông tin cho ThongTinSD
            ThongTinSD thongTinSD = new ThongTinSD();
            thongTinSD.setTgMuon(tgMuon);
            thongTinSD.setTgTra(tgTra);
            thongTinSD.setThanhVien(tv);
            thongTinSD.setThietBi(thietBi);
            thongTinSD.setTgDatCho(LocalDateTime.now());

            // Set other necessary fields
            return TTSDRepository.save(thongTinSD);
        }
        return null;
    }
}
