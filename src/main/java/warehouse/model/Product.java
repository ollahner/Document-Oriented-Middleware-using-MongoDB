package warehouse.model;

public class Product {

    private Long productId;

    private String productName;

    private String productCategory;

    private double productQuantity;

    public Product() {
    }

    public Product(Long productId, String productName, String productCategory, double productQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productQuantity = productQuantity;
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

    public double getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(double productQuantity) {
        this.productQuantity = productQuantity;
    }
}
