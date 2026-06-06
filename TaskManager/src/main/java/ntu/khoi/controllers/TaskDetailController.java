package ntu.khoi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

import ntu.khoi.models.BinhLuan;
import ntu.khoi.models.NhiemVu;
import ntu.khoi.repositories.BinhLuanRepository;
import ntu.khoi.repositories.NguoiDungRepository;
import ntu.khoi.repositories.NhiemVuRepository;

@Controller
public class TaskDetailController {

    @Autowired private NhiemVuRepository nhiemVuRepo;
    @Autowired private BinhLuanRepository binhLuanRepo;
    @Autowired private NguoiDungRepository nguoiDungRepo;

    
    @GetMapping("/task/chitiet")
    public String xemChiTietTask(@RequestParam("id") Integer id, HttpSession session, Model model) {
        if (session.getAttribute("USER_ID") == null) return "redirect:/login";

        NhiemVu task = nhiemVuRepo.findById(id).orElse(null);
        if (task == null) return "redirect:/dashboard";

        model.addAttribute("task", task);
        model.addAttribute("dsBinhLuan", binhLuanRepo.findByNhiemVu_IdOrderByThoiGianTaoAsc(id));
        model.addAttribute("userName", session.getAttribute("USER_NAME"));
        model.addAttribute("userRole", session.getAttribute("USER_ROLE"));
        model.addAttribute("userId", session.getAttribute("USER_ID")); 

        return "task-detail";
    }

    
    @PostMapping("/task/comment/them")
    public String themComment(@RequestParam("taskId") Integer taskId,
                              @RequestParam("noiDung") String noiDung,
                              HttpSession session) {
        Integer userId = (Integer) session.getAttribute("USER_ID");
        if (userId == null) return "redirect:/login";

        if (noiDung != null && !noiDung.trim().isEmpty()) {
            BinhLuan cmt = new BinhLuan();
            cmt.setNoiDung(noiDung);
            cmt.setThoiGianTao(java.time.LocalDateTime.now());
            cmt.setNhiemVu(nhiemVuRepo.findById(taskId).orElse(null));
            cmt.setNguoiDung(nguoiDungRepo.findById(userId).orElse(null));
            
            binhLuanRepo.save(cmt);
        }
        
        
        return "redirect:/task/chitiet?id=" + taskId;
    }
}