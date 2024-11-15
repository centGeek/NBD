package shop.orm.menagers;


import org.junit.jupiter.api.*;
import shop.orm.model.Product;
import shop.orm.repository.AbstractMongoRepository;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StockManagerTest {

    @BeforeAll
    public static void setUp() {
        AbstractMongoRepository.getDatabase();
    }

    @Test
    @Order(1)
    public void addingAndDeletingProductsCorrectly() {
        try (StockManager stockManager = new StockManager("testStock")) {

            int prevSize = stockManager.getAllProductsAvailable().size();
            Product product = TestData.getProduct2();
            for (int i = 0; i < 10; i++) {
                stockManager.addProductToDatabase(product.getProductName(), product.getPrice());
            }

            List<Product> allProductsByName = stockManager.getAllProductsByName(product.getProductName());
            Assertions.assertEquals(prevSize + 10, allProductsByName.size());
            allProductsByName = stockManager.getAllProductsByName(product.getProductName());
            Assertions.assertEquals(prevSize + 10, allProductsByName.size());

            stockManager.deleteProduct(allProductsByName.getFirst());

            Assertions.assertEquals(prevSize + 9, stockManager.getAllProductsByName(product.getProductName()).size());


        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    @Order(2)
    public void changingProductPriceCorrectly() {
        Product product = TestData.getProduct1();

        try (StockManager stockManager = new StockManager("testStock")) {
            for (int i = 0; i < 2; i++) {
                stockManager.addProductToDatabase(product.getProductName(), product.getPrice());
            }
            stockManager.addProductToDatabase(product.getProductName() + "d", product.getPrice());

            stockManager.changeProductPrice(product.getProductName(), BigDecimal.valueOf(5));
            List<Product> allProductsAvailable = stockManager.getAllProductsByName(product.getProductName());
            for (Product prod : allProductsAvailable) {
                Assertions.assertEquals(BigDecimal.valueOf(5), prod.getPrice());
            }
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }




    @Test
    @Order(10)
    public void primaryNodeDown() {
        try {
            //You need to have tail,cut cmdlets on your computer!
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "mongosh --host mongodb1:27017 --username admin --password adminpassword --authenticationDatabase admin --eval \"rs.status().members.filter(member => member.stateStr === 'PRIMARY')[0].name\" | tail -1 | cut -d : -f 1");

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();
            String toKill = output.toString();

            ProcessBuilder processBuilder2 = new ProcessBuilder("cmd.exe", "/c", "docker stop " + toKill);
            Process process2 = processBuilder2.start();
            process2.waitFor();

            Product product = TestData.getProduct1();

            try (StockManager stockManager = new StockManager("testStock")) {
                for (int i = 0; i < 2; i++) {
                    stockManager.addProductToDatabase(product.getProductName(), product.getPrice());
                }
                stockManager.addProductToDatabase(product.getProductName() + "d", product.getPrice());

                stockManager.changeProductPrice(product.getProductName(), BigDecimal.valueOf(5));
                List<Product> allProductsAvailable = stockManager.getAllProductsByName(product.getProductName());
                for (Product prod : allProductsAvailable) {
                    Assertions.assertEquals(BigDecimal.valueOf(5), prod.getPrice());
                }
            } catch (Exception e) {
                Assertions.fail(e);
            }

            ProcessBuilder processBuilder3 = new ProcessBuilder("cmd.exe", "/c", "docker start " + toKill);
            Process process3 = processBuilder3.start();
            process3.waitFor();
            Thread.sleep(3000);


            ProcessBuilder processBuilder4 = new ProcessBuilder("cmd.exe", "/c", "mongosh --host mongodb1:27017 --username admin --password adminpassword --authenticationDatabase admin --eval \"use('shop'); db.testStock.validate()\" | grep valid | cut -d : -f 2 | tail -1 | cut -d ',' -f 1");
            Process process4 = processBuilder4.start();

            BufferedReader reader2 = new BufferedReader(new InputStreamReader(process4.getInputStream()));
            StringBuilder output2 = new StringBuilder();
            String line2;

            while ((line2 = reader2.readLine()) != null) {
                output2.append(line2).append("\n");
            }

            process4.waitFor();

            String validateResult = output2.toString();
            if (!validateResult.equals(" true\n")) {
                throw new RuntimeException("node validation failure, maybe you don't have tail or cut cmdlets on you system");
            }


        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @AfterAll
    public static void closeAll() {
        AbstractMongoRepository.decrementCounter();
    }
}
