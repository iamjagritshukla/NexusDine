package com.canteen.digitalcanteen.controller;
import com.canteen.digitalcanteen.dto.*;
import com.canteen.digitalcanteen.service.OrderService;
import com.canteen.digitalcanteen.repository.MenuItemRepository;
import com.canteen.digitalcanteen.model.MenuItem;
import java.util.Map;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/cook")
public class CookApiController {
 private final OrderService service; private final MenuItemRepository menuRepository;
 public CookApiController(OrderService service, MenuItemRepository menuRepository){this.service=service;this.menuRepository=menuRepository;}
 @GetMapping("/inventory") public List<Map<String,Object>> inventory(){return menuRepository.findAll().stream().sorted(java.util.Comparator.comparing(MenuItem::getName)).map(m -> Map.<String,Object>of("id",m.getId(),"name",m.getName(),"stockQuantity",m.getStockQuantity(),"lowStockThreshold",m.getLowStockThreshold(),"available",m.isAvailable(),"lowStock",m.isLowStock())).toList();}
 @GetMapping("/orders") public List<OrderResponse> active(){return service.activeOrders();}
 @GetMapping("/orders/recent") public List<OrderResponse> recent(){return service.latestOrders();}
 @PatchMapping("/orders/{id}/status") public OrderResponse update(@PathVariable Long id,@Valid @RequestBody StatusUpdateRequest request){return service.updateStatus(id,request.status());}
}
