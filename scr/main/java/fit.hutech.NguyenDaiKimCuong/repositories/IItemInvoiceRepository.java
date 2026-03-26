package fit.hutech.NguyenDaiKimCuong.repositories;

import fit.hutech.NguyenDaiKimCuong.entities.Invoice;
import fit.hutech.NguyenDaiKimCuong.entities.ItemInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IItemInvoiceRepository extends
JpaRepository<ItemInvoice, Long>{
	void deleteByInvoice(Invoice invoice);
}
