package warehouse.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import warehouse.model.Warehouse;

public interface WarehouseRepository extends MongoRepository<Warehouse, String> {

    Optional<Warehouse> findByWarehouseId(Long warehouseId);

    boolean existsByWarehouseId(Long warehouseId);

    void deleteByWarehouseId(Long warehouseId);

    List<Warehouse> findByProductDataProductId(Long productId);
}
