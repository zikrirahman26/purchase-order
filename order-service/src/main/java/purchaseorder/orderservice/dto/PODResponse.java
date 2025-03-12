package purchaseorder.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PODResponse {

    private int id;
    private int pohId;
    private int itemId;
    private int itemQty;
    private int itemCost;
    private int itemPrice;
}
