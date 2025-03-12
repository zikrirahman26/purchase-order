package purchaseorder.orderservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class POHRequest {

    private String description;

    @NotNull(message = "Order detail must not be null")
    private List<PODRequest> ordersDetails;
}
