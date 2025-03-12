package purchaseorder.orderservice.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import purchaseorder.orderservice.entity.PurchaseOrderDetail;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class PurchaseOrderDetailRepositoryTest {

    @Autowired
    private PurchaseOrderDetailRepository purchaseOrderDetailRepository;

    @BeforeEach
    void setUp() {
        purchaseOrderDetailRepository.deleteAll();
    }

    @Test
    void testSavePurchaseOrderDetail() {
        PurchaseOrderDetail purchaseOrderDetail = new PurchaseOrderDetail();
        purchaseOrderDetail.setPohId(1);
        purchaseOrderDetail.setItemId(100);
        purchaseOrderDetail.setItemQty(5);
        purchaseOrderDetail.setItemCost(200000);
        purchaseOrderDetail.setItemPrice(250000);

        PurchaseOrderDetail savedDetail = purchaseOrderDetailRepository.save(purchaseOrderDetail);

        assertEquals(1, savedDetail.getPohId());
        assertEquals(100, savedDetail.getItemId());
    }

    @Test
    void testFindPurchaseOrderDetailById() {
        PurchaseOrderDetail purchaseOrderDetail = new PurchaseOrderDetail();
        purchaseOrderDetail.setPohId(1);
        purchaseOrderDetail.setItemId(100);
        purchaseOrderDetail.setItemQty(5);
        purchaseOrderDetail.setItemCost(200000);
        purchaseOrderDetail.setItemPrice(250000);

        PurchaseOrderDetail savedDetail = purchaseOrderDetailRepository.save(purchaseOrderDetail);
        Optional<PurchaseOrderDetail> foundDetail = purchaseOrderDetailRepository.findById(savedDetail.getId());

        assertTrue(foundDetail.isPresent());
        assertEquals(1, foundDetail.get().getPohId());
        assertEquals(100, foundDetail.get().getItemId());
    }

    @Test
    void testUpdatePurchaseOrderDetail() {
        PurchaseOrderDetail purchaseOrderDetail = new PurchaseOrderDetail();
        purchaseOrderDetail.setPohId(1);
        purchaseOrderDetail.setItemId(100);
        purchaseOrderDetail.setItemQty(5);
        purchaseOrderDetail.setItemCost(200000);
        purchaseOrderDetail.setItemPrice(250000);

        PurchaseOrderDetail savedDetail = purchaseOrderDetailRepository.save(purchaseOrderDetail);
        savedDetail.setItemQty(10);
        savedDetail.setItemPrice(300000);
        PurchaseOrderDetail updatedDetail = purchaseOrderDetailRepository.save(savedDetail);

        assertEquals(10, updatedDetail.getItemQty());
        assertEquals(300000, updatedDetail.getItemPrice());
    }

    @Test
    void testFindByPohId() {
        PurchaseOrderDetail detail1 = new PurchaseOrderDetail();
        detail1.setPohId(1);
        detail1.setItemId(100);
        detail1.setItemQty(5);
        detail1.setItemCost(200000);
        detail1.setItemPrice(250000);

        PurchaseOrderDetail detail2 = new PurchaseOrderDetail();
        detail2.setPohId(1);
        detail2.setItemId(101);
        detail2.setItemQty(3);
        detail2.setItemCost(150000);
        detail2.setItemPrice(180000);

        purchaseOrderDetailRepository.save(detail1);
        purchaseOrderDetailRepository.save(detail2);

        List<PurchaseOrderDetail> details = purchaseOrderDetailRepository.findByPohId(1);

        assertFalse(details.isEmpty());
        assertEquals(2, details.size());
    }

    @Test
    void testDeleteByPohId() {
        PurchaseOrderDetail detail1 = new PurchaseOrderDetail();
        detail1.setPohId(1);
        detail1.setItemId(100);
        detail1.setItemQty(5);
        detail1.setItemCost(200000);
        detail1.setItemPrice(250000);

        PurchaseOrderDetail detail2 = new PurchaseOrderDetail();
        detail2.setPohId(1);
        detail2.setItemId(101);
        detail2.setItemQty(3);
        detail2.setItemCost(150000);
        detail2.setItemPrice(180000);

        purchaseOrderDetailRepository.save(detail1);
        purchaseOrderDetailRepository.save(detail2);

        purchaseOrderDetailRepository.deleteByPohId(1);

        List<PurchaseOrderDetail> remainingDetails = purchaseOrderDetailRepository.findByPohId(1);

        assertTrue(remainingDetails.isEmpty());
    }
}
