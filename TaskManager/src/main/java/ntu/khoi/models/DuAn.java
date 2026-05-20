package ntu.khoi.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Table(name = "du_an")
public class DuAn {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "ten_du_an")
    private String tenDuAn;
    
    @Column(name = "mo_ta")
    private String moTa;

    @JsonIgnore
    @OneToMany(mappedBy = "duAn")
    private List<NhiemVu> dsNhiemVu;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTenDuAn() {
		return tenDuAn;
	}

	public void setTenDuAn(String tenDuAn) {
		this.tenDuAn = tenDuAn;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public List<NhiemVu> getDsNhiemVu() {
		return dsNhiemVu;
	}

	public void setDsNhiemVu(List<NhiemVu> dsNhiemVu) {
		this.dsNhiemVu = dsNhiemVu;
	}

    
}