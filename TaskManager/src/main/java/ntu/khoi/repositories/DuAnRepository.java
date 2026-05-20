package ntu.khoi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ntu.khoi.models.DuAn;

@Repository
public interface DuAnRepository extends JpaRepository<DuAn, Integer> {
}