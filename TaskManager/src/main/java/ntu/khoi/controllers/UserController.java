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
public class UserController {

    @Autowired
    private NguoiDungRepository nguoiDungRepo;

    
    @GetMapping("/users")
    public String danhSachUser(HttpSession session, Model model) {
        String role = (String) session.getAttribute("USER_ROLE");
        if (!"LEADER".equals(role)) {
            return "redirect:/dashboard"; 
        }

        model.addAttribute("dsNguoiDung", nguoiDungRepo.findAll());
        model.addAttribute("userName", session.getAttribute("USER_NAME"));
        model.addAttribute("userRole", role);
        return "users"; 
    }

    
    @PostMapping("/users/them")
    public String themUser(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("hoTen") String hoTen,
                           @RequestParam("vaiTro") String vaiTro,
                           HttpSession session) {
        if (!"LEADER".equals(session.getAttribute("USER_ROLE"))) return "redirect:/dashboard";

        NguoiDung nd = new NguoiDung();
        nd.setUsername(username);
        nd.setPassword(password);
        nd.setHoTen(hoTen);
        nd.setVaiTro(vaiTro);
        
        nguoiDungRepo.save(nd); 
        return "redirect:/users";
    }

    
    @GetMapping("/users/xoa")
    public String xoaUser(@RequestParam("id") Integer id, HttpSession session) {
        if (!"LEADER".equals(session.getAttribute("USER_ROLE"))) return "redirect:/dashboard";

        
        Integer currentUserId = (Integer) session.getAttribute("USER_ID");
        if (id.equals(currentUserId)) {
            return "redirect:/users"; 
        }

        nguoiDungRepo.deleteById(id);
        return "redirect:/users";
    }

    
    @GetMapping("/users/sua")
    public String trangSuaUser(@RequestParam("id") Integer id, HttpSession session, Model model) {
        if (!"LEADER".equals(session.getAttribute("USER_ROLE"))) return "redirect:/dashboard";

        NguoiDung nd = nguoiDungRepo.findById(id).orElse(null);
        if (nd == null) return "redirect:/users";

        model.addAttribute("userToEdit", nd);
        model.addAttribute("userName", session.getAttribute("USER_NAME"));
        model.addAttribute("userRole", session.getAttribute("USER_ROLE"));
        return "sua-user";
    }

    
    @PostMapping("/users/capnhat")
    public String capNhatUser(@RequestParam("id") Integer id,
                              @RequestParam("hoTen") String hoTen,
                              @RequestParam("vaiTro") String vaiTro,
                              @RequestParam("password") String password,
                              HttpSession session) {
        if (!"LEADER".equals(session.getAttribute("USER_ROLE"))) return "redirect:/dashboard";

        NguoiDung nd = nguoiDungRepo.findById(id).orElse(null);
        if (nd != null) {
            nd.setHoTen(hoTen);
            nd.setVaiTro(vaiTro);
            if (password != null && !password.trim().isEmpty()) {
                nd.setPassword(password); 
            }
            nguoiDungRepo.save(nd);
        }
        return "redirect:/users";
    }
}