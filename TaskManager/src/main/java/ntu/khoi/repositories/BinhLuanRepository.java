package ntu.khoi.repositories;

import ntu.khoi.models.BinhLuan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BinhLuanRepository extends JpaRepository<BinhLuan, Integer> {
    
    List<BinhLuan> findByNhiemVu_IdOrderByThoiGianTaoAsc(Integer taskId);
}