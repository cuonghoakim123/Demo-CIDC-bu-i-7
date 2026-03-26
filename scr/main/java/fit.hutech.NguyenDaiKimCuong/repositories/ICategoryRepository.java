package fit.hutech.NguyenDaiKimCuong.repositories;
import fit.hutech.NguyenDaiKimCuong.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends
JpaRepository<Category, Long> {
}
