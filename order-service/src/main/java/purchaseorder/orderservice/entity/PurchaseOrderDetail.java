package purchaseorder.orderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "po_d")
public class PurchaseOrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int pohId;

    @Column(nullable = false)
    private int itemId;

    @Column(nullable = false)
    private int itemQty;

    @Column(nullable = false)
    private int itemCost;

    @Column(nullable = false)
    private int itemPrice;
}
