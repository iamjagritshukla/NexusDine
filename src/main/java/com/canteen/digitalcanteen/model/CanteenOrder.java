package com.canteen.digitalcanteen.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="canteen_orders", indexes={@Index(name="idx_order_status_created", columnList="status,created_at")})
public class CanteenOrder {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(unique=true, length=24) private String orderNumber;
    @Column(nullable=false, length=20) private String customerMobile;
    @Column(nullable=false, precision=10, scale=2) private BigDecimal subtotal=BigDecimal.ZERO;
    @Column(nullable=false, precision=10, scale=2) private BigDecimal totalAmount=BigDecimal.ZERO;
    @Column(nullable=false, length=20) private String paymentMethod="OFFLINE";
    @Column(nullable=false, length=20) private String paymentStatus="PENDING";
    @Enumerated(EnumType.STRING) @Column(nullable=false, length=20) private OrderStatus status=OrderStatus.PLACED;
    @Column(nullable=false, name="created_at") private LocalDateTime createdAt;
    @Column(nullable=false, name="updated_at") private LocalDateTime updatedAt;
    @Column(length=500) private String instructions;
    @OneToMany(mappedBy="order", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.LAZY)
    private List<OrderItem> items = new ArrayList<>();

    @PrePersist void beforeInsert(){createdAt=LocalDateTime.now(); updatedAt=createdAt;}
    @PreUpdate void beforeUpdate(){updatedAt=LocalDateTime.now();}
    public void addItem(OrderItem item){items.add(item); item.setOrder(this);}
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getOrderNumber(){return orderNumber;} public void setOrderNumber(String orderNumber){this.orderNumber=orderNumber;}
    public String getCustomerMobile(){return customerMobile;} public void setCustomerMobile(String customerMobile){this.customerMobile=customerMobile;}
    public BigDecimal getSubtotal(){return subtotal;} public void setSubtotal(BigDecimal subtotal){this.subtotal=subtotal;}
    public BigDecimal getTotalAmount(){return totalAmount;} public void setTotalAmount(BigDecimal totalAmount){this.totalAmount=totalAmount;}
    public String getPaymentMethod(){return paymentMethod;} public void setPaymentMethod(String paymentMethod){this.paymentMethod=paymentMethod;}
    public String getPaymentStatus(){return paymentStatus;} public void setPaymentStatus(String paymentStatus){this.paymentStatus=paymentStatus;}
    public OrderStatus getStatus(){return status;} public void setStatus(OrderStatus status){this.status=status;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime createdAt){this.createdAt=createdAt;}
    public LocalDateTime getUpdatedAt(){return updatedAt;} public void setUpdatedAt(LocalDateTime updatedAt){this.updatedAt=updatedAt;}
    public String getInstructions(){return instructions;} public void setInstructions(String instructions){this.instructions=instructions;}
    public List<OrderItem> getItems(){return items;} public void setItems(List<OrderItem> items){this.items=items;}
}
