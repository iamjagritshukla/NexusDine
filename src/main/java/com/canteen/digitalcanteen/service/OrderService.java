package com.canteen.digitalcanteen.service;

import com.canteen.digitalcanteen.dto.*;
import com.canteen.digitalcanteen.model.*;
import com.canteen.digitalcanteen.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderService {
 private final CanteenOrderRepository orderRepository;
 private final MenuItemRepository menuRepository;
 public OrderService(CanteenOrderRepository orderRepository, MenuItemRepository menuRepository){this.orderRepository=orderRepository;this.menuRepository=menuRepository;}

 @Transactional
 public OrderResponse placeOrder(OrderRequest request) {
  if (!"OFFLINE".equalsIgnoreCase(request.paymentMethod())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Online payment is coming soon. Select Offline Payment.");
  CanteenOrder order=new CanteenOrder(); order.setCustomerMobile(request.customerMobile()); order.setPaymentMethod("OFFLINE"); order.setPaymentStatus("PENDING"); order.setStatus(OrderStatus.PLACED); order.setInstructions(request.instructions()==null?null:request.instructions().trim());
  BigDecimal subtotal=BigDecimal.ZERO;
  for (OrderLineRequest line: request.items()) {
   MenuItem menu=menuRepository.findById(line.menuItemId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"A selected menu item no longer exists."));
   if (!menu.isAvailable() || menu.getStockQuantity() <= 0) throw new ResponseStatusException(HttpStatus.CONFLICT,menu.getName()+" is currently unavailable.");
   if (menu.getStockQuantity() < line.quantity()) throw new ResponseStatusException(HttpStatus.CONFLICT,"Only "+menu.getStockQuantity()+" unit(s) left for "+menu.getName()+".");
   BigDecimal effectivePrice=menu.getEffectivePrice();
   OrderItem item=new OrderItem(menu,line.quantity(),effectivePrice); order.addItem(item); subtotal=subtotal.add(item.getLineTotal());
   menu.setStockQuantity(menu.getStockQuantity()-line.quantity()); menuRepository.save(menu);
  }
  order.setSubtotal(subtotal); order.setTotalAmount(subtotal);
  CanteenOrder saved=orderRepository.saveAndFlush(order); saved.setOrderNumber("T-"+String.format("%04d",saved.getId()));
  return OrderResponse.from(orderRepository.saveAndFlush(saved));
 }

 @Transactional(readOnly=true)
 public OrderResponse getOrder(Long id){return orderRepository.findFullById(id).map(OrderResponse::from).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Order not found."));}

 @Transactional(readOnly=true)
 public List<OrderResponse> activeOrders(){return orderRepository.findActiveWithItems(List.of(OrderStatus.PLACED,OrderStatus.PREPARING,OrderStatus.READY)).stream().map(OrderResponse::from).toList();}
 @Transactional(readOnly=true)
 public List<OrderResponse> latestOrders(){return orderRepository.findLatestWithItems().stream().limit(100).map(OrderResponse::from).toList();}

 @Transactional
 public OrderResponse updateStatus(Long id, String requested) {
  OrderStatus next;
  try { next=OrderStatus.valueOf(requested.trim().toUpperCase(Locale.ROOT)); }
  catch(Exception ex){throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid order status.");}
  CanteenOrder order=orderRepository.findFullById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Order not found."));
  boolean allowed=switch(order.getStatus()) {
   case PLACED -> next==OrderStatus.PREPARING || next==OrderStatus.CANCELLED;
   case PREPARING -> next==OrderStatus.READY || next==OrderStatus.CANCELLED;
   case READY -> next==OrderStatus.COMPLETED;
   case COMPLETED, CANCELLED -> false;
  };
  if(!allowed) throw new ResponseStatusException(HttpStatus.CONFLICT,"Cannot change status from "+order.getStatus()+" to "+next+".");
  order.setStatus(next);
  return OrderResponse.from(orderRepository.save(order));
 }
}
