package com.example.demo.controller;

import com.example.demo.entity.ThanhVien;
import com.example.demo.repository.TTSDRepository;
import com.example.demo.repository.ThanhVienRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class THANHVIENController {

    @Autowired
    private ThanhVienRepository thanhVienRepository;
    @Autowired
    private TTSDRepository ttsdRepository;

    @PostMapping("/changePassword")
    public ResponseEntity<Map<String, Object>> changePassword(HttpSession session,
                                                              @RequestParam("newPassword") String newPassword,
                                                              @RequestParam("confirmPassword") String confirmPassword) {
        Map<String, Object> map = new HashMap<>();

        // Kiểm tra xác nhận mật khẩu
        if (!newPassword.equals(confirmPassword)) {
            map.put("success", false);
            map.put("message", "Mật khẩu xác nhận không khớp");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        }

        // Lấy thông tin thành viên từ session
        ThanhVien loggedInUser = (ThanhVien) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            map.put("success", false);
            map.put("message", "Không tìm thấy thông tin người dùng");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
        }


        // Cập nhật mật khẩu mới cho thành viên
        loggedInUser.setPassword(newPassword);
        thanhVienRepository.save(loggedInUser);

        map.put("success", true);
        map.put("message", "Đổi mật khẩu thành công");

        // Load lại trang user
        return ResponseEntity.ok(map);
    }
//    @GetMapping("/lichsu")
//    public String home(HttpSession session, Model model) {
//        ThanhVien thanhVien = (ThanhVien) session.getAttribute("loggedInUser");
//        model.addAttribute("ttsds", ttsdRepository.findByThanhVienMaTV(thanhVien.getMaTV()));
//        return "user";
//
//    }

}
