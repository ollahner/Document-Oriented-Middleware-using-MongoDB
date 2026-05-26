package warehouse.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "warehouses")
public class Warehouse {

	@Id
	private String id;

	@Indexed(unique = true)
	private Long warehouseId;

	private String warehouseName;

	private String warehousePostalCode;

	private String warehouseCity;

	private String warehouseCountry;

	private Instant timestamp;

	private List<Product> productData = new ArrayList<>();

	public Warehouse() {
	}

	public Warehouse(Long warehouseId, String warehouseName, String warehousePostalCode,
					 String warehouseCity, String warehouseCountry, Instant timestamp,
					 List<Product> productData) {
		this.warehouseId = warehouseId;
		this.warehouseName = warehouseName;
		this.warehousePostalCode = warehousePostalCode;
		this.warehouseCity = warehouseCity;
		this.warehouseCountry = warehouseCountry;
		this.timestamp = timestamp;
		if (productData != null) {
			this.productData = productData;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getWarehousePostalCode() {
		return warehousePostalCode;
	}

	public void setWarehousePostalCode(String warehousePostalCode) {
		this.warehousePostalCode = warehousePostalCode;
	}

	public String getWarehouseCity() {
		return warehouseCity;
	}

	public void setWarehouseCity(String warehouseCity) {
		this.warehouseCity = warehouseCity;
	}

	public String getWarehouseCountry() {
		return warehouseCountry;
	}

	public void setWarehouseCountry(String warehouseCountry) {
		this.warehouseCountry = warehouseCountry;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public List<Product> getProductData() {
		return productData;
	}

	public void setProductData(List<Product> productData) {
		this.productData = productData;
	}
}
