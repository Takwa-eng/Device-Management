package com.device.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Builder
public class DeviceDto {

    private int id;
    @NotNull(message = "name shouldn't be null")
    @Size(min = 2, message = "Device name should have at least 2 characters")
    private String name;
    @NotNull(message = "brand shouldn't be null")
    @Size(min = 2, message = "Device name should have at least 2 characters")
    private String brand;
    private LocalDateTime creationDateTime;
}
