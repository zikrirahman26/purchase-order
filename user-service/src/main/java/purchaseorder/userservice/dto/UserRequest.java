package purchaseorder.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    @NotNull(message = "Firstname must not be null")
    @NotBlank(message = "Firstname must not be blank")
    private String firstName;
    private String lastName;

    @Email(message = "Email must be a valid format")
    @NotNull(message = "Email must not be null")
    private String email;

    @Pattern(
            regexp = "^\\d{10,15}$",
            message = "Phone number must be between 10 to 15 digits and contain only numbers")
    @NotNull(message = "Phone number must not be null")
    private String phone;

}
