package purchaseorder.orderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "po_h")
public class PurchaseOrderHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp datetime;

    private String description;
    private int totalPrice;
    private int totalCost;
    private String createdBy;
    private String updatedBy;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDatetime;

    @UpdateTimestamp
    private Timestamp updatedDatetime;
}
