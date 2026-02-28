package com.smartmobilitytripmanagement.beans;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
@Setter
@Getter
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



    public Trip() {}
    public Trip(Long userId, String transportType, String startLocation) {
        this.userId = userId;
        this.transportType = transportType;
        this.startLocation = startLocation;
        this.startTime = LocalDateTime.now();
        this.status = "STARTED";
    }


}
