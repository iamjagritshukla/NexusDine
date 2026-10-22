package com.canteen.digitalcanteen.dto;
import com.canteen.digitalcanteen.model.CanteenOrder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
public record OrderResponse(Long id, String orderNumber, String customerMobile, BigDecimal subtotal, BigDecimal totalAmount,
 String paymentMethod, String paymentStatus, String status, LocalDateTime createdAt, LocalDateTime updatedAt,
 String instructions, List<OrderItemResponse> items) {
 public static OrderResponse from(CanteenOrder o) {
  return new OrderResponse(o.getId(),o.getOrderNumber(),o.getCustomerMobile(),o.getSubtotal(),o.getTotalAmount(),o.getPaymentMethod(),o.getPaymentStatus(),o.getStatus().name(),o.getCreatedAt(),o.getUpdatedAt(),o.getInstructions(),o.getItems().stream().map(i -> new OrderItemResponse(i.getMenuItem()==null?null:i.getMenuItem().getId(),i.getItemName(),i.getQuantity(),i.getUnitPrice(),i.getLineTotal())).toList());
 }
}
