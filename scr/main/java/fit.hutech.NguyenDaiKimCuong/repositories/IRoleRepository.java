package fit.hutech.NguyenDaiKimCuong.repositories;

import fit.hutech.NguyenDaiKimCuong.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
    Role findRoleById(Long id);
}
