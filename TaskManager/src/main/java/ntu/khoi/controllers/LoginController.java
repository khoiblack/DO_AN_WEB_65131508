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
public class LoginController {

    @Autowired
    private NguoiDungRepository nguoiDungRepo;

    
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    
    @PostMapping("/login")
    public String processLogin(@RequestParam("username") String username, 
                               @RequestParam("password") String password, 
                               HttpSession session, 
                               Model model) {
        
        
        NguoiDung user = nguoiDungRepo.findByUsername(username);
        
        
        if (user != null && user.getPassword().equals(password)) {
            
            session.setAttribute("USER_ID", user.getId());
            session.setAttribute("USER_NAME", user.getHoTen());
            session.setAttribute("USER_ROLE", user.getVaiTro());
            
            
            return "redirect:/dashboard"; 
        }
        
        
        model.addAttribute("error", "Sai tài khoản hoặc mật khẩu. Vui lòng thử lại!");
        return "login";
    }

    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/login"; 
    }
}