package com.device.dto;

import jakarta.validation.constraints.NotNull;
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
    private String name;
    @NotNull(message = "brand shouldn't be null")
    private String brand;
    private LocalDateTime creationDateTime;
}
