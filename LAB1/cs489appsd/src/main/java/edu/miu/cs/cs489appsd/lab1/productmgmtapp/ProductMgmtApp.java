package edu.miu.cs.cs489appsd.lab1.productmgmtapp;

import edu.miu.cs.cs489appsd.lab1.productmgmtapp.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

@SpringBootApplication
public class ProductMgmtApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProductMgmtApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Initializing data
        Product[] products = new Product[]{
                new Product(new BigInteger("31288741190182539912"), "Banana", LocalDate.of(2026, 1, 24), 124, 0.55),
                new Product(new BigInteger("29274582650152771644"), "Apple", LocalDate.of(2025, 12, 9), 18, 1.09),
                new Product(new BigInteger("91899274600128155167"), "Carrot", LocalDate.of(2026, 3, 31), 89, 2.99),
                new Product(new BigInteger("31288741190182539913"), "Banana", LocalDate.of(2026, 2, 13), 240, 0.65)
        };

        printProducts(products);
    }

    public void printProducts(Product[] products) {
        // Sort: ascending order of name, then descending order of unit price
        Arrays.sort(products, Comparator
                .comparing(Product::getName)
                .thenComparing(Comparator.comparing(Product::getUnitPrice).reversed()));

        System.out.println("Printing in JSON format:");
        printJson(products);

        System.out.println("\nPrinting in XML format:");
        printXml(products);

        System.out.println("\nPrinting in CSV format:");
        printCsv(products);
    }

    private void printJson(Product[] products) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            String json = mapper.writeValueAsString(products);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void printXml(Product[] products) {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            String xml = xmlMapper.writeValueAsString(products);
            System.out.println(xml);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void printCsv(Product[] products) {
        System.out.println("productId,name,dateSupplied,quantityInStock,unitPrice");
        for (Product p : products) {
            System.out.printf("%s,%s,%s,%d,%.2f%n",
                    p.getProductId(),
                    p.getName(),
                    p.getDateSupplied(),
                    p.getQuantityInStock(),
                    p.getUnitPrice());
        }
    }
}
