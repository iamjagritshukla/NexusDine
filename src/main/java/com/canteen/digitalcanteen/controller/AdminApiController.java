package com.canteen.digitalcanteen.controller;

import com.canteen.digitalcanteen.model.CanteenOrder;
import com.canteen.digitalcanteen.model.MenuItem;
import com.canteen.digitalcanteen.model.OrderStatus;
import com.canteen.digitalcanteen.repository.CanteenOrderRepository;
import com.canteen.digitalcanteen.repository.MenuItemRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/admin")
public class AdminApiController {
 private final MenuItemRepository menu; private final CanteenOrderRepository orders;
 public AdminApiController(MenuItemRepository menu, CanteenOrderRepository orders){this.menu=menu;this.orders=orders;}

 @GetMapping("/summary") public Map<String,Object> summary(){
  List<CanteenOrder> all=orders.findAll(); BigDecimal revenue=all.stream().filter(o->o.getStatus()!=OrderStatus.CANCELLED).map(CanteenOrder::getTotalAmount).reduce(BigDecimal.ZERO,BigDecimal::add);
  long pending=all.stream().filter(o->o.getStatus()==OrderStatus.PLACED).count(); long preparing=all.stream().filter(o->o.getStatus()==OrderStatus.PREPARING).count(); long ready=all.stream().filter(o->o.getStatus()==OrderStatus.READY).count();
  return Map.of("totalOrders",all.size(),"revenue",revenue,"pendingOrders",pending,"preparingOrders",preparing,"readyOrders",ready,"menuItems",menu.count(),"lowStock",menu.findAll().stream().filter(MenuItem::isLowStock).count());
 }
 @GetMapping("/orders") public List<Map<String,Object>> allOrders(){return orders.findLatestWithItems().stream().limit(200).map(o->{Map<String,Object> m=new LinkedHashMap<>();m.put("id",o.getId());m.put("orderNumber",o.getOrderNumber());m.put("customerMobile",o.getCustomerMobile());m.put("status",o.getStatus().name());m.put("totalAmount",o.getTotalAmount());m.put("paymentStatus",o.getPaymentStatus());m.put("createdAt",o.getCreatedAt());m.put("items",o.getItems().stream().map(i->i.getItemName()+" × "+i.getQuantity()).toList());return m;}).toList();}
 @GetMapping("/menu") public List<MenuItem> allMenu(){return menu.findAll().stream().sorted(Comparator.comparing(MenuItem::getCategory).thenComparing(MenuItem::getName)).toList();}
 @PostMapping("/menu") @ResponseStatus(HttpStatus.CREATED) public MenuItem create(@Valid @RequestBody MenuRequest r){MenuItem m=new MenuItem(); apply(m,r); return menu.save(m);}
 @PutMapping("/menu/{id}") public MenuItem update(@PathVariable Long id,@Valid @RequestBody MenuRequest r){MenuItem m=find(id);apply(m,r);return menu.save(m);}
 @DeleteMapping("/menu/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){MenuItem item=find(id);item.setAvailable(false);menu.save(item);}
 @PatchMapping("/menu/{id}/stock") public MenuItem stock(@PathVariable Long id,@Valid @RequestBody StockRequest r){MenuItem m=find(id);m.setStockQuantity(r.stockQuantity());m.setLowStockThreshold(r.lowStockThreshold());return menu.save(m);}
 @PatchMapping("/menu/{id}/offer") public MenuItem offer(@PathVariable Long id,@Valid @RequestBody OfferRequest r){MenuItem m=find(id);m.setDiscountPercent(r.discountPercent());return menu.save(m);}
 @PatchMapping("/menu/{id}/availability") public MenuItem availability(@PathVariable Long id,@RequestBody AvailabilityRequest r){MenuItem m=find(id);m.setAvailable(r.available());return menu.save(m);}
 private MenuItem find(Long id){return menu.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Menu item not found"));}
 private void apply(MenuItem m,MenuRequest r){m.setName(r.name().trim());m.setCategory(r.category().trim());m.setPrice(r.price());m.setDescription(r.description());m.setImageUrl(r.imageUrl());m.setAvailable(r.available());m.setStockQuantity(r.stockQuantity());m.setLowStockThreshold(r.lowStockThreshold());m.setDiscountPercent(r.discountPercent());}
 public record MenuRequest(@NotBlank @Size(max=100) String name,@NotBlank @Size(max=60) String category,@NotNull @DecimalMin("0.01") BigDecimal price,@Size(max=500) String description,@Size(max=500) String imageUrl,boolean available,@Min(0) int stockQuantity,@Min(0) int lowStockThreshold,@NotNull @DecimalMin("0") @DecimalMax("100") BigDecimal discountPercent){}
 public record StockRequest(@Min(0) int stockQuantity,@Min(0) int lowStockThreshold){}
 public record OfferRequest(@NotNull @DecimalMin("0") @DecimalMax("100") BigDecimal discountPercent){}
 public record AvailabilityRequest(boolean available){}
}
