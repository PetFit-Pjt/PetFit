package com.port.petfit.user.member.SearchController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public String search(@RequestParam(name = "query", required = false) String query,
                         @RequestParam(name = "page", defaultValue = "0") int page,
                         Model model) {
        Page<Hospital> hospitals;
        if (query != null && !query.isEmpty()) {
            hospitals = hospitalService.searchByHospitalNameOrAddress(query, PageRequest.of(page, 9));
        } else {
            hospitals = hospitalService.getAllHospitals(PageRequest.of(page, 9));
        }
        model.addAttribute("hospitals", hospitals);
        return "search_results"; // 검색 결과를 표시할 뷰 이름
    }

    @GetMapping("/search")
    public String searchDetailsPage(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Page<Hospital> hospitals = hospitalService.getAllHospitals(PageRequest.of(page, 9));
        model.addAttribute("hospitals", hospitals);
        return "search_details"; // 모든 병원 정보를 표시할 뷰 이름
    }

    @GetMapping("/searchDoctor")
    public String searchDoctor(@RequestParam(name = "query", required = false) String query,
                               @RequestParam(name = "page", defaultValue = "0") int page,
                               Model model) {
        Page<Hospital> hospitals;
        if (query != null && !query.isEmpty()) {
            hospitals = hospitalService.searchByDoctorNames(query, PageRequest.of(page, 9));
        } else {
            hospitals = hospitalService.getAllHospitals(PageRequest.of(page, 9));
        }
        model.addAttribute("hospitals", hospitals);
        return "searchDoctor"; // 검색 결과를 표시할 뷰 이름
    }
}
