package com.canteen.digitalcanteen.controller;
import com.canteen.digitalcanteen.model.MenuItem;
import com.canteen.digitalcanteen.repository.MenuItemRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/menu")
public class MenuApiController {
 private final MenuItemRepository repository;
 public MenuApiController(MenuItemRepository repository){this.repository=repository;}
 @GetMapping public List<MenuItem> list(){return repository.findByAvailableTrueAndStockQuantityGreaterThanOrderByCategoryAscNameAsc(0);}
}
