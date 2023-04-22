package com.delivery.apidelivery.services;

import com.delivery.apidelivery.entities.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class OrderService {
    private List<Food> menu;

    private List<Order> orders;

    private List<Food> items;

    private Order order;

    public OrderService(){
        menu= new ArrayList<>();
        menu.add(new Food("Mazamorra","Postre 1", 5));
        menu.add(new Food("Arroz con leche","Postre 2", 5));
        menu.add(new Food("Torta de chocolate","Postre 3", 7));
        orders= new ArrayList<>();
        items= new ArrayList<>();
    }

    public List<Food> showMenu(){
        return this.menu;
    }

    public void addFood(Food food){
        this.items.add(food);
    }

    public void makeOrder(String id, String customerName, String customerEmail){
        this.order= new Order(id, customerName, customerEmail);
        this.order.setItems(items);

        LocalDateTime creacion= this.order.getCreationTime();

        if(this.order.getItems().size()<=2)
            this.order.setEstimatedDeliveryTime(creacion.plusMinutes(20));
        else{
            if(this.order.getItems().size()<=4)
                this.order.setEstimatedDeliveryTime(creacion.plusMinutes(40));
            else{
                if(this.order.getItems().size()<=6)
                    this.order.setEstimatedDeliveryTime(creacion.plusMinutes(60));
            }
        }

        this.orders.add(this.order);
    }

    public Optional<Order> searchOrder(String id){
        return orders.stream().filter(o -> o.getId().equals(id)).findFirst();
    }

    public List<Order> showOrders(){
        return this.orders;
    }

    public void limpiarLista(){
        this.items= new ArrayList<>();
    }

    public Order getOrder() {
        return this.order;
    }
}