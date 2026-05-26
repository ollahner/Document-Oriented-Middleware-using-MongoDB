package warehouse.dto;

import java.util.List;

public class ProductSummary {
    private Long productId;
    private String productName;
    private String productCategory;
    private List<WarehouseStock> warehouses;

    public ProductSummary(Long productId, String productName, String productCategory, List<WarehouseStock> warehouses) {
        this.productId = productId;
        this.productName = productName;
        this.productCategory = productCategory;
        this.warehouses = warehouses;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public List<WarehouseStock> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<WarehouseStock> warehouses) {
        this.warehouses = warehouses;
    }

    public static class WarehouseStock {
        private Long warehouseId;
        private String warehouseName;
        private double productQuantity;

        public WarehouseStock(Long warehouseId, String warehouseName, double productQuantity) {
            this.warehouseId = warehouseId;
            this.warehouseName = warehouseName;
            this.productQuantity = productQuantity;
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

        public double getProductQuantity() {
            return productQuantity;
        }

        public void setProductQuantity(double productQuantity) {
            this.productQuantity = productQuantity;
        }
    }
}
