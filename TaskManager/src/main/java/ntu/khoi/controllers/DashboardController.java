package ntu.khoi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import ntu.khoi.repositories.DuAnRepository;
import ntu.khoi.repositories.NguoiDungRepository;
import ntu.khoi.repositories.NhiemVuRepository;

@Controller
public class DashboardController {

    @Autowired
    private NhiemVuRepository nhiemVuRepo;

    @Autowired
    private DuAnRepository duAnRepo;
    @Autowired
    private NguoiDungRepository nguoiDungRepo;

    @GetMapping("/dashboard")
    public String showDashboard(@org.springframework.web.bind.annotation.RequestParam(value = "duAnId", required = false) Integer duAnId, 
                                HttpSession session, 
                                Model model) {
        
        Integer userId = (Integer) session.getAttribute("USER_ID");
        if (userId == null) {
            return "redirect:/login";
        }

        String role = (String) session.getAttribute("USER_ROLE");
        String name = (String) session.getAttribute("USER_NAME");
        
        model.addAttribute("userName", name);
        model.addAttribute("userRole", role);

        
        if ("LEADER".equals(role)) {
            model.addAttribute("dsDuAn", duAnRepo.findAll());
            model.addAttribute("dsNguoiDung", nguoiDungRepo.findAll()); 
            
            if (duAnId != null) {
                
                model.addAttribute("dsNhiemVu", nhiemVuRepo.findByDuAn_Id(duAnId));
                model.addAttribute("selectedDuAnId", duAnId);
            } else {
                
                model.addAttribute("dsNhiemVu", nhiemVuRepo.findAll());
            }
        } else {
            
            model.addAttribute("dsNhiemVu", nhiemVuRepo.findByNguoiThucHien_Id(userId));
        }

        return "dashboard"; 
    }
 
    @org.springframework.web.bind.annotation.PostMapping("/duan/them")
    public String themDuAnMoi(@org.springframework.web.bind.annotation.RequestParam("tenDuAn") String tenDuAn,
                              @org.springframework.web.bind.annotation.RequestParam("moTa") String moTa,
                              HttpSession session) {
        
        String role = (String) session.getAttribute("USER_ROLE");
        if (!"LEADER".equals(role)) {
            return "redirect:/dashboard";
        }
        
        
        ntu.khoi.models.DuAn da = new ntu.khoi.models.DuAn();
        da.setTenDuAn(tenDuAn);
        da.setMoTa(moTa);
        duAnRepo.save(da); 
        
        return "redirect:/dashboard"; 
    }

    
    @GetMapping("/task/doitrangthai")
    public String doiTrangThaiTask(@org.springframework.web.bind.annotation.RequestParam("id") Integer taskId,
                                   @org.springframework.web.bind.annotation.RequestParam("status") String status,
                                   HttpSession session) {
        
        if (session.getAttribute("USER_ID") == null) {
            return "redirect:/login";
        }

        
        ntu.khoi.models.NhiemVu nv = nhiemVuRepo.findById(taskId).orElse(null);
        if (nv != null) {
            nv.setTrangThai(status); 
            nhiemVuRepo.save(nv); 
        }

        return "redirect:/dashboard";
    }
 
    @org.springframework.web.bind.annotation.PostMapping("/task/them")
    public String themNhiemVuMoi(@org.springframework.web.bind.annotation.RequestParam("tieuDe") String tieuDe,
                                 @org.springframework.web.bind.annotation.RequestParam("noiDung") String noiDung,
                                 @org.springframework.web.bind.annotation.RequestParam("deadline") String deadlineStr,
                                 @org.springframework.web.bind.annotation.RequestParam("duAnId") Integer duAnId,
                                 @org.springframework.web.bind.annotation.RequestParam("nhanVienId") Integer nhanVienId,
                                 HttpSession session) {
        
        
        String role = (String) session.getAttribute("USER_ROLE");
        if (!"LEADER".equals(role)) {
            return "redirect:/dashboard";
        }

        
        ntu.khoi.models.NhiemVu nv = new ntu.khoi.models.NhiemVu();
        nv.setTieuDe(tieuDe);
        nv.setNoiDung(noiDung);
        nv.setTrangThai("TODO"); 
        nv.setDeadline(java.time.LocalDate.parse(deadlineStr)); 

        
        nv.setDuAn(duAnRepo.findById(duAnId).orElse(null));
        
        
        nv.setNguoiThucHien(nguoiDungRepo.findById(nhanVienId).orElse(null));

        nhiemVuRepo.save(nv); 

        return "redirect:/dashboard";
    }
}