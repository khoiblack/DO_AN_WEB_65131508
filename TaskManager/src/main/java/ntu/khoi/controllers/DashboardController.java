package ntu.khoi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
            java.util.List<ntu.khoi.models.DuAn> tatCaDuAn = duAnRepo.findAll();
            java.util.List<ntu.khoi.models.DuAn> duAnDangMo = new java.util.ArrayList<>();
            java.util.List<ntu.khoi.models.DuAn> duAnDaDong = new java.util.ArrayList<>(); 
            
            for (ntu.khoi.models.DuAn d : tatCaDuAn) {
                if (d.getTrangThai() == null || "OPEN".equals(d.getTrangThai())) {
                    duAnDangMo.add(d);
                } else if ("CLOSED".equals(d.getTrangThai())) {
                    duAnDaDong.add(d); 
                }
            }
            
            model.addAttribute("dsDuAn", duAnDangMo); 
            model.addAttribute("dsDuAnDaDong", duAnDaDong); 
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
 
    @GetMapping("/task/xoa")
    public String xoaNhiemVu(@org.springframework.web.bind.annotation.RequestParam("id") Integer taskId,
                             HttpSession session) {
        
        
        String role = (String) session.getAttribute("USER_ROLE");
        if (!"LEADER".equals(role)) {
            return "redirect:/dashboard"; 
        }

        nhiemVuRepo.deleteById(taskId);

        return "redirect:/dashboard";
    }
 
    @GetMapping("/task/sua")
    public String trangSuaNhiemVu(@org.springframework.web.bind.annotation.RequestParam("id") Integer taskId, 
                                  HttpSession session, Model model) {
        String role = (String) session.getAttribute("USER_ROLE");
        if (!"LEADER".equals(role)) {
            return "redirect:/dashboard";
        }

       
        ntu.khoi.models.NhiemVu nv = nhiemVuRepo.findById(taskId).orElse(null);
        if (nv == null) {
            return "redirect:/dashboard";
        }

        
        model.addAttribute("task", nv);
        model.addAttribute("dsDuAn", duAnRepo.findAll());
        model.addAttribute("dsNguoiDung", nguoiDungRepo.findAll());
        model.addAttribute("userName", session.getAttribute("USER_NAME"));
        model.addAttribute("userRole", role);

        return "sua-task"; 
    }

   
    @PostMapping("/task/capnhat")
    public String capNhatNhiemVu(@org.springframework.web.bind.annotation.RequestParam("id") Integer taskId,
                                 @org.springframework.web.bind.annotation.RequestParam("tieuDe") String tieuDe,
                                 @org.springframework.web.bind.annotation.RequestParam("noiDung") String noiDung,
                                 @org.springframework.web.bind.annotation.RequestParam("deadline") String deadlineStr,
                                 @org.springframework.web.bind.annotation.RequestParam("duAnId") Integer duAnId,
                                 @org.springframework.web.bind.annotation.RequestParam("nhanVienId") Integer nhanVienId,
                                 HttpSession session) {
        String role = (String) session.getAttribute("USER_ROLE");
        if (!"LEADER".equals(role)) {
            return "redirect:/dashboard";
        }

        ntu.khoi.models.NhiemVu nv = nhiemVuRepo.findById(taskId).orElse(null);
        if (nv != null) {
            nv.setTieuDe(tieuDe);
            nv.setNoiDung(noiDung);
            nv.setDeadline(java.time.LocalDate.parse(deadlineStr));
            nv.setDuAn(duAnRepo.findById(duAnId).orElse(null));
            nv.setNguoiThucHien(nguoiDungRepo.findById(nhanVienId).orElse(null));
            
            nhiemVuRepo.save(nv); 
        }

        return "redirect:/dashboard";
    }
 
    @GetMapping("/duan/xoa")
    public String xoaDuAn(@org.springframework.web.bind.annotation.RequestParam("id") Integer id, HttpSession session) {
        if (!"LEADER".equals(session.getAttribute("USER_ROLE"))) return "redirect:/dashboard";

        
        java.util.List<ntu.khoi.models.NhiemVu> dsTaskTrongDuAn = nhiemVuRepo.findByDuAn_Id(id);
        nhiemVuRepo.deleteAll(dsTaskTrongDuAn);

        
        duAnRepo.deleteById(id);
        
        return "redirect:/dashboard";
    }

   
    @GetMapping("/duan/sua")
    public String trangSuaDuAn(@org.springframework.web.bind.annotation.RequestParam("id") Integer id, HttpSession session, Model model) {
        if (!"LEADER".equals(session.getAttribute("USER_ROLE"))) return "redirect:/dashboard";

        ntu.khoi.models.DuAn da = duAnRepo.findById(id).orElse(null);
        if (da == null) return "redirect:/dashboard";

        model.addAttribute("duAn", da);
        model.addAttribute("userName", session.getAttribute("USER_NAME"));
        model.addAttribute("userRole", session.getAttribute("USER_ROLE"));

        return "sua-duan"; 
    }

    
    @PostMapping("/duan/capnhat")
    public String capNhatDuAn(@org.springframework.web.bind.annotation.RequestParam("id") Integer id,
                              @org.springframework.web.bind.annotation.RequestParam("tenDuAn") String tenDuAn,
                              @org.springframework.web.bind.annotation.RequestParam("moTa") String moTa,
                              HttpSession session) {
        if (!"LEADER".equals(session.getAttribute("USER_ROLE"))) return "redirect:/dashboard";

        ntu.khoi.models.DuAn da = duAnRepo.findById(id).orElse(null);
        if (da != null) {
            da.setTenDuAn(tenDuAn);
            da.setMoTa(moTa);
            duAnRepo.save(da); 
        }
        return "redirect:/dashboard";
    }
 
    @GetMapping("/duan/dong")
    public String dongDuAn(@org.springframework.web.bind.annotation.RequestParam("id") Integer id, HttpSession session) {
        if (!"LEADER".equals(session.getAttribute("USER_ROLE"))) return "redirect:/dashboard";

        ntu.khoi.models.DuAn da = duAnRepo.findById(id).orElse(null);
        if (da != null) {
            da.setTrangThai("CLOSED"); 
            duAnRepo.save(da);
        }
        return "redirect:/dashboard";
    }
 
    @GetMapping("/duan/mo")
    public String moDuAn(@org.springframework.web.bind.annotation.RequestParam("id") Integer id, HttpSession session) {
        if (!"LEADER".equals(session.getAttribute("USER_ROLE"))) return "redirect:/dashboard";

        ntu.khoi.models.DuAn da = duAnRepo.findById(id).orElse(null);
        if (da != null) {
            da.setTrangThai("OPEN"); 
            duAnRepo.save(da);
        }
        return "redirect:/dashboard";
    }
}