package com.delivery.apidelivery.controller;

import com.delivery.apidelivery.entities.*;
import com.delivery.apidelivery.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService= orderService;
    }

    //VER MENÚ

    public ResponseEntity<List<Food>> getMenu(){
        return ResponseEntity.ok(this.orderService.showMenu());
    }

    //AGREGAR COMIDA

    public ResponseEntity<Food> agregarComida(int num){
        Food food= this.orderService.showMenu().get(num);
        this.orderService.addFood(food);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    //HACER PEDIDO

    public ResponseEntity<Order> realizarOrden(String id, String customerName, String customerEmail){
        this.orderService.makeOrder(id, customerName, customerEmail);
        this.orderService.limpiarLista();
        return new ResponseEntity<>(orderService.getOrder(), HttpStatus.CREATED);
    }

    //VER ÓRDENES

    public ResponseEntity<List<Order>> getOrders(){
        return ResponseEntity.ok(this.orderService.showOrders());
    }

    //VER UNA ORDEN

    public ResponseEntity<Order> getOrder(String id){
        Optional<Order> optionalOrder= this.orderService.searchOrder(id);
        if(optionalOrder.isPresent())
            return ResponseEntity.ok(optionalOrder.get());
        return ResponseEntity.notFound().build();
    }

    //CALCULAR TOTAL

    public ResponseEntity<String> calcularTotal(String id){
        Optional<Order> optionalOrder= this.orderService.searchOrder(id);
        if(optionalOrder.isPresent())
            return ResponseEntity.ok(optionalOrder.get().calculateTotal());
        return ResponseEntity.notFound().build();
    }

    //RECIBIR ACTUALIZACIÓN DEL PEDIDO

    public ResponseEntity<String> actualizacion(String id){
        Optional<Order> optionalOrder= this.orderService.searchOrder(id);
        if(optionalOrder.isPresent())
            return ResponseEntity.ok(optionalOrder.get().update());
        return ResponseEntity.notFound().build();
    }
}