package com.canteen.digitalcanteen.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
public record OrderRequest(
 @NotBlank @Pattern(regexp="^[6-9][0-9]{9}$", message="Enter a valid 10-digit Indian mobile number") String customerMobile,
 @NotBlank String paymentMethod,
 @Size(max=500) String instructions,
 @NotEmpty List<@Valid OrderLineRequest> items
) {}
