package com.device.management.controller;

import com.device.controller.DeviceController;
import com.device.dto.DeviceDto;
import com.device.service.DeviceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeviceController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceService deviceService;

    @Autowired
    private ObjectMapper objectMapper;

    private DeviceDto deviceDto;

    @BeforeEach
    public void init() {
        deviceDto = DeviceDto.builder()
                .id(1)
                .name("phone")
                .brand("Apple")
                .creationDateTime(LocalDateTime.now())
                .build();
    }

    @Test
    void createDevice_ReturnsCreated() throws Exception {
        given(deviceService.createDevice(ArgumentMatchers.any(DeviceDto.class))).willReturn(deviceDto);

        ResultActions response = mockMvc.perform(post("/api/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deviceDto)));

        response.andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(deviceDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand", CoreMatchers.is(deviceDto.getBrand())));
    }

    @Test
    void deleteDevice_ReturnsOk() throws Exception {
        int deviceId = 1;
        doNothing().when(deviceService).deleteDevice(deviceId);

        ResultActions response = mockMvc.perform(delete("/api/devices/{id}", deviceId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Device deleted successfully"));
    }

    @Test
    void getAllDevices_ReturnsDevices() throws Exception {
        List<DeviceDto> devices = Arrays.asList(deviceDto);
        given(deviceService.getDevices()).willReturn(devices);

        ResultActions response = mockMvc.perform(get("/api/devices")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.is(deviceDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].brand", CoreMatchers.is(deviceDto.getBrand())));
    }

    @Test
    void getDevicesByBrand_ReturnsDevices() throws Exception {
        DeviceDto deviceDto1 = DeviceDto.builder().name("Laptop").brand("Apple").build();
        List<DeviceDto> devices = Arrays.asList(deviceDto, deviceDto1);
        given(deviceService.findByBrand("Apple")).willReturn(devices);

        ResultActions response = mockMvc.perform(get("/api/devices")
                .param("brand", "Apple")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.is(deviceDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].brand", CoreMatchers.is(deviceDto.getBrand())));
    }

    @Test
    void getDeviceById_ReturnsDevice() throws Exception {
        given(deviceService.getDeviceById(1)).willReturn(deviceDto);

        ResultActions response = mockMvc.perform(get("/api/devices/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(deviceDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand", CoreMatchers.is(deviceDto.getBrand())));
    }

    @Test
    void updateDevice_ReturnsUpdatedDevice() throws Exception {
        int deviceId = 1;
        given(deviceService.updateDevice(deviceDto, deviceId)).willReturn(deviceDto);

        ResultActions response = mockMvc.perform(put("/api/devices/{id}", deviceId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deviceDto)));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(deviceDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand", CoreMatchers.is(deviceDto.getBrand())));
    }
}
