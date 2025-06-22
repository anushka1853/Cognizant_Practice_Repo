import java.util.Arrays;
import java.util.Comparator;

public class Main {

    // Linear Search by product name
    public static Product linearSearch(Product[] products, String targetName) {
        for (Product product : products) {
            if (product.productName.equalsIgnoreCase(targetName)) {
                return product;
            }
        }
        return null;
    }

    // Binary Search by product name (requires sorted array)
    public static Product binarySearch(Product[] products, String targetName) {
        int left = 0, right = products.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            int cmp = products[mid].productName.compareToIgnoreCase(targetName);
            if (cmp == 0) return products[mid];
            else if (cmp < 0) left = mid + 1;
            else right = mid - 1;
        }

        return null;
    }

    public static void main(String[] args) {
        // Step 1: Initialize products
        Product[] products = {
            new Product(101, "Laptop", "Electronics"),
            new Product(102, "Phone", "Electronics"),
            new Product(103, "Shoes", "Footwear"),
            new Product(104, "Watch", "Accessories"),
            new Product(105, "Camera", "Electronics")
        };

        // Step 2: Perform Linear Search
        String searchKey = "Shoes";
        Product foundLinear = linearSearch(products, searchKey);
        System.out.println("Linear Search Result: " + (foundLinear != null ? foundLinear : "Not Found"));

        // Step 3: Sort for Binary Search
        Arrays.sort(products, Comparator.comparing(p -> p.productName.toLowerCase()));
        Product foundBinary = binarySearch(products, searchKey);
        System.out.println("Binary Search Result: " + (foundBinary != null ? foundBinary : "Not Found"));
    }
}
