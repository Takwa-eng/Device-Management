package com.device.controller;

import com.device.dto.DeviceDto;
import com.device.entity.Device;
import com.device.exception.DeviceNotFoundException;
import com.device.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/device")
public class DeviceController {

    private DeviceService deviceService;
    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    /***
     * Add new Device.
     * @param deviceDto device
     * @return  created Device
     */
    @PostMapping("/add")
    public ResponseEntity<DeviceDto> addDevice(@RequestBody @Valid DeviceDto deviceDto) {
       return  new ResponseEntity<>(deviceService.createDevice(deviceDto),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDto> getDeviceById(@PathVariable int id) throws DeviceNotFoundException {
        return ResponseEntity.ok(deviceService.getDeviceById(id));
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<DeviceDto>> getAllDevices() {
        return ResponseEntity.ok(deviceService.getDevices());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteDeviceById(@PathVariable int id) {
        deviceService.deleteDevice(id);
        return new ResponseEntity<>("Device delete successfully", HttpStatus.OK);
    }
    @PutMapping("update/{id}")
    public ResponseEntity<DeviceDto> updateDevice(@RequestBody DeviceDto device,  @PathVariable(value = "id") int id) throws DeviceNotFoundException {
        DeviceDto  response= deviceService.updateDevice(device,id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getByBrand")
    public ResponseEntity<List<DeviceDto>> getDevicesByBrand(@RequestParam (value = "brand", required = true) String brand) {
        return ResponseEntity.ok(deviceService.findByBrand(brand));
    }
}
