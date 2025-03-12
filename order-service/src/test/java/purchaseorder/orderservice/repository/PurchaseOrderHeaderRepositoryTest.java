package purchaseorder.orderservice.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import purchaseorder.orderservice.entity.PurchaseOrderHeader;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class PurchaseOrderHeaderRepositoryTest {

    @Autowired
    private PurchaseOrderHeaderRepository purchaseOrderHeaderRepository;

    @BeforeEach
    void setUp() {
        purchaseOrderHeaderRepository.deleteAll();
    }

    @Test
    void testSavePurchaseOrderHeader() {
        PurchaseOrderHeader purchaseOrderHeader = new PurchaseOrderHeader();
        purchaseOrderHeader.setDescription("Order untuk supplier A");
        purchaseOrderHeader.setTotalPrice(500000);
        purchaseOrderHeader.setTotalCost(450000);
        purchaseOrderHeader.setCreatedBy("admin");
        purchaseOrderHeader.setUpdatedBy("admin");

        PurchaseOrderHeader savedOrder = purchaseOrderHeaderRepository.save(purchaseOrderHeader);

        assertEquals("Order untuk supplier A", savedOrder.getDescription());
    }

    @Test
    void testFindPurchaseOrderById() {
        PurchaseOrderHeader purchaseOrderHeader = new PurchaseOrderHeader();
        purchaseOrderHeader.setDescription("Order untuk supplier A");
        purchaseOrderHeader.setTotalPrice(500000);
        purchaseOrderHeader.setTotalCost(450000);
        purchaseOrderHeader.setCreatedBy("admin");
        purchaseOrderHeader.setUpdatedBy("admin");

        PurchaseOrderHeader savedOrder = purchaseOrderHeaderRepository.save(purchaseOrderHeader);
        Optional<PurchaseOrderHeader> foundOrder = purchaseOrderHeaderRepository.findById(savedOrder.getId());

        assertTrue(foundOrder.isPresent());
        assertEquals("Order untuk supplier A", foundOrder.get().getDescription());
    }

    @Test
    void testUpdatePurchaseOrderHeader() {
        PurchaseOrderHeader purchaseOrderHeader = new PurchaseOrderHeader();
        purchaseOrderHeader.setDescription("Order untuk supplier A");
        purchaseOrderHeader.setTotalPrice(500000);
        purchaseOrderHeader.setTotalCost(450000);
        purchaseOrderHeader.setCreatedBy("admin");
        purchaseOrderHeader.setUpdatedBy("admin");

        PurchaseOrderHeader savedOrder = purchaseOrderHeaderRepository.save(purchaseOrderHeader);
        savedOrder.setTotalPrice(550000);
        savedOrder.setUpdatedBy("superadmin");
        PurchaseOrderHeader updatedOrder = purchaseOrderHeaderRepository.save(savedOrder);

        assertEquals(550000, updatedOrder.getTotalPrice());
        assertEquals("superadmin", updatedOrder.getUpdatedBy());
    }

    @Test
    void testDeletePurchaseOrderHeader() {
        PurchaseOrderHeader purchaseOrderHeader = new PurchaseOrderHeader();
        purchaseOrderHeader.setDescription("Order untuk supplier A");
        purchaseOrderHeader.setTotalPrice(500000);
        purchaseOrderHeader.setTotalCost(450000);
        purchaseOrderHeader.setCreatedBy("admin");
        purchaseOrderHeader.setUpdatedBy("admin");

        PurchaseOrderHeader savedOrder = purchaseOrderHeaderRepository.save(purchaseOrderHeader);
        purchaseOrderHeaderRepository.deleteById(savedOrder.getId());

        Optional<PurchaseOrderHeader> deletedOrder = purchaseOrderHeaderRepository.findById(savedOrder.getId());

        assertFalse(deletedOrder.isPresent());
    }

    @Test
    void testFindAllPurchaseOrders() {
        PurchaseOrderHeader order1 = new PurchaseOrderHeader();
        order1.setDescription("Order untuk supplier A");
        order1.setTotalPrice(500000);
        order1.setTotalCost(450000);
        order1.setCreatedBy("admin");
        order1.setUpdatedBy("admin");

        PurchaseOrderHeader order2 = new PurchaseOrderHeader();
        order2.setDescription("Order untuk supplier B");
        order2.setTotalPrice(750000);
        order2.setTotalCost(700000);
        order2.setCreatedBy("admin");
        order2.setUpdatedBy("admin");

        purchaseOrderHeaderRepository.save(order1);
        purchaseOrderHeaderRepository.save(order2);

        List<PurchaseOrderHeader> orders = purchaseOrderHeaderRepository.findAll();

        assertFalse(orders.isEmpty());
        assertEquals(2, orders.size());
    }
}
