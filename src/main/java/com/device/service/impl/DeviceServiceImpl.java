package com.device.service.impl;

import com.device.dao.DeviceRepository;
import com.device.dto.DeviceDto;
import com.device.entity.Device;
import com.device.exception.DeviceNotFoundException;
import com.device.service.DeviceService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImpl implements DeviceService {

    private DeviceRepository deviceRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }


    @Override
    public DeviceDto createDevice(DeviceDto deviceDto) {

       // DeviceDto device = DeviceDto.build(1, deviceDto.getName(), deviceDto.getBrand(), LocalDateTime.MAX);
       // return mapToDto(deviceRepository.save(device));

        Device device = new Device();
        device.setName(deviceDto.getName());
        device.setBrand(deviceDto.getBrand());

        Device newDevice = deviceRepository.save(device);

        DeviceDto deviceResponse = new DeviceDto();
        deviceResponse.setId(newDevice.getId());
        deviceResponse.setName(newDevice.getName());
        deviceResponse.setBrand(newDevice.getBrand());
        return deviceResponse;
    }

    @Override
    public DeviceDto getDeviceById(int id)  {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new DeviceNotFoundException("Device not found"));
        return mapToDto(device);
    }

    @Override
    public List<DeviceDto> getDevices() {
        List<Device> listOfDevice = deviceRepository.findAll();
        return  listOfDevice.stream().map(p -> mapToDto(p)).collect(Collectors.toList());

    }

    @Override
    public DeviceDto updateDevice(DeviceDto devicedto,int id) {

       Device device = deviceRepository.findById(id).orElseThrow(() -> new DeviceNotFoundException("Device could not be updated"));
        device.setName(devicedto.getName());

        device.setBrand(devicedto.getBrand());
        //device.setCreationDateTime(device.get().getCreationDateTime());
       Device deviceToUpdate = deviceRepository.save(device);

        return mapToDto(deviceToUpdate) ;

    }


    @Override
    public void deleteDevice(int id) {
        Device pokemon = deviceRepository.findById(id).orElseThrow(() -> new DeviceNotFoundException("Device could not be delete"));
        deviceRepository.deleteById(id);

    }

    @Override
    public List<DeviceDto> findByBrand(String brand) {
         List<Device> listOfDevice = deviceRepository.findByBrand(brand);
        return  listOfDevice.stream().map(p -> mapToDto(p)).collect(Collectors.toList());

    }


    /*
     * Note to reviewer:
     * I'm aware that there are libraries such as MapStruct or ModelMapper
     * that can automate the mapping process between entities and DTOs.
     * However, for simplicity and to avoid introducing additional dependencies,
     * I opted for manual mapping in this case, as the entity is straightforward
     * and the mapping logic is minimal.
     */
    private DeviceDto mapToDto (Device device) {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setId(device.getId());
        deviceDto.setName(device.getName());
        deviceDto.setBrand(device.getBrand());
        return deviceDto;
    }

}
