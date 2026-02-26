package com.smartmobilitytripmanagement.beans;

import jakarta.persistence.*;


import java.time.LocalDateTime;
@Entity
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "transport_type")
    private String transportType; // BUS, BRT, TER

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "start_location")
    private String startLocation;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "end_location")
    private String endLocation;

    private String status; // STARTED, COMPLETED, CANCELLED

    @Column(name = "calculated_price")
    private Double calculatedPrice;

    public Trip() {}
    public Trip(Long userId, String transportType, String startLocation) {
        this.userId = userId;
        this.transportType = transportType;
        this.startLocation = startLocation;
        this.startTime = LocalDateTime.now();
        this.status = "STARTED";
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getTransportType() {
        return transportType;
    }
    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public String getStartLocation() {
        return startLocation;
    }
    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    public String getEndLocation() {
        return endLocation;
    }
    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Double getCalculatedPrice() {
        return calculatedPrice;
    }
    public void setCalculatedPrice(Double calculatedPrice) {
        this.calculatedPrice = calculatedPrice;
    }



}
