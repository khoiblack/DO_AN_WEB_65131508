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
import ntu.khoi.services.AiService;

@Controller
public class DashboardController {

    @Autowired private NhiemVuRepository nhiemVuRepo;
    @Autowired private DuAnRepository duAnRepo;
    @Autowired private NguoiDungRepository nguoiDungRepo;
    @Autowired private AiService aiService;

    @GetMapping("/dashboard")
    public String showDashboard(@org.springframework.web.bind.annotation.RequestParam(value = "duAnId", required = false) Integer duAnId,
                                @org.springframework.web.bind.annotation.RequestParam(value = "keyword", required = false) String keyword,
                                @org.springframework.web.bind.annotation.RequestParam(value = "status", required = false) String status,
                                @org.springframework.web.bind.annotation.RequestParam(value = "page", defaultValue = "0") int page,
                                HttpSession session, 
                                Model model) {
        
        Integer userId = (Integer) session.getAttribute("USER_ID");
        if (userId == null) return "redirect:/login";

        String role = (String) session.getAttribute("USER_ROLE");
        model.addAttribute("userName", session.getAttribute("USER_NAME"));
        model.addAttribute("userRole", role);

        
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, 10, org.springframework.data.domain.Sort.by("id").descending());
        org.springframework.data.domain.Page<ntu.khoi.models.NhiemVu> taskPage;

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
            
            
            taskPage = nhiemVuRepo.locVaPhanTrangLeader(duAnId, keyword, status, pageable);
            model.addAttribute("selectedDuAnId", duAnId);
            java.util.List<ntu.khoi.models.NhiemVu> allTasksForStats = (duAnId != null) 
                    ? nhiemVuRepo.findByDuAn_Id(duAnId) 
                    : nhiemVuRepo.findAll();
                    
            long todoCount = allTasksForStats.stream().filter(t -> "TODO".equals(t.getTrangThai())).count();
            long doingCount = allTasksForStats.stream().filter(t -> "DOING".equals(t.getTrangThai())).count();
            long doneCount = allTasksForStats.stream().filter(t -> "DONE".equals(t.getTrangThai())).count();
            
            model.addAttribute("todoCount", todoCount);
            model.addAttribute("doingCount", doingCount);
            model.addAttribute("doneCount", doneCount);

        } else {
            
            java.util.List<ntu.khoi.models.NhiemVu> userTasks = nhiemVuRepo.findByDsNguoiThucHien_Id(userId);
            long doingCount = userTasks.stream().filter(t -> "DOING".equals(t.getTrangThai())).count();
            long dueTodayCount = userTasks.stream().filter(t -> java.time.LocalDate.now().equals(t.getDeadline()) && !"DONE".equals(t.getTrangThai())).count();
            long totalTasks = userTasks.size();
            long doneCount = userTasks.stream().filter(t -> "DONE".equals(t.getTrangThai())).count();
            int progressPercent = (totalTasks > 0) ? (int) ((doneCount * 100) / totalTasks) : 0;

            model.addAttribute("doingCount", doingCount);
            model.addAttribute("dueTodayCount", dueTodayCount);
            model.addAttribute("progressPercent", progressPercent);
            
            
            taskPage = nhiemVuRepo.locVaPhanTrangMember(userId, keyword, status, pageable);
        }

        
        model.addAttribute("dsNhiemVu", taskPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", taskPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);

        return "LEADER".equals(role) ? "dashboard" : "dashboard-member"; 
    }
 
    @PostMapping("/duan/them")
    public String themDuAnMoi(@org.springframework.web.bind.annotation.RequestParam("tenDuAn") String tenDuAn,
                              @org.springframework.web.bind.annotation.RequestParam("moTa") String moTa,
                              HttpSession session) {
        String role = (String) session.getAttribute("USER_ROLE");
        if (!"LEADER".equals(role)) return "redirect:/dashboard";
        
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
        if (session.getAttribute("USER_ID") == null) return "redirect:/login";

        ntu.khoi.models.NhiemVu nv = nhiemVuRepo.findById(taskId).orElse(null);
        if (nv != null) {
            nv.setTrangThai(status); 
            nhiemVuRepo.save(nv); 
        }
        return "redirect:/dashboard";
    }
 
    @GetMapping("/task/api/doitrangthai")
    @org.springframework.web.bind.annotation.ResponseBody 
    public org.springframework.http.ResponseEntity<String> doiTrangThaiAjax(
            @org.springframework.web.bind.annotation.RequestParam("id") Integer taskId,
            @org.springframework.web.bind.annotation.RequestParam("status") String status,
            HttpSession session) {
        
        if (session.getAttribute("USER_ID") == null) return org.springframework.http.ResponseEntity.status(401).body("Chưa đăng nhập");

        ntu.khoi.models.NhiemVu nv = nhiemVuRepo.findById(taskId).orElse(null);
        if (nv != null) {
            nv.setTrangThai(status); 
            nhiemVuRepo.save(nv); 
            return org.springframework.http.ResponseEntity.ok("Thành công"); 
        }
        return org.springframework.http.ResponseEntity.badRequest().body("Lỗi");
    }

    @PostMapping("/task/them")
    public String themNhiemVuMoi(@org.springframework.web.bind.annotation.RequestParam("tieuDe") String tieuDe,
                                 @org.springframework.web.bind.annotation.RequestParam("noiDung") String noiDung,
                                 @org.springframework.web.bind.annotation.RequestParam("deadline") String deadlineStr,
                                 @org.springframework.web.bind.annotation.RequestParam("duAnId") Integer duAnId,
                                 @org.springframework.web.bind.annotation.RequestParam(value = "nhanVienIds", required = false) java.util.List<Integer> nhanVienIds,
                                 HttpSession session,
                                 org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) { 
        
        String role = (String) session.getAttribute("USER_ROLE");
        if (!"LEADER".equals(role)) return "redirect:/dashboard";

        
        if (nhiemVuRepo.existsByTieuDeAndDuAn_Id(tieuDe, duAnId)) {
            redirectAttributes.addFlashAttribute("errorMsg", "Lỗi: Công việc '" + tieuDe + "' đã tồn tại trong dự án này!");
            return "redirect:/dashboard"; 
        }

        
        ntu.khoi.models.NhiemVu nv = new ntu.khoi.models.NhiemVu();
        nv.setTieuDe(tieuDe);
        nv.setNoiDung(noiDung);
        nv.setTrangThai("TODO"); 
        nv.setDeadline(java.time.LocalDate.parse(deadlineStr)); 
        nv.setDuAn(duAnRepo.findById(duAnId).orElse(null));
        
        if (nhanVienIds != null && !nhanVienIds.isEmpty()) {
            java.util.List<ntu.khoi.models.NguoiDung> dsNhanVien = nguoiDungRepo.findAllById(nhanVienIds);
            nv.setDsNguoiThucHien(dsNhanVien);
        }

        
        nhiemVuRepo.save(nv); 
        
        redirectAttributes.addFlashAttribute("successMsg", "Thêm công việc thành công!");
        return "redirect:/dashboard";
    }
 
    @GetMapping("/task/xoa")
    public String xoaNhiemVu(@org.springframework.web.bind.annotation.RequestParam("id") Integer taskId, 
                             HttpSession session,
                             org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
                             
        String role = (String) session.getAttribute("USER_ROLE");
        if (!"LEADER".equals(role)) return "redirect:/dashboard"; 

        ntu.khoi.models.NhiemVu nv = nhiemVuRepo.findById(taskId).orElse(null);
        if (nv != null) {
            
            if (nv.getDsNguoiThucHien() != null) {
                nv.getDsNguoiThucHien().clear();
                nhiemVuRepo.save(nv);
            }
            
            try {
               
                nhiemVuRepo.delete(nv);
                redirectAttributes.addFlashAttribute("successMsg", "Đã xóa công việc thành công!");
            } catch (Exception e) {
                
                redirectAttributes.addFlashAttribute("errorMsg", "⛔ Không thể xóa! Công việc này đang có dữ liệu trao đổi (bình luận). Vui lòng vào chi tiết xóa bình luận trước.");
            }
        }
        return "redirect:/dashboard";
    }
 
    @GetMapping("/task/sua")
    public String trangSuaNhiemVu(@org.springframework.web.bind.annotation.RequestParam("id") Integer taskId, HttpSession session, Model model) {
        String role = (String) session.getAttribute("USER_ROLE");
        if (!"LEADER".equals(role)) return "redirect:/dashboard";

        ntu.khoi.models.NhiemVu nv = nhiemVuRepo.findById(taskId).orElse(null);
        if (nv == null) return "redirect:/dashboard";

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
                                 @org.springframework.web.bind.annotation.RequestParam(value = "nhanVienIds", required = false) java.util.List<Integer> nhanVienIds,
                                 HttpSession session) {
        
        String role = (String) session.getAttribute("USER_ROLE");
        if (!"LEADER".equals(role)) return "redirect:/dashboard";

        ntu.khoi.models.NhiemVu nv = nhiemVuRepo.findById(taskId).orElse(null);
        if (nv != null) {
            nv.setTieuDe(tieuDe);
            nv.setNoiDung(noiDung);
            nv.setDeadline(java.time.LocalDate.parse(deadlineStr));
            nv.setDuAn(duAnRepo.findById(duAnId).orElse(null));
            
            
            if (nhanVienIds != null && !nhanVienIds.isEmpty()) {
                java.util.List<ntu.khoi.models.NguoiDung> dsNhanVien = nguoiDungRepo.findAllById(nhanVienIds);
                nv.setDsNguoiThucHien(dsNhanVien);
            } else {
                nv.getDsNguoiThucHien().clear(); 
            }
            
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
    @GetMapping("/task/ai-goi-y")
    @org.springframework.web.bind.annotation.ResponseBody
    public String goiYAI(@org.springframework.web.bind.annotation.RequestParam("tieuDe") String tieuDe,
                         @org.springframework.web.bind.annotation.RequestParam("duAnId") Integer duAnId) {
        
        
        ntu.khoi.models.DuAn da = duAnRepo.findById(duAnId).orElse(null);
        String tenDuAn = (da != null) ? da.getTenDuAn() : "Dự án mặc định";
        String moTaDuAn = (da != null) ? da.getMoTa() : "Không có mô tả";
        
        
        return aiService.phanRaCongViec(tieuDe, tenDuAn, moTaDuAn);
    }
    }