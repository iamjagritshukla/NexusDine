package com.canteen.digitalcanteen.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="order_items")
public class OrderItem {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="order_id", nullable=false) private CanteenOrder order;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="menu_item_id") private MenuItem menuItem;
    @Column(nullable=false, length=100) private String itemName;
    @Column(nullable=false) private int quantity;
    @Column(nullable=false, precision=10, scale=2) private BigDecimal unitPrice;
    @Column(nullable=false, precision=10, scale=2) private BigDecimal lineTotal;
    public OrderItem() {}
    public OrderItem(MenuItem menuItem, int quantity, BigDecimal unitPrice){this.menuItem=menuItem;this.itemName=menuItem.getName();this.quantity=quantity;this.unitPrice=unitPrice;this.lineTotal=unitPrice.multiply(BigDecimal.valueOf(quantity));}
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public CanteenOrder getOrder(){return order;} public void setOrder(CanteenOrder order){this.order=order;}
    public MenuItem getMenuItem(){return menuItem;} public void setMenuItem(MenuItem menuItem){this.menuItem=menuItem;}
    public String getItemName(){return itemName;} public void setItemName(String itemName){this.itemName=itemName;}
    public int getQuantity(){return quantity;} public void setQuantity(int quantity){this.quantity=quantity;}
    public BigDecimal getUnitPrice(){return unitPrice;} public void setUnitPrice(BigDecimal unitPrice){this.unitPrice=unitPrice;}
    public BigDecimal getLineTotal(){return lineTotal;} public void setLineTotal(BigDecimal lineTotal){this.lineTotal=lineTotal;}
}
