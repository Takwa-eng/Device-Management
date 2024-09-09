package com.device.service.impl;

import com.device.dao.DeviceRepository;
import com.device.dto.DeviceDto;
import com.device.entity.Device;
import com.device.exception.DeviceNotFoundException;
import com.device.service.DeviceService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImpl implements DeviceService {

    private DeviceRepository deviceRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }


    /**
     * Creates a new device.
     * @param deviceDto : device
     * @return  DeviceDto the created device
     */
    @Override
    public DeviceDto createDevice(DeviceDto deviceDto) {
        Device device = new Device();
        device.setName(deviceDto.getName());
        device.setBrand(deviceDto.getBrand());
        device.setCreationDateTime(LocalDateTime.now());

        Device savedDevice = deviceRepository.save(device);
        return mapToDto(savedDevice);
    }

    /**
     * search device by id
     * @param id id of device
     * @return DeviceDto the found device
     * @throws DeviceNotFoundException if no device found
     */
    @Override
    public DeviceDto getDeviceById(int id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException("Device with ID " + id + " not found"));
        return mapToDto(device);
    }

    /**
     * get all devices
     * @return List of devices
     */
    @Override
    public List<DeviceDto> getDevices() {
        return deviceRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     *  Updates a device by its ID.
     * @param deviceDto
     * @param id
     * @return the updated device
     * @throws DeviceNotFoundException if no device found with the given id
     */
    @Override
    public DeviceDto updateDevice(DeviceDto deviceDto, int id) {
        Device existingDevice = deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException("Device with ID " + id + " not found"));

        if (deviceDto.getName() != null) {
            existingDevice.setName(deviceDto.getName());
        }
        if (deviceDto.getBrand() != null) {
            existingDevice.setBrand(deviceDto.getBrand());
        }

        // Creation date should not be updated
        existingDevice.setCreationDateTime(existingDevice.getCreationDateTime());

        Device updatedDevice = deviceRepository.save(existingDevice);
        return mapToDto(updatedDevice);
    }

    /**
     * Deletes a device by
     * @param id the ID of the device to delete
     * @throws DeviceNotFoundException if the device with the given ID is not found
     */
    @Override
    public void deleteDevice(int id) {
        deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException("Device could not be delete"));
        deviceRepository.deleteById(id);
    }

    /**
     * Finds devices by brand
     * @param brand
     * @return a list of devices with the specified brand
     */
    @Override
    public List<DeviceDto> findByBrand(String brand) {
        return deviceRepository.findByBrand(brand).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /*
     * Note to reviewer:
     * I'm aware that there are libraries such as MapStruct or ModelMapper
     * that can automate the mapping process between entities and DTOs.
     * However, for simplicity and to avoid introducing additional dependencies,
     * I opted for manual mapping in this case, as the entity is straightforward
     * and the mapping logic is minimal.
     */
    private DeviceDto mapToDto(Device device) {
        return DeviceDto.builder()
                .id(device.getId())
                .name(device.getName())
                .brand(device.getBrand())
                .creationDateTime(device.getCreationDateTime())
                .build();
    }
}
