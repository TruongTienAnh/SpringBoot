package com.example.demo.controller;

import com.example.demo.repository.TTSDRepository;
import com.example.demo.repository.ThietBiRepository;
import com.example.demo.utilities.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.example.demo.repository.ThanhVienRepository;
import com.example.demo.repository.xulyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.jackson.JsonMixinModuleEntries;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.ThanhVien;
import com.example.demo.entity.xuly;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.example.demo.entity.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class Homecontroller {
    private boolean isLoggedIn = false;

    @Autowired
    private ThanhVienRepository thanhVienRepository;
    @Autowired
    private JsonMixinModuleEntries jsonMixinModuleEntries;

    @Autowired
    private xulyRepository xuLyRepository;
    @Autowired
    private ThietBiRepository thietBiRepository;
    @Autowired
    private ThietBiServiceImpl thietBiService;
    @Autowired
    private TTSDRepository ttsdRepository;
    @Autowired
    private xulyRepository xulyRepository;
    @Autowired
    private TTSDServiceImpl ttsdService;


    @GetMapping("/login")
    public String LoginPage() {
        return "login";
    }
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
    @GetMapping("/Quenmatkhau")
    public String QuenmatkhauPage() {
        return "Quenmatkhau";
    }
    @GetMapping("/user")
    public String userPage(HttpSession session, Model model) {
        if (!isLoggedIn || session.getAttribute("loggedInUser") == null) {
            return "redirect:/login"; // Chuyển hướng nếu chưa đăng nhập
        }
        // Hiển thị trang người dùng nếu đã đăng nhập
        ThanhVien thanhVien = (ThanhVien) session.getAttribute("loggedInUser");
        model.addAttribute("thanhVien", thanhVien);
        model.addAttribute("ttsds", ttsdRepository.findByThanhVienMaTV(thanhVien.getMaTV()));
        model.addAttribute("TTXulys", xulyRepository.findByMaTV(thanhVien.getMaTV()));
        return "user";
    }

    @GetMapping("/datcho")
    public String datchoPage(Model model, HttpSession session) {
        if (!isLoggedIn || session.getAttribute("loggedInUser") == null) {
            return "redirect:/login"; // Chuyển hướng nếu chưa đăng nhập
        }
        // Hiển thị trang người dùng nếu đã đăng nhập
        ThanhVien thanhVien = (ThanhVien) session.getAttribute("loggedInUser");
        model.addAttribute("thanhVien", thanhVien);
        List<ThietBi> deviceList = thietBiRepository.findAll();
        model.addAttribute("DeviceList", deviceList);

        return "datcho";
    }

    @PostMapping("/dat")
    public String datThietBi(@RequestParam int maTB,
                             @RequestParam String tgMuon,
                             @RequestParam String tgTra,
                             HttpSession session) {
        System.out.println(tgMuon);
        System.out.println(tgTra);
        LocalDateTime tgMuonDateTime = LocalDateTime.parse(tgMuon + "T00:00:00");
        LocalDateTime tgTraDateTime = LocalDateTime.parse(tgTra + "T00:00:00");
        System.out.println(tgMuonDateTime);
        System.out.println(tgTraDateTime);
        ThanhVien thanhVien = (ThanhVien) session.getAttribute("loggedInUser");

        try {
            ttsdService.datThietBi(maTB, thanhVien, tgMuonDateTime, tgTraDateTime);
        } catch (Exception e) {
            // You can customize the response based on your needs
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return "redirect:/datcho";
    }

//    thanhvien
    @GetMapping("/qlthanhvien")
    public String qltvPage(Model model) {
        List<ThanhVien> memberList = thanhVienRepository.findAll();
        model.addAttribute("memberList", memberList);

        return "qlthanhvien";
}


//add thanhvien
    @PostMapping(name = "/themThanhVien")
    @ResponseBody
    public Map<String, Object> themTV(@RequestBody Map<String, String> thanhVien) {
        Map<String, Object> map = new HashMap<>();

        // Kiểm tra các trường dữ liệu không được để trống
        if (thanhVien.get("maTV") == null || thanhVien.get("maTV").isEmpty() ||
                thanhVien.get("hoTen") == null || thanhVien.get("hoTen").isEmpty() ||
                thanhVien.get("khoa") == null || thanhVien.get("khoa").isEmpty() ||
                thanhVien.get("nganh") == null || thanhVien.get("nganh").isEmpty() ||
                thanhVien.get("sdt") == null || thanhVien.get("sdt").isEmpty() ||
                thanhVien.get("password") == null || thanhVien.get("password").isEmpty() ||
                thanhVien.get("email") == null || thanhVien.get("email").isEmpty()) {

            map.put("success", false);
            map.put("message", "Không được bỏ trống");
            return map;
        }

        try {
            int maTV = Integer.parseInt(thanhVien.get("maTV"));
            String tenTV = thanhVien.get("hoTen"); // Sửa từ "tenTV" thành "hoTen"
            String khoa = thanhVien.get("khoa");
            String nganh = thanhVien.get("nganh");
            String sdt = thanhVien.get("sdt");
            String password = thanhVien.get("password");
            String email = thanhVien.get("email");
            String gmailRegex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";

            // Kiểm tra định dạng email
            if (!email.matches(gmailRegex)) {
                map.put("success", false);
                map.put("message", "Email không đúng định dạng");
                return map;
            }
            // Kiểm tra xem mã thành viên, email, số điện thoại và tên thành viên có tồn tại hay không
            ThanhVien existMaTV = thanhVienRepository.findByMaTV(maTV);
            ThanhVien exitsEmail = thanhVienRepository.findByEmail(email);
            ThanhVien exitsPhone = thanhVienRepository.findBySdt(sdt);

            if (existMaTV != null) {
                map.put("message", "Mã thành viên đã tồn tại");
                map.put("success", false);
                return map;
            }
            if (exitsEmail != null) {
                map.put("message", "Email đã tồn tại");
                map.put("success", false);
                return map;
            }
            if (exitsPhone != null) {
                map.put("message", "Số điện thoại đã tồn tại");
                map.put("success", false);
                return map;
            }

            // Tạo mới thành viên và lưu vào cơ sở dữ liệu
            ThanhVien newMember = new ThanhVien(maTV, tenTV, khoa, nganh, sdt, password, email);
            ThanhVien addedMember = thanhVienRepository.save(newMember);

            map.put("success", true);
            map.put("message", "Thêm thành viên thành công");
        } catch (NumberFormatException e) {
            map.put("success", false);
            map.put("message", "Mã thành viên phải là một số nguyên hợp lệ");
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "Có lỗi xảy ra: " + e.getMessage());
        }

        return map;
    }


    @PostMapping("/deleteMember")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteMembersByIds(@RequestBody List<Integer> maTVList) {
        Map<String, Object> response = new HashMap<>();

        try {
            if (maTVList == null || maTVList.isEmpty()) {
                response.put("success", false);
                response.put("message", "Danh sách mã thành viên rỗng hoặc không tồn tại");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            List<String> failedToDelete = new ArrayList<>();
            for (Integer maTV : maTVList) {
                ThanhVien thanhVien = thanhVienRepository.findByMaTV(maTV);
                if (thanhVien == null) {
                    failedToDelete.add(maTV.toString());
                } else {
                    thanhVienRepository.delete(thanhVien);
                }
            }

            if (failedToDelete.isEmpty()) {
                response.put("success", true);
                response.put("message", "Xóa thành công tất cả thành viên được chọn");
            } else {
                response.put("success", false);
                response.put("message", "Không thể xóa các thành viên có mã: " + String.join(", ", failedToDelete));
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Xóa thất bại");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/editMember")
    @ResponseBody
    public Map<String, Object> editMember(@RequestBody Map<String, String> thanhVien) {
        Map<String, Object> map = new HashMap<>();

        // Kiểm tra dữ liệu đầu vào
        if (thanhVien.get("maTV") == null || thanhVien.get("maTV").isEmpty() ||
                thanhVien.get("tenTV") == null || thanhVien.get("tenTV").isEmpty() ||
                thanhVien.get("khoa") == null || thanhVien.get("khoa").isEmpty() ||
                thanhVien.get("nganh") == null || thanhVien.get("nganh").isEmpty() ||
                thanhVien.get("sdt") == null || thanhVien.get("sdt").isEmpty() ||
                thanhVien.get("password") == null || thanhVien.get("password").isEmpty() ||
                thanhVien.get("email") == null || thanhVien.get("email").isEmpty()) {

            map.put("success", false);
            map.put("message", "Không được để trống các trường");
            return map;
        }

        int maTV = Integer.parseInt((String) thanhVien.get("maTV"));
        String tenTV = String.valueOf(thanhVien.get("tenTV"));
        String khoa = String.valueOf(thanhVien.get("khoa"));
        String nganh = String.valueOf(thanhVien.get("nganh"));
        String sdt = String.valueOf(thanhVien.get("sdt"));
        String password = String.valueOf(thanhVien.get("password"));
        String email = String.valueOf(thanhVien.get("email"));
        String gmailRegex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";

        // Kiểm tra định dạng email
        if (!email.matches(gmailRegex)) {
            map.put("success", false);
            map.put("message", "Email không đúng định dạng");
            return map;
        }
        ThanhVien existingMemberByMaTV = thanhVienRepository.findByMaTV(maTV);
        if (existingMemberByMaTV == null) {
            map.put("message", "Không tìm thấy thành viên với mã " + maTV);
            map.put("success", false);
            return map;
        }

        ThanhVien updateMember = new ThanhVien(maTV, tenTV, khoa, nganh, sdt, password, email);
        ThanhVien result = thanhVienRepository.save(updateMember);

        map.put("success", result != null);
        if (result != null) {
            map.put("message", "Cập nhật thành viên thành công");
        }
        return map;
    }


    @PostMapping("/searchMember")
    @ResponseBody
    @JsonIgnoreProperties
    public List<ThanhVien> searchDevice(@RequestBody Map<String, String> searchData) {
        String searchValue = searchData.get("searchValue");
        if (searchValue.isEmpty()) {
            return thanhVienRepository.findAll();
        }
        List<ThanhVien> searchResult = thanhVienRepository.findByKeyword(searchValue);
        return searchResult;
    }

    @PostMapping("/thanhVienExcel")
    @ResponseBody
    public Map<String, Object> fileExcelUpload(@RequestParam("excelBtn") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        if (!file.isEmpty()) {
            File convertFile = null;
            try {
                convertFile = File.createTempFile("uploaded", ".xlsx");
                try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                    fos.write(file.getBytes());
                }

                List<List<String>> data = ExcelUtil.readExcel(convertFile.getAbsolutePath(), 0);
                List<ThanhVien> thanhvienList = thanhVienExcelUtil.convertToThanhVienList(data);

                for (ThanhVien thanhvien : thanhvienList) {
                    Optional<ThanhVien> existingThanhVien = thanhVienRepository.findById(thanhvien.getMaTV());
                    if (existingThanhVien.isPresent()) {
                        ThanhVien existing = existingThanhVien.get();
                        // Update existing record with new values
                        existing.setHoTen(thanhvien.getHoTen());
                        existing.setKhoa(thanhvien.getKhoa());
                        existing.setNganh(thanhvien.getNganh());
                        existing.setSdt(thanhvien.getSdt());
                        existing.setPassword(thanhvien.getPassword());
                        existing.setEmail(thanhvien.getEmail());
                        thanhVienRepository.save(existing);
                    } else {
                        // Save new record
                        thanhVienRepository.save(thanhvien);
                    }
                }

                map.put("success", true);
                map.put("message", "Nhập excel thành công");
            } catch (Exception e) {
                e.printStackTrace();
                map.put("success", false);
                map.put("message", "Lỗi khi nhập excel: " + e.getMessage());
            } finally {
                if (convertFile != null && convertFile.exists()) {
                    convertFile.delete();
                }
            }
            return map;
        }
        map.put("success", false);
        map.put("message", "No file uploaded");
        return map;
    }





    @GetMapping("/xulyvp")
    public String xlvpPage(Model model) {
        List<xuly> xulyList = xuLyRepository.findAll();
        model.addAttribute("xulyList", xulyList);
        return "xulyvp";
    }
    @GetMapping("/thongke")
    public String thongkePage() {
        return "thongke";
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute ThanhVien thanhVien, @RequestParam("user") String username,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("nganh") String nganh,
            @RequestParam("khoa") String khoa,
            @RequestParam("sdt") String sdt,
            @RequestParam("email") String email) {
        // Xử lý logic đăng ký người dùng ở đây
        ;
        thanhVien = new ThanhVien(Integer.parseInt(username), name, khoa, nganh, sdt, password, email);
        // Sau khi đăng ký thành công, chuyển hướng về trang đăng nhập
        thanhVienRepository.save(thanhVien);
        return "redirect:/login";
    }
    @PostMapping("/login")
    public String loginSuccess(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               HttpSession session) {
        try {
            // Tìm kiếm thành viên dựa trên mã thành viên (username)
            int maTV = Integer.parseInt(username); // Chuyển đổi username thành int
            ThanhVien thanhVien = thanhVienRepository.findByMaTVAndPassword(maTV, password);
            if (thanhVien != null) {
                session.setAttribute("loggedInUser", thanhVien); // Lưu thông tin người dùng đã đăng nhập vào session
                isLoggedIn = true;
                return "redirect:/user";
            } else {
                return "redirect:/login"; // Trả về trang đăng nhập với thông báo lỗi

            }
        } catch (NumberFormatException e) {
            return "redirect:/login"; // Trả về trang đăng nhập với thông báo lỗi
        }
    }
/*    int id = -1;
    @Autowired
    private ThanhVienService thanhVienService;

    @GetMapping
    public String HomePage() {
        return "login";
    }

    @GetMapping("/register")
    public String RegisterPage(Model model) {
        model.addAttribute("ThanhVien", new ThanhVien());
        return "register";
    }

    @GetMapping("/login")
    public String LoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String Login(@RequestParam String username, @RequestParam String password) {
        id = Integer.parseInt(username);
        if(thanhVienService.existsByMaTVAndPassword(id, password)) {
            return "redirect:/user";
        }
        else {
            return "redirect:/login";
        }
    }

    @PostMapping("/register")
    public String Register(@Valid @ModelAttribute("ThanhVien") ThanhVien tv, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "register";
        }
        thanhVienService.SaveThanhVien(tv);
        return "redirect:/login";
    }

*/
}
