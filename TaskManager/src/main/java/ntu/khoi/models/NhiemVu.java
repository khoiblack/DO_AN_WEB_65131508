package ntu.khoi.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "nhiem_vu")
public class NhiemVu {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "tieu_de")
    private String tieuDe;
    
    @Column(name = "noi_dung")
    private String noiDung;
    
    private LocalDate deadline; 
    
    @Column(name = "trang_thai")
    private String trangThai; 

    
    @ManyToOne
    @JoinColumn(name = "du_an_id")
    private DuAn duAn;

    
    @ManyToOne
    @JoinColumn(name = "nguoi_thuc_hien_id")
    private NguoiDung nguoiThucHien;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getTieuDe() {
		return tieuDe;
	}


	public void setTieuDe(String tieuDe) {
		this.tieuDe = tieuDe;
	}


	public String getNoiDung() {
		return noiDung;
	}


	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}


	public LocalDate getDeadline() {
		return deadline;
	}


	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}


	public String getTrangThai() {
		return trangThai;
	}


	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}


	public DuAn getDuAn() {
		return duAn;
	}


	public void setDuAn(DuAn duAn) {
		this.duAn = duAn;
	}


	public NguoiDung getNguoiThucHien() {
		return nguoiThucHien;
	}


	public void setNguoiThucHien(NguoiDung nguoiThucHien) {
		this.nguoiThucHien = nguoiThucHien;
	}

    
}