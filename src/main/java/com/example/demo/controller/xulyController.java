package com.example.demo.controller;

import com.example.demo.entity.ThanhVien;
import com.example.demo.repository.ThanhVienRepository;
import  com.example.demo.repository.xulyRepository;
import com.example.demo.entity.xuly;
import com.example.demo.utilities.XulyService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class xulyController {
    @Autowired
    private XulyService xulyService;
    @Autowired
    private ThanhVienRepository thanhVienRepository;
    @Autowired
    private xulyRepository xuLyRepository;
    @PostMapping("/searchVP")
    @ResponseBody
    @JsonIgnoreProperties
    public List<xuly> searchVP(@RequestBody Map<String, String> searchData) {
        String searchValue = searchData.get("searchValue");
        if(searchValue.isEmpty()){
            return xuLyRepository.findAll();
        }
        return this.xulyService.searchMaTV(searchValue);
    }
    @PostMapping("/themvp")
    @ResponseBody
    public Map<String, Object> themVP(@RequestBody Map<String, String> viPham) {
        Map<String, Object> map = new HashMap<>();
        if (viPham.get("maXL") == null || viPham.get("maXL").isEmpty() ||
                viPham.get("maTV") == null || viPham.get("maTV").isEmpty() ||
                viPham.get("hinhThuc") == null || viPham.get("hinhThuc").isEmpty() ||
                viPham.get("date") == null || viPham.get("date").isEmpty() ||
                viPham.get("status") == null || viPham.get("status").isEmpty()) {
            map.put("success", false);
            map.put("message","Nhập đầy đủ thông tin");
            return map;
        }
        try {
            int MaXL = Integer.parseInt(viPham.get("maXL"));
            int maTV = Integer.parseInt(viPham.get("maTV"));
            String hinhThuc = viPham.get("hinhThuc");
            int tien = Integer.parseInt(viPham.get("tien"));
            String[] date = viPham.get("date").split("T");
            String status = viPham.get("status");

            xuly existMaXL = xuLyRepository.findByMaXL(MaXL);

            if (existMaXL != null) {
                map.put("message", "Mã xử lý đã tồn tại");
                map.put("success", false);
                return map;
            }
            xuly newViPham;
            if(status.equals("hoanThanh"))
                newViPham = new xuly(MaXL, maTV,hinhThuc,tien, date[0] +" "+ date[1]+":00",1 );
            else newViPham = new xuly(MaXL, maTV,hinhThuc,tien, date[0] +" "+ date[1]+":00",0 );
            xuLyRepository.save(newViPham);
            map.put("success", true);
            map.put("message", "Thêm vi phạm thành công");
        } catch (NumberFormatException e) {
            map.put("success", false);
            map.put("message", e.getMessage());
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "Có lỗi xảy ra: " + e.getMessage());
        }

        return map;
    }
    @PostMapping("/suavp")
    @ResponseBody
    public Map<String, Object> suaVP(@RequestBody Map<String, String> viPham) {
        Map<String, Object> map = new HashMap<>();
        if (viPham.get("maXL") == null || viPham.get("maXL").isEmpty() ||
                viPham.get("maTV") == null || viPham.get("maTV").isEmpty() ||
                viPham.get("hinhThuc") == null || viPham.get("hinhThuc").isEmpty() ||
                viPham.get("date") == null || viPham.get("date").isEmpty() ||
                viPham.get("status") == null || viPham.get("status").isEmpty()) {
            map.put("success", false);
            map.put("message","Nhập đầy đủ thông tin");
            return map;
        }
        try {
            int MaXL = Integer.parseInt(viPham.get("maXL"));
            int maTV = Integer.parseInt(viPham.get("maTV"));
            String hinhThuc = viPham.get("hinhThuc");
            int tien = Integer.parseInt(viPham.get("tien"));
            String[] date = viPham.get("date").split("T");
            String status = viPham.get("status");

            ThanhVien existMaTV = thanhVienRepository.findByMaTV(maTV);

            if (existMaTV == null) {
                map.put("message", "Không tìm thấy mã thành viên");
                map.put("success", false);
                return map;
            }
            xuly newViPham;
            if(status.equals("hong"))
                newViPham = new xuly(MaXL, maTV,hinhThuc,tien, date[0] +" "+ date[1]+":00",1 );
            else newViPham = new xuly(MaXL, maTV,hinhThuc,tien, date[0] +" "+ date[1]+":00",0 );
            xuLyRepository.save(newViPham);
            map.put("success", true);
            map.put("message", "sửa thành công");
        } catch (NumberFormatException e) {
            map.put("success", false);
            map.put("message", e.getMessage());
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "Có lỗi xảy ra: " + e.getMessage());
        }

        return map;
    }
}
