package purchaseorder.itemservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemRequest {

    @NotNull(message = "Name must not be null")
    @NotBlank(message = "Name must not be blank")
    private String name;
    private String description;

    @NotNull(message = "Price number must not be null")
    @PositiveOrZero
    private int price;

    @NotNull(message = "Cost number must not be null")
    @PositiveOrZero
    private int cost;
}
