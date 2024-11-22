package com.example.service;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void saveProductsFromCsv(MultipartFile file) throws Exception {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .parse(reader);

            for (CSVRecord record : records) {
                Long productId = Long.parseLong(record.get("S.No")); // Use Product Id (S.No) as the unique identifier

                // Check if the product already exists
                Product existingProduct = productRepository.findById(productId).orElse(null);

                if (existingProduct != null) {
                    // Update the existing product
                    existingProduct.setProductName(record.get("Product Name"));
                    existingProduct.setProductImage(record.get("Product Image"));
                    existingProduct.setProductQuantity(Integer.parseInt(record.get("Product Quantity")));
                    existingProduct.setProductCost(Double.parseDouble(record.get("Product Cost")));

                    productRepository.save(existingProduct);
                } else {
                    // Insert a new product
                    Product newProduct = new Product();
                    newProduct.setId(productId); // Set the Product Id
                    newProduct.setProductName(record.get("Product Name"));
                    newProduct.setProductImage(record.get("Product Image"));
                    newProduct.setProductQuantity(Integer.parseInt(record.get("Product Quantity")));
                    newProduct.setProductCost(Double.parseDouble(record.get("Product Cost")));

                    productRepository.save(newProduct);
                }
            }
        } catch (Exception e) {
            throw new Exception("Error processing CSV file: " + e.getMessage());
        }
    }
}