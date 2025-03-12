package purchaseorder.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class POHResponse {

    private int id;
    private Timestamp datetime;
    private String description;
    private int totalPrice;
    private int totalCost;
    private List<PODResponse> ordersDetails;
}
