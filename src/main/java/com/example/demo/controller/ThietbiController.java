package com.example.demo.controller;

import com.example.demo.entity.ThietBi;
import com.example.demo.repository.ThietBiRepository;
import com.example.demo.utilities.ThietBiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/qlthietbi")
public class ThietbiController {
    @Autowired
    private ThietBiServiceImpl thietBiService;

    @Autowired
    private ThietBiRepository thietBiRepository;

    @GetMapping({"", "/"})
    public String getAllThietBi(Model model) {
        model.addAttribute("listThietBi", thietBiService.getAllThietBi());

        model.addAttribute("thietBi", new ThietBi());

        return "quanlytb";
    }
    @PostMapping("/add")
    public String themThietBi(@ModelAttribute ThietBi thietBi, Model model) {
        List<ThietBi> thietbis = thietBiService.getAllThietBi();
        for (ThietBi thietbii : thietbis) {
            if(thietbii.getMaTB() == thietBi.getMaTB()){
                // If the ID already exists, add an error message to the model
                return "redirect:/qlthietbi";
            }
        }
        thietBiService.saveThietBi(thietBi);
        return "redirect:/qlthietbi";
    }

    @PostMapping("/capnhat")
    public String capNhatThietBi(@ModelAttribute ThietBi thietBi) {
        thietBiService.saveThietBi(thietBi); // Sử dụng cùng một phương thức lưu để cập nhật
        return "redirect:/qlthietbi";
    }
    @PostMapping("/xoa")
    public String xoaThietBi(@RequestParam("ids") List<Integer> ids) {
        // Loop through the list of IDs and delete each item
        for (Integer id : ids) {

            thietBiService.deleteThietBiById(id);
        }
        return "redirect:/qlthietbi";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<ThietBi> searchThietBi(@RequestParam("keyword") String keyword) {
        return thietBiRepository.findByKeyword(keyword);
    }
}
