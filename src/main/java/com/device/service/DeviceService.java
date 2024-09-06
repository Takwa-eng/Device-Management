package com.device.service;

import com.device.dto.DeviceDto;
import com.device.entity.Device;
import com.device.exception.DeviceNotFoundException;

import java.util.List;

/**
 *
 */
public interface DeviceService {
    /**
     *  creation new device
     * @param deviceDto : device
     * @return device created
     */
    DeviceDto createDevice(DeviceDto deviceDto);

    DeviceDto getDeviceById(int id);

    List<DeviceDto> getDevices();

    DeviceDto updateDevice(DeviceDto deviceDto,int id);

    void deleteDevice(int id);

    List<DeviceDto> findByBrand(String brand);
}
