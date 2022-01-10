package com.example.jsonh2carcomplexdb.web;


import com.example.jsonh2carcomplexdb.model.A;
import com.example.jsonh2carcomplexdb.model.B;
import com.example.jsonh2carcomplexdb.model.Car;
import com.example.jsonh2carcomplexdb.model.Warehouse;
import com.example.jsonh2carcomplexdb.repository.ARepository;
import com.example.jsonh2carcomplexdb.repository.BRepository;
import com.example.jsonh2carcomplexdb.service.WarehouseService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = WarehouseController.REQUEST_URL)
public class WarehouseController {

    public static final String REQUEST_URL = "/api/v1/revisions/";
    public static final String GET_ALL_WARE_HOUSES = "warehouse";
    public static final String GET_CAR_BY_ID = "/car/";
    private final String PAGE_LIMIT = "100";

    private final WarehouseService warehouseService;

    @Autowired
    public BRepository bRepository;

    @Autowired
    public ARepository aRepository;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping(value = GET_ALL_WARE_HOUSES, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "page number"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
    })
    public Page<Warehouse> getAllWarehouse(
            Pageable pageable,
            @ApiParam(value = "sortBy - eg. created, id, fileName")
            @RequestParam(value = "sort_by", required = false, defaultValue = "created") String sortBy,

            @ApiParam(value = "sortDirection (desc/asc)")
            @RequestParam(value = "sort_direction", required = false, defaultValue = "asc") String sortDirection,

            @RequestParam(value = "limit", required = false, defaultValue = PAGE_LIMIT) int limit) {

        var direction = Sort.Direction.fromString(sortDirection);
        var sort = Sort.by(direction, sortBy);
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        var warehouses = warehouseService.getAllWareHouses();

        return warehouseService.buildWarehousePageable(warehouses, pageable);

    }
/*
    @GetMapping(value = GET_CAR_BY_ID+"{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "page number"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
    })
    public Page<Warehouse> getCarById(@PathVariable int id,
            Pageable pageable,
            @ApiParam(value = "sortBy - eg. created, id, fileName")
            @RequestParam(value = "sort_by", required = false, defaultValue = "created") String sortBy,

            @ApiParam(value = "sortDirection (desc/asc)")
            @RequestParam(value = "sort_direction", required = false, defaultValue = "asc") String sortDirection,

            @RequestParam(value = "limit", required = false, defaultValue = PAGE_LIMIT) int limit) {

        var direction = Sort.Direction.fromString(sortDirection);
        var sort = Sort.by(direction, sortBy);
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        var warehouses = warehouseService.getCarById();

        return warehouseService.buildCarPageable(warehouses, pageable);

    }

    */

    @GetMapping(value = GET_CAR_BY_ID+"{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Car getCarById(@PathVariable int id){
        Car car = warehouseService.getCarById(id);
        return car;
    }

    @GetMapping(value = "/getb/{id}")
    public B getB(@PathVariable long id){
       var b =  bRepository.findById(id).get();
       return b;
    }

    @GetMapping(value = "/geta/{id}")
    public List<A> getA(@PathVariable long id){
        var a =  aRepository.findById(id).get();
        return List.of( a);
    }


    @PostMapping(value = "/save")
    public Warehouse saveWarehouse(@RequestBody Warehouse warehouse){
        return warehouseService.saveWareHouse(warehouse);
    }

}
