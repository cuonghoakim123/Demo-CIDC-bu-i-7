package fit.hutech.NguyenDaiKimCuong.repositories;

import fit.hutech.NguyenDaiKimCuong.entities.Invoice;
import fit.hutech.NguyenDaiKimCuong.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IInvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByUserOrderByInvoiceDateDesc(User user);
    List<Invoice> findByUserAndStatusNotOrderByInvoiceDateDesc(User user, String status);
    List<Invoice> findAllByOrderByInvoiceDateDesc();
    List<Invoice> findAllByStatusNotOrderByInvoiceDateDesc(String status);
    Optional<Invoice> findFirstByUserAndStatusOrderByInvoiceDateDesc(User user, String status);
    Optional<Invoice> findByOrderNumber(String orderNumber);
    long countByStatusNot(String status);
}
