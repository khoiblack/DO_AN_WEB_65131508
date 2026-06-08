package ntu.khoi.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import ntu.khoi.models.NhiemVu;

@Repository
public interface NhiemVuRepository extends JpaRepository<NhiemVu, Integer> {
    
    List<NhiemVu> findByDuAn_Id(Integer id);
    
    
    List<NhiemVu> findByDsNguoiThucHien_Id(Integer id);
    boolean existsByTieuDeAndDuAn_Id(String tieuDe, Integer duAnId);
 
    @Query("SELECT n FROM NhiemVu n WHERE " +
           "(:duAnId IS NULL OR n.duAn.id = :duAnId) AND " +
           "(:keyword IS NULL OR :keyword = '' OR LOWER(n.tieuDe) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "(:status IS NULL OR :status = '' OR n.trangThai = :status)")
    Page<NhiemVu> locVaPhanTrangLeader(@Param("duAnId") Integer duAnId, 
                                       @Param("keyword") String keyword, 
                                       @Param("status") String status, 
                                       Pageable pageable);

    
    @Query("SELECT n FROM NhiemVu n JOIN n.dsNguoiThucHien nd WHERE nd.id = :userId AND " +
           "(:keyword IS NULL OR :keyword = '' OR LOWER(n.tieuDe) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "(:status IS NULL OR :status = '' OR n.trangThai = :status)")
    Page<NhiemVu> locVaPhanTrangMember(@Param("userId") Integer userId, 
                                       @Param("keyword") String keyword, 
                                       @Param("status") String status, 
                                       Pageable pageable);
}