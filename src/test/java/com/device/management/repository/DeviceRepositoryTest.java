package com.device.management.repository;

import com.device.dao.DeviceRepository;
import com.device.entity.Device;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class DeviceRepositoryTest {

    @Autowired
    private DeviceRepository deviceRepository;


    @Test
    public void DeviceRepository_Save_ReturnSavedDevice() {

        Device pokemon = Device.builder().name("SmartPhone").brand("Appel").build();

        Device savedDevice = deviceRepository.save(pokemon);

        Assertions.assertThat(savedDevice).isNotNull();
        Assertions.assertThat(savedDevice.getId()).isGreaterThan(0);
        Assertions.assertThat(savedDevice.getName()).isEqualTo("SmartPhone");
        Assertions.assertThat(savedDevice.getBrand()).isEqualTo("Appel");
    }


    @Test
    public void DeviceRepository_GetAll_ReturnMoreThenOneDevice() {
        Device device = Device.builder()
                .name("SmartPhone")
                .brand("Sumsung").build();
        Device device2 = Device.builder()
                .name("SmartPhone")
                .brand("Appel").build();

        deviceRepository.save(device);
        deviceRepository.save(device2);

        List<Device> deviceList = deviceRepository.findAll();

        Assertions.assertThat(deviceList).isNotNull();
        Assertions.assertThat(deviceList.size()).isEqualTo(2);
    }

    @Test
    public void DeviceRepository_FindById_ReturnDevice() {
        Device device = Device.builder()
                .name("SmartPhone")
                .brand("Appel").build();

        deviceRepository.save(device);

        Device deviceList = deviceRepository.findById(device.getId()).get();

        Assertions.assertThat(deviceList).isNotNull();

        Assertions.assertThat(deviceList.getName()).isEqualTo("SmartPhone");
        Assertions.assertThat(deviceList.getBrand()).isEqualTo("Appel");
    }


    @Test
    public void DeviceRepository_FindByBrand_ReturnDeviceNotNull() {
        Device device = Device.builder()
                .name("SmartPhone")
                .brand("Appel").build();

        deviceRepository.save(device);

        List<Device> deviceList = deviceRepository.findByBrand("Appel");

        Assertions.assertThat(deviceList).isNotNull();
    }

    @Test
    public void DeviceRepository_FindByBrand_ReturnDeviceNull() {
        Device device = Device.builder()
                .name("SmartPhone")
                .brand("Appel").build();

        deviceRepository.save(device);

        List<Device> deviceList = deviceRepository.findByBrand("TEST");

        Assertions.assertThat(deviceList.size() == 0);
    }


    @Test
    public void DeviceRepository_UpdateDevice_ReturnDeviceNotNull() {
        Device device = Device.builder()
                .name("SmartPhone")
                .brand("Appel").build();

        deviceRepository.save(device);

        Device deviceSave = deviceRepository.findById(device.getId()).get();
        deviceSave.setBrand("Tv");
        deviceSave.setName("LG");

        Device updatedPokemon = deviceRepository.save(deviceSave);

        Assertions.assertThat(updatedPokemon.getName()).isNotNull();
        Assertions.assertThat(updatedPokemon.getBrand()).isNotNull();
        Assertions.assertThat(updatedPokemon.getBrand()).isEqualTo("Tv");
        Assertions.assertThat(updatedPokemon.getName()).isEqualTo("LG");
    }

    @Test
    public void DeviceRepository_DeviceDelete_ReturnDeviceIsEmpty() {
        Device device = Device.builder()
                .name("SmartPhone")
                .brand("Appel").build();

        deviceRepository.save(device);
        deviceRepository.deleteById(device.getId());
        Optional<Device> deviceReturn = deviceRepository.findById(device.getId());
        Assertions.assertThat(deviceReturn).isEmpty();
    }

    @Test
    public void DeviceRepository_DeviceDelete_ReturnDeviceNotFound() {
        Device device = Device.builder()
                .name("SmartPhone")
                .brand("Appel").build();

        deviceRepository.save(device);
        deviceRepository.deleteById(13);
        Optional<Device> deviceReturn = deviceRepository.findById(device.getId());
        Assertions.assertThat(deviceReturn).isNotEmpty();
    }
}
