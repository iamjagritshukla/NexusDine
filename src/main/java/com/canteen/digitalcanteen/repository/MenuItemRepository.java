package com.canteen.digitalcanteen.repository;
import com.canteen.digitalcanteen.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByAvailableTrueOrderByCategoryAscNameAsc();
    List<MenuItem> findByAvailableTrueAndStockQuantityGreaterThanOrderByCategoryAscNameAsc(int quantity);
}
