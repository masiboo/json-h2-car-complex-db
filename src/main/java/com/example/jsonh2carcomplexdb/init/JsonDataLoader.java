package com.example.jsonh2carcomplexdb.init;


import com.example.jsonh2carcomplexdb.model.A;
import com.example.jsonh2carcomplexdb.model.B;
import com.example.jsonh2carcomplexdb.model.C;
import com.example.jsonh2carcomplexdb.model.Vehicle;
import com.example.jsonh2carcomplexdb.model.Warehouse;
import com.example.jsonh2carcomplexdb.model.WarehousesData;
import com.example.jsonh2carcomplexdb.repository.ARepository;
import com.example.jsonh2carcomplexdb.repository.BRepository;
import com.example.jsonh2carcomplexdb.service.WarehouseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class JsonDataLoader implements CommandLineRunner {

    private final WarehouseService warehouseService;
    private final ARepository aRepository;
    private final BRepository bRepository;
    ObjectMapper mapper;
    private A a;
    private B b;

    public JsonDataLoader(WarehouseService warehouseService, ARepository aRepository, BRepository bRepository) {
        this.warehouseService = warehouseService;
        this.aRepository = aRepository;
        this.bRepository = bRepository;
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public List<Warehouse> getWarehouse() throws JsonProcessingException {
        Warehouse[] warehouses = mapper.readValue(WarehousesData.jsonData, Warehouse[].class);
        var warehouseList = Arrays.asList(warehouses);
        sortByDate(warehouseList);
        return warehouseList;
    }

    public List<Warehouse> sortByDate(List<Warehouse> warehouses) {
        for (Warehouse warehouse : warehouses) {
            var vehicles = sort(warehouse.getCar().getVehicles());
            warehouse.getCar().setVehicles(vehicles);
        }
        return warehouses;
    }

    public List<Vehicle> sort(List<Vehicle> vehicles) {
        vehicles.sort((vehicle1, vehicle2) -> {
            if (vehicle1.getDateAdded() == null || vehicle2.getDateAdded() == null)
                return 0;
            return vehicle1.getDateAdded().compareTo(vehicle2.getDateAdded());
        });
        return vehicles;
    }

    public String getPrettyJson(Object object) throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    public void insertABC(){

        B b = new B();
        b.setName("B-Name");
        b.setAddress("B-Address");

        List<C> cList = List.of(new C("c-name", "c-address"),new C( "c-name2", "c-address2"),
                new C( "c-name3", "c-address3"));

      //  cList.forEach(item -> item.setB(b));


        A a = new A();
        a.setName("A-Name");
        a.setAddress("A-Address");
        a.setB(b);
        aRepository.save(a);

        cList.forEach(item -> item.setB(b));
        b.setCBatch(cList);
        aRepository.save(a);


    }

    public void getAB(){
       var aa =  aRepository.getById(a.getAId());
       var bb = aa.getB();
       bb.setAddress("new address of B");
       bRepository.save(bb);
        System.out.printf("Stop");
    }

    @Override
    public void run(String... args) throws Exception {
        warehouseService.deleteAll();
       // aRepository.deleteAll();
       // bRepository.deleteAll();
        var warehouse = getWarehouse();
        warehouse.forEach(item -> warehouseService.saveWareHouse(item));
        insertABC();
       // getAB();
    }
}
