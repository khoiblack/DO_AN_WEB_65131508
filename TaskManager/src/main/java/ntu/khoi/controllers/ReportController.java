package ntu.khoi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import ntu.khoi.models.NhiemVu;
import ntu.khoi.repositories.NhiemVuRepository;

@Controller
public class ReportController {

    @Autowired
    private NhiemVuRepository nhiemVuRepo;

    @GetMapping("/reports")
    public String xemThongKe(HttpSession session, Model model) {
        
        String role = (String) session.getAttribute("USER_ROLE");
        if (!"LEADER".equals(role)) {
            return "redirect:/dashboard";
        }

        
        List<NhiemVu> dsTask = nhiemVuRepo.findAll();
        
        long todo = 0, doing = 0, done = 0;
        
        for (NhiemVu nv : dsTask) {
            if ("TODO".equals(nv.getTrangThai())) todo++;
            else if ("DOING".equals(nv.getTrangThai())) doing++;
            else if ("DONE".equals(nv.getTrangThai())) done++;
        }

        
        model.addAttribute("tongSo", dsTask.size());
        model.addAttribute("todo", todo);
        model.addAttribute("doing", doing);
        model.addAttribute("done", done);
        
        model.addAttribute("userName", session.getAttribute("USER_NAME"));
        model.addAttribute("userRole", role);

        return "reports"; 
    }
}