package com.device.controller;

import com.device.dto.DeviceDto;
import com.device.exception.DeviceNotFoundException;
import com.device.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Device Controller
 */

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    /**
     * Add new Device.
     * @param deviceDto device
     * @return created Device
     */
    @PostMapping
    public ResponseEntity<DeviceDto> addDevice(@RequestBody @Valid DeviceDto deviceDto) {
        return new ResponseEntity<>(deviceService.createDevice(deviceDto), HttpStatus.CREATED);
    }

    /**
     * Get device by ID.
     * @param id device ID
     * @return details of device
     * @throws DeviceNotFoundException if device is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<DeviceDto> getDeviceById(@PathVariable int id) throws DeviceNotFoundException {
        return ResponseEntity.ok(deviceService.getDeviceById(id));
    }

    /**
     * Get all devices, with optional filtering by brand.
     * @param brand optional brand filter
     * @return list of devices
     */
    @GetMapping
    public ResponseEntity<List<DeviceDto>> getAllDevices(@RequestParam(value = "brand", required = false) String brand) {
        if (brand != null) {
            return ResponseEntity.ok(deviceService.findByBrand(brand));
        } else {
            return ResponseEntity.ok(deviceService.getDevices());
        }
    }

    /**
     * Delete device by ID.
     * @param id device ID
     * @return confirmation message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDeviceById(@PathVariable int id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.ok("Device deleted successfully");
    }

    /**
     * Update device by ID.
     * @param deviceDto updated device data
     * @param id device ID
     * @return updated device
     * @throws DeviceNotFoundException if device is not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<DeviceDto> updateDevice(@RequestBody DeviceDto deviceDto, @PathVariable int id) throws DeviceNotFoundException {
        DeviceDto updatedDevice = deviceService.updateDevice(deviceDto, id);
        return ResponseEntity.ok(updatedDevice);
    }
}

