package com.device.management.service;

import com.device.dao.DeviceRepository;
import com.device.dto.DeviceDto;
import com.device.entity.Device;
import com.device.exception.DeviceNotFoundException;
import com.device.service.impl.DeviceServiceImpl;
import com.fasterxml.jackson.databind.annotation.NoClass;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DeviceServiceTest {
    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceServiceImpl deviceService;

    @Test
    public void DeviceService_CreateDevice_ReturnsDeviceDto() {
        Device device = Device.builder()
                .name("SmartPhone")
                .brand("Appel").build();
        DeviceDto deviceDto = DeviceDto.builder()
                .name("SmartPhone")
                .brand("Appel").build();

        when(deviceRepository.save(Mockito.any(Device.class))).thenReturn(device);

        DeviceDto savedDevice = deviceService.createDevice(deviceDto);

        Assertions.assertThat(savedDevice).isNotNull();
        Assertions.assertThat(savedDevice.getBrand()).isEqualTo("Appel");
        Assertions.assertThat(savedDevice.getName()).isEqualTo("SmartPhone");
    }

    @Test
    public void DeviceService_FindById_ReturnDeviceDto()  {
        int deviceId = 1;
        Device device = Device.builder()
                .name("SmartPhone")
                .brand("Appel").build();
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.ofNullable(device));
        DeviceDto deviceReturn = deviceService.getDeviceById(deviceId);
        Assertions.assertThat(deviceReturn).isNotNull();

    }


    @Test
    public void DeviceService_UpdateDevice_ReturnDeviceDto() {
        int deviceId = 1;
        Device device = Device.builder()
                .id(1)
                .name("SmartPhone")
                .brand("Appel").build();
        DeviceDto deviceDto = DeviceDto.builder().id(1).name("TV").brand("LG").build();

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.ofNullable(device));
        when(deviceRepository.save(device)).thenReturn(device);

        DeviceDto updateReturn = deviceService.updateDevice(deviceDto, deviceId);

        Assertions.assertThat(updateReturn).isNotNull();
        Assertions.assertThat(updateReturn.getId()).isEqualTo(deviceId);
        Assertions.assertThat(updateReturn.getName()).isEqualTo("TV");
        Assertions.assertThat(updateReturn.getBrand()).isEqualTo("LG");
    }


    @Test
    public void DeviceService_DeleteDeviceById_ReturnString() {
        int deviceId = 1;
        Device device = Device.builder().id(1).name("TV").brand("LG").build();

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.ofNullable(device));
        assertAll(() -> deviceService.deleteDevice(deviceId));

    }


    @Test
    public void PokemonService_GetAllPokemon_ReturnsResponseDto() {

        Device device = Device.builder()
                .id(1)
                .name("SmartPhone")
                .brand("Appel").build();
       List<Device> deviceList =  new ArrayList<>();
       deviceList.add(device);
        when(deviceRepository.findAll()).thenReturn(deviceList);
        List<DeviceDto> devices = deviceService.getDevices();

        Assertions.assertThat(devices).isNotNull();
    }
}
