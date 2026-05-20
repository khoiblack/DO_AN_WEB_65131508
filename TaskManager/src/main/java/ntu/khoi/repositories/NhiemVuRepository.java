package ntu.khoi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import ntu.khoi.models.NhiemVu;

@Repository
public interface NhiemVuRepository extends JpaRepository<NhiemVu, Integer> {
    
    List<NhiemVu> findByDuAn_Id(Integer id);
    
    
    List<NhiemVu> findByNguoiThucHien_Id(Integer id);
}