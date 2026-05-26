package warehouse.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import warehouse.dto.ProductSummary;
import warehouse.dto.ProductSummary.WarehouseStock;
import warehouse.exception.ResourceNotFoundException;
import warehouse.model.Product;
import warehouse.model.Warehouse;
import warehouse.repository.WarehouseRepository;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public Warehouse createWarehouse(Warehouse warehouse) {
        if (warehouse.getWarehouseId() == null || warehouse.getWarehouseId() <= 0) {
            throw new IllegalArgumentException("warehouseId must be positive");
        }
        if (!StringUtils.hasText(warehouse.getWarehouseName())) {
            throw new IllegalArgumentException("warehouseName must not be empty");
        }
        if (!StringUtils.hasText(warehouse.getWarehousePostalCode())) {
            throw new IllegalArgumentException("warehousePostalCode must not be empty");
        }
        if (!StringUtils.hasText(warehouse.getWarehouseCity())) {
            throw new IllegalArgumentException("warehouseCity must not be empty");
        }
        if (!StringUtils.hasText(warehouse.getWarehouseCountry())) {
            throw new IllegalArgumentException("warehouseCountry must not be empty");
        }
        if (warehouseRepository.existsByWarehouseId(warehouse.getWarehouseId())) {
            throw new IllegalArgumentException("Warehouse with id %s already exists".formatted(warehouse.getWarehouseId()));
        }
        ensureDefaults(warehouse);
        return warehouseRepository.save(warehouse);
    }

    public List<Warehouse> findAllWarehouses() {
        return warehouseRepository.findAll();
    }

    public Warehouse findWarehouse(Long warehouseId) {
        return warehouseRepository.findByWarehouseId(warehouseId)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse %s not found".formatted(warehouseId)));
    }

    public void deleteWarehouse(Long warehouseId) {
        if (!warehouseRepository.existsByWarehouseId(warehouseId)) {
            throw new ResourceNotFoundException("Warehouse %s not found".formatted(warehouseId));
        }
        warehouseRepository.deleteByWarehouseId(warehouseId);
    }

    public Warehouse addProductToWarehouse(Long warehouseId, Product incoming) {
        validateAddProduct(warehouseId, incoming);
        Warehouse warehouse = findWarehouse(warehouseId);

        List<Product> products = Optional.ofNullable(warehouse.getProductData()).orElseGet(ArrayList::new);
        int existingIndex = -1;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(incoming.getProductId())) {
                existingIndex = i;
                break;
            }
        }

        if (existingIndex >= 0) {
            products.set(existingIndex, incoming);
        } else {
            products.add(incoming);
        }

        warehouse.setProductData(products);
        if (warehouse.getTimestamp() == null) {
            warehouse.setTimestamp(Instant.now());
        }
        return warehouseRepository.save(warehouse);
    }

    public void removeProductFromWarehouse(Long productId, Long warehouseId) {
        Warehouse warehouse = findWarehouse(warehouseId);
        List<Product> products = Optional.ofNullable(warehouse.getProductData()).orElseGet(ArrayList::new);
        boolean removed = products.removeIf(p -> productId.equals(p.getProductId()));
        if (!removed) {
            throw new ResourceNotFoundException("Product %s not found in warehouse %s".formatted(productId, warehouseId));
        }
        warehouse.setProductData(products);
        warehouseRepository.save(warehouse);
    }

    public List<ProductSummary> findAllProducts() {
        Map<Long, ProductSummary> aggregated = new LinkedHashMap<>();
        for (Warehouse warehouse : warehouseRepository.findAll()) {
            for (Product product : Optional.ofNullable(warehouse.getProductData()).orElseGet(ArrayList::new)) {
                ProductSummary summary = aggregated.computeIfAbsent(product.getProductId(), id -> new ProductSummary(
                        product.getProductId(), product.getProductName(), product.getProductCategory(), new ArrayList<>()));
                summary.getWarehouses().add(new WarehouseStock(warehouse.getWarehouseId(), warehouse.getWarehouseName(), product.getProductQuantity()));
            }
        }
        return new ArrayList<>(aggregated.values());
    }

    public ProductSummary findProduct(Long productId) {
        return findAllProducts().stream()
                .filter(p -> productId.equals(p.getProductId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product %s not found in any warehouse".formatted(productId)));
    }

    private void validateAddProduct(Long warehouseId, Product incoming) {
        if (warehouseId == null || warehouseId <= 0) {
            throw new IllegalArgumentException("warehouseId must be positive");
        }
        if (incoming == null) {
            throw new IllegalArgumentException("product must not be null");
        }
        if (incoming.getProductId() == null || incoming.getProductId() <= 0) {
            throw new IllegalArgumentException("productId must be positive");
        }
        if (!StringUtils.hasText(incoming.getProductName())) {
            throw new IllegalArgumentException("productName must not be empty");
        }
        if (!StringUtils.hasText(incoming.getProductCategory())) {
            throw new IllegalArgumentException("productCategory must not be empty");
        }
        if (incoming.getProductQuantity() < 0) {
            throw new IllegalArgumentException("productQuantity must be zero or positive");
        }
    }

    private void ensureDefaults(Warehouse warehouse) {
        if (warehouse.getTimestamp() == null) {
            warehouse.setTimestamp(Instant.now());
        }
        if (warehouse.getProductData() == null) {
            warehouse.setProductData(new ArrayList<>());
        }
    }
}
