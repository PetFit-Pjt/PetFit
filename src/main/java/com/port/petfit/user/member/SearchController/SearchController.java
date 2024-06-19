package com.port.petfit.user.member.SearchController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.port.petfit.user.member.account.Hospital;
import com.port.petfit.user.member.account.HospitalService;

@Controller
public class SearchController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping("/searchHospital")
    public String search(@RequestParam(name = "query") String query, Model model) {
        List<Hospital> hospitals = hospitalService.searchByHospitalNameOrAddress(query);
        model.addAttribute("hospitals", hospitals);
        return "search_results"; // 검색 결과를 표시할 뷰 이름
    }
}
