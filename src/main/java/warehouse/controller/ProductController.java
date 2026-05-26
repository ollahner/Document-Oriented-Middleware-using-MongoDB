package warehouse.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import warehouse.dto.ProductSummary;
import warehouse.model.Product;
import warehouse.service.WarehouseService;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final WarehouseService warehouseService;

    public ProductController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestParam("warehouseId") Long warehouseId,
                                        @RequestBody Product product) {
        warehouseService.addProductToWarehouse(warehouseId, product);
        return ResponseEntity.created(URI.create("/product/" + product.getProductId())).build();
    }

    @GetMapping
    public List<ProductSummary> listAll() {
        return warehouseService.findAllProducts();
    }

    @GetMapping("/{id}")
    public ProductSummary getOne(@PathVariable("id") Long productId) {
        return warehouseService.findProduct(productId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFromWarehouse(@PathVariable("id") Long productId,
                                                    @RequestParam("warehouseId") Long warehouseId) {
        warehouseService.removeProductFromWarehouse(productId, warehouseId);
        return ResponseEntity.noContent().build();
    }
}
