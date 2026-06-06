package ntu.khoi.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Table(name = "nguoi_dung")
public class NguoiDung {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String username;
    private String password;
    
    @Column(name = "ho_ten")
    private String hoTen;
    
    @Column(name = "vai_tro")
    private String vaiTro; 

    @JsonIgnore
 
    @ManyToMany(mappedBy = "dsNguoiThucHien", cascade = CascadeType.ALL)
    private List<NhiemVu> dsNhiemVu;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public String getVaiTro() {
		return vaiTro;
	}

	public void setVaiTro(String vaiTro) {
		this.vaiTro = vaiTro;
	}

	public List<NhiemVu> getDsNhiemVu() {
		return dsNhiemVu;
	}

	public void setDsNhiemVu(List<NhiemVu> dsNhiemVu) {
		this.dsNhiemVu = dsNhiemVu;
	}

    
}