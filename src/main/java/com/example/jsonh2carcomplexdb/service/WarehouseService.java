package com.example.jsonh2carcomplexdb.service;



import com.example.jsonh2carcomplexdb.model.Car;
import com.example.jsonh2carcomplexdb.model.Location;
import com.example.jsonh2carcomplexdb.model.Vehicle;
import com.example.jsonh2carcomplexdb.model.Warehouse;
import com.example.jsonh2carcomplexdb.repository.WarehouseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public Page<Warehouse> buildWarehousePageable(List<Warehouse> warehouses, Pageable pageable) {
        int total = warehouses.size();
        int start = toIntExact(pageable.getOffset());
        int end = Math.min((start + pageable.getPageSize()), total);
        List<Warehouse> returnWarehouseList = new ArrayList<>();
        if (start <= end) {
            returnWarehouseList = warehouses.subList(start, end);
        }
        return new PageImpl<>(returnWarehouseList, pageable, total);
    }

    public Page<Car> buildCarPageable(List<Car> cars, Pageable pageable) {
        int total = cars.size();
        int start = toIntExact(pageable.getOffset());
        int end = Math.min((start + pageable.getPageSize()), total);
        List<Car> returnCarList = new ArrayList<>();
        if (start <= end) {
            returnCarList = cars.subList(start, end);
        }
        return new PageImpl<>(returnCarList, pageable, total);
    }


    public List<Warehouse> getAllWareHouses() {
        return warehouseRepository.findAll();
    }
    
    public Car getCarById(int carId){
        var filteredWarehouse = warehouseRepository.findAll().stream()
                .filter(warehouse -> warehouse.getCar().getId() == carId )
                .findAny()
                .orElse(null);
        var car = filteredWarehouse.getCar();
        return car;
    }

    public Warehouse saveWareHouse(Warehouse warehouse) {
        Car car = warehouse.getCar();
        car.setWarehouse(warehouse);
        Location location = warehouse.getLocation();
        location.setWarehouse(warehouse);
        List<Vehicle> vehicles  = car.getVehicles();
        vehicles.forEach( vehicle -> {
            vehicle.setCar(car);
        });
        return warehouseRepository.save(warehouse);
    }

    public void deleteAll(){
        warehouseRepository.deleteAll();
    }
}
