package com.canteen.digitalcanteen.dto;
import java.math.BigDecimal;
public record OrderItemResponse(Long menuItemId, String itemName, int quantity, BigDecimal unitPrice, BigDecimal lineTotal) {}
