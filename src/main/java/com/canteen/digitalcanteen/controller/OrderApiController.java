package com.canteen.digitalcanteen.controller;
import com.canteen.digitalcanteen.dto.*;
import com.canteen.digitalcanteen.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/api/orders")
public class OrderApiController {
 private final OrderService service;
 public OrderApiController(OrderService service){this.service=service;}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public OrderResponse place(@Valid @RequestBody OrderRequest request){return service.placeOrder(request);}
 @GetMapping("/{id}") public OrderResponse get(@PathVariable Long id){return service.getOrder(id);}
 @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
 @ResponseStatus(HttpStatus.BAD_REQUEST) public Map<String,String> validation(org.springframework.web.bind.MethodArgumentNotValidException ex){return Map.of("error",ex.getBindingResult().getFieldErrors().stream().findFirst().map(e->e.getDefaultMessage()).orElse("Please check your input."));}
}
