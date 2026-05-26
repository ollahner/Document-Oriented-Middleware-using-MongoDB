package warehouse.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import warehouse.model.Warehouse;
import warehouse.service.WarehouseService;

@RestController
@RequestMapping("/warehouse")
@Validated
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    public ResponseEntity<Warehouse> create(@RequestBody Warehouse warehouse) {
        Warehouse created = warehouseService.createWarehouse(warehouse);
        return ResponseEntity.created(URI.create("/warehouse/" + created.getWarehouseId())).body(created);
    }

    @GetMapping
    public List<Warehouse> getAll() {
        return warehouseService.findAllWarehouses();
    }

    @GetMapping("/{id}")
    public Warehouse getOne(@PathVariable("id") Long warehouseId) {
        return warehouseService.findWarehouse(warehouseId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long warehouseId) {
        warehouseService.deleteWarehouse(warehouseId);
        return ResponseEntity.noContent().build();
    }
}
