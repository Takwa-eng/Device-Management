package com.device.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.repository.Temporal;

import java.time.LocalDateTime;
import java.util.Date;


@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Builder
@Entity
public class Device {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String brand;

   @CreationTimestamp
   private LocalDateTime creationDateTime;




}
