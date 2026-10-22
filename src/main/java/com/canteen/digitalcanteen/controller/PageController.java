package com.canteen.digitalcanteen.controller;
import com.canteen.digitalcanteen.repository.MenuItemRepository;
import com.canteen.digitalcanteen.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
@Controller
public class PageController {
 private final MenuItemRepository menuRepository; private final OrderService orderService;
 @Value("${app.base-url:http://localhost:8080}") private String baseUrl;
 public PageController(MenuItemRepository menuRepository, OrderService orderService){this.menuRepository=menuRepository;this.orderService=orderService;}
 @GetMapping({"/","/menu"}) public String menu(Model model, Authentication authentication){if(authentication!=null && authentication.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ROLE_COOK"))) return "redirect:/cook"; model.addAttribute("menuItems",menuRepository.findByAvailableTrueAndStockQuantityGreaterThanOrderByCategoryAscNameAsc(0));return "index";}
 @GetMapping("/cook") public String cook(){return "cook";}
 @GetMapping("/admin") public String admin(){return "admin";}
 @GetMapping("/login") public String login(){return "login";}
 @GetMapping("/qr") public String qr(Model model){model.addAttribute("qrDestination",baseUrl.replaceAll("/+$", "")+"/");return "qr";}
 @GetMapping("/orders/{id}/receipt") public String receipt(@PathVariable Long id, Model model){model.addAttribute("order",orderService.getOrder(id));return "receipt";}
 @GetMapping("/orders/{id}/track") public String track(@PathVariable Long id, Model model){model.addAttribute("orderId",id);return "tracking";}
}
