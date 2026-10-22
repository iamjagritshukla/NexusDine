package com.canteen.digitalcanteen.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
public record OrderLineRequest(@NotNull Long menuItemId, @Min(1) @jakarta.validation.constraints.Max(30) int quantity) {}
