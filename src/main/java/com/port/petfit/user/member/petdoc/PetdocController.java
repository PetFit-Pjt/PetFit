package com.port.petfit.user.member.petdoc;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.port.petfit.user.member.account.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class PetdocController {

	@Autowired
	private PetdocService petdocService;

    @PostMapping("/petdoc")
    public String createPetdoc(@ModelAttribute Petdoc petdoc) {
        petdocService.createPetdoc(petdoc);
        return "petdoc";
    }

    // 전체 게시글 목록 보여주기
    @GetMapping("/petdocs")
    public String showAllPetdocs(Model model) {
        List<Petdoc> allPetdocs = petdocService.getAllPetdocs();
        model.addAttribute("posts", allPetdocs);
        return "petdoc";
    }
    
    // 내 글 목록 보여주기
    @GetMapping("/user_petdoc")
    public String showUserPetdocs(Model model, HttpSession session) {
        // 세션에서 현재 유저의 정보를 가져옴
        User currentUser = (User) session.getAttribute("user");
        // 현재 유저의 ID를 이용하여 해당 유저가 작성한 게시글만 가져옴
        List<Petdoc> userPetdocs = petdocService.getPetdocsByUserName(currentUser.getUserName());
        model.addAttribute("posts", userPetdocs);
        return "petdoc_list";
    }
    
    @GetMapping("/petdoc")
    public String showPetdocPage() {
    	return "petdoc";    			
    }

    @PostMapping("/petdoc/edit/{id}")
    @ResponseBody
    public ResponseEntity<String> updatePetdoc(@PathVariable("id") Integer id, @ModelAttribute Petdoc petdoc) {
        petdoc.setPetdocId(id);
        petdocService.editPetdoc(petdoc);
        // 게시글이 성공적으로 수정되었다는 얼럿 창을 띄우기
        String message = "게시글이 성공적으로 수정되었습니다.";
        String script = "<script>alert('" + message + "'); window.location.href='/petdocs';</script>";
        return ResponseEntity.ok().body(script);
    }

    @PostMapping("/petdoc/edit")
    public String updatePetdoc(@ModelAttribute Petdoc petdoc) {
        petdocService.editPetdoc(petdoc);
        return "redirect:/petdocs"; 
    }
    
    @GetMapping("/petdoc/delete/{id}")
    public String deletePetdoc(@PathVariable("id") Integer id) {
        petdocService.deletePetdoc(id);
        return "redirect:/petdocs";
    }
    
}