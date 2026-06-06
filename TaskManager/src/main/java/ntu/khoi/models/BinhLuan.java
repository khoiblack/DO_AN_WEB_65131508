package ntu.khoi.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class BinhLuan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String noiDung;

    private LocalDateTime thoiGianTao = LocalDateTime.now();

    
    @ManyToOne
    @JoinColumn(name = "nhiem_vu_id")
    private NhiemVu nhiemVu;

   
    @ManyToOne
    @JoinColumn(name = "nguoi_dung_id")
    private NguoiDung nguoiDung;

    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNoiDung() { return noiDung; }
    public void setNoiDung(String noiDung) { this.noiDung = noiDung; }

    public LocalDateTime getThoiGianTao() { return thoiGianTao; }
    public void setThoiGianTao(LocalDateTime thoiGianTao) { this.thoiGianTao = thoiGianTao; }

    public NhiemVu getNhiemVu() { return nhiemVu; }
    public void setNhiemVu(NhiemVu nhiemVu) { this.nhiemVu = nhiemVu; }

    public NguoiDung getNguoiDung() { return nguoiDung; }
    public void setNguoiDung(NguoiDung nguoiDung) { this.nguoiDung = nguoiDung; }
}