package purchaseorder.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import purchaseorder.orderservice.entity.PurchaseOrderDetail;

import java.util.List;

@Repository
public interface PurchaseOrderDetailRepository extends JpaRepository<PurchaseOrderDetail, Integer> {

    List<PurchaseOrderDetail> findByPohId(Integer pohId);

    void deleteByPohId(int pohId);
}
