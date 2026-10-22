package com.canteen.digitalcanteen.repository;
import com.canteen.digitalcanteen.model.CanteenOrder;
import com.canteen.digitalcanteen.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
public interface CanteenOrderRepository extends JpaRepository<CanteenOrder, Long> {
    @Query("select distinct o from CanteenOrder o left join fetch o.items where o.id = :id") Optional<CanteenOrder> findFullById(@Param("id") Long id);
    @Query("select distinct o from CanteenOrder o left join fetch o.items where o.status in :statuses order by o.createdAt asc") List<CanteenOrder> findActiveWithItems(@Param("statuses") List<OrderStatus> statuses);
    @Query("select distinct o from CanteenOrder o left join fetch o.items order by o.createdAt desc") List<CanteenOrder> findLatestWithItems();
}
