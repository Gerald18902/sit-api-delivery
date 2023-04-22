package com.delivery.apidelivery.entities;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private final String id;
    private String customerName;
    private String customerEmail;
    private String status;
    private final LocalDateTime creationTime;
    private LocalDateTime estimatedDeliveryTime;
    private List<Food> items;

    public Order(String id, String customerName, String customerEmail) {
        this.id = id;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.status = "EN PROCESO";
        this.creationTime= LocalDateTime.now();
        this.items = new ArrayList<>();
    }

    //CALCULAR TOTAL
    public String calculateTotal(){
        double total= 0;

        for(Food oF: this.items){
            total+= oF.getPrice();
        }

        return "Total: S/. "+total;
    }

    //VER ESTADO DEL PEDIDO
    public String showStatus(){
        LocalTime creacion= this.creationTime.toLocalTime();
        LocalTime entrega= this.estimatedDeliveryTime.toLocalTime();
        DateTimeFormatter formato= DateTimeFormatter.ofPattern("hh:mm a");

        return "Hora del pedido: "+creacion.format(formato)+", Hora de entrega: "+entrega.format(formato)+", Estado: "+this.status;
    }

    //RECIBIR ACTUALIZACIÓN DEL PEDIDO
    public String update(){
        LocalDateTime ahora= LocalDateTime.now();
        long falta= Duration.between(ahora, this.estimatedDeliveryTime).toMinutes();
        long tiempo= Duration.between(this.creationTime, this.estimatedDeliveryTime).toMinutes();

        if(falta<=(tiempo/2))
            this.setStatus("EN CAMINO");

        if(falta==0)
            this.setStatus("ENTREGADO");

        return showStatus();
    }

    public String getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public LocalDateTime getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(LocalDateTime estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public List<Food> getItems() {
        return items;
    }

    public void setItems(List<Food> items) {
        this.items = items;
    }
}