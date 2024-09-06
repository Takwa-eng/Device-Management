package com.device.management.controller;

import com.device.controller.DeviceController;
import com.device.dto.DeviceDto;
import com.device.entity.Device;
import com.device.service.DeviceService;
import com.device.service.impl.DeviceServiceImpl;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
    private Device device;
    private DeviceDto deviceDto;


    @BeforeEach
    public void init() {
        device = Device.builder().name("phone").brand("Appel").build();

        deviceDto = DeviceDto.builder().name("phone").brand("Appel").build();


    }


    @Test
    void DeviceController_CreateDevice_ReturnCreated() throws Exception {


        given(deviceService.createDevice(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/device/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deviceDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(deviceDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand", CoreMatchers.is(deviceDto.getBrand())));
    }

    @Test
    public void DeleteDevice_ReturnString() throws Exception {
        int deviceId = 1;
        doNothing().when(deviceService).deleteDevice(deviceId);
        ResultActions response = mockMvc.perform(delete("/api/device/delete/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void GetAllDevices_Returndevices() throws Exception {
        List<DeviceDto> devices = Arrays.asList(deviceDto);
        when(deviceService.getDevices()).thenReturn(devices);

        ResultActions response = mockMvc.perform(get("/api/device/fetchAll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(devices)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.is(deviceDto.getName())));


    }

    @Test
    public void GetAllDevicesByBrand_Returndevices() throws Exception {

        DeviceDto deviceDto1 = DeviceDto.builder().name("Laptop").brand("Appel").build();
        List<DeviceDto> devices = Arrays.asList(deviceDto ,deviceDto1 );
        when(deviceService.findByBrand("Appel")).thenReturn(devices);

        ResultActions response = mockMvc.perform(get("/api/device/getByBrand")
                .contentType(MediaType.APPLICATION_JSON)
                .param("brand","Appel"));


        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.is(deviceDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].brand", CoreMatchers.is(deviceDto.getBrand())));

    }


    @Test
    public void DeviceDetail_ReturnDevice() throws Exception {

        when(deviceService.getDeviceById(1)).thenReturn(deviceDto);

        ResultActions response = mockMvc.perform(get("/api/device/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deviceDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(device.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand", CoreMatchers.is(device.getBrand())));
    }


    @Test
    public void DeviceUpdate_ReturnDeviceUpdated() throws Exception {
        int deviceId = 1;
        when(deviceService.updateDevice(deviceDto,deviceId)).thenReturn(deviceDto);

        ResultActions response = mockMvc.perform(put("/api/device/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deviceDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(deviceDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand", CoreMatchers.is(deviceDto.getBrand())));
    }
}
