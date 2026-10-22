package com.canteen.digitalcanteen.config;
import com.canteen.digitalcanteen.model.MenuItem;
import com.canteen.digitalcanteen.repository.MenuItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;
import java.util.List;
@Configuration
public class DataInitializer {
 @Bean CommandLineRunner seedMenu(MenuItemRepository repo){return args -> {
  if(repo.count()>0)return;
  repo.saveAll(List.of(
   new MenuItem("Crispy Samosa Chaat","Snacks",new BigDecimal("45.00"),"Crispy samosa with curd and tangy chutney","https://images.unsplash.com/photo-1601050690597-df0568f70950?auto=format&fit=crop&w=700&q=80",true),
   new MenuItem("Executive Deluxe Thali","Meals",new BigDecimal("110.00"),"A filling campus lunch thali","https://images.unsplash.com/photo-1546833999-b9f581a1996d?auto=format&fit=crop&w=700&q=80",true),
   new MenuItem("Paneer Tikka Roll","Rolls",new BigDecimal("80.00"),"Smoky paneer wrapped in a soft roll","https://images.unsplash.com/photo-1628294895950-9805252327bc?auto=format&fit=crop&w=700&q=80",true),
   new MenuItem("High-Protein Sprout Bowl","Healthy",new BigDecimal("65.00"),"Fresh sprouts, vegetables and light dressing","https://images.unsplash.com/photo-1512621776951-a57141f2eefd?auto=format&fit=crop&w=700&q=80",true),
   new MenuItem("Double Egg Chicken Bhurji Bowl","Non-Veg",new BigDecimal("95.00"),"Egg bhurji with chicken and herbs","https://images.unsplash.com/photo-1525351484163-7529414344d8?auto=format&fit=crop&w=700&q=80",true),
   new MenuItem("Cheese Grilled Sandwich","Quick Bites",new BigDecimal("55.00"),"Grilled sandwich with cheese and vegetables","https://images.unsplash.com/photo-1528735602780-2552fd46c7af?auto=format&fit=crop&w=700&q=80",true),
   new MenuItem("Cold Coffee","Drinks",new BigDecimal("50.00"),"Chilled creamy cold coffee","https://images.unsplash.com/photo-1461023058943-07fcbe16d735?auto=format&fit=crop&w=700&q=80",true),
   new MenuItem("Chole Bhature Combo","Meals",new BigDecimal("85.00"),"Chole with fluffy bhature","https://images.unsplash.com/photo-1626132647523-66f5bf380027?auto=format&fit=crop&w=700&q=80",true)
  ));
 };}
}
