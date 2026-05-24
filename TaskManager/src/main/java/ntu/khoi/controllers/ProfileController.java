package ntu.khoi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import ntu.khoi.models.NguoiDung;
import ntu.khoi.repositories.NguoiDungRepository;

@Controller
public class ProfileController {

    @Autowired
    private NguoiDungRepository nguoiDungRepo;

    
    @GetMapping("/profile")
    public String xemProfile(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("USER_ID");
        if (userId == null) {
            return "redirect:/login"; 
        }

        
        NguoiDung currentUser = nguoiDungRepo.findById(userId).orElse(null);
        if (currentUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", currentUser);
        model.addAttribute("userName", session.getAttribute("USER_NAME"));
        model.addAttribute("userRole", session.getAttribute("USER_ROLE"));

        return "profile";
    }

    
    @PostMapping("/profile/capnhat")
    public String capNhatProfile(@RequestParam("hoTen") String hoTen,
                                 @RequestParam(value = "password", required = false) String password,
                                 HttpSession session) {
        
        Integer userId = (Integer) session.getAttribute("USER_ID");
        if (userId == null) return "redirect:/login";

        NguoiDung currentUser = nguoiDungRepo.findById(userId).orElse(null);
        if (currentUser != null) {
            
            currentUser.setHoTen(hoTen);
            if (password != null && !password.trim().isEmpty()) {
                currentUser.setPassword(password);
            }
            
            nguoiDungRepo.save(currentUser);
            
            
            session.setAttribute("USER_NAME", hoTen); 
        }

        
        return "redirect:/profile";
    }
}