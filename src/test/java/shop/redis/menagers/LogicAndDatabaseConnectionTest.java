//package shop.orm.menagers;
//
//
//import shop.orm.menagers.TestData;
//import shop.orm.menagers.ClientRegisterManager;
//import shop.orm.menagers.PurchaseManager;
//import shop.orm.menagers.StockManager;
//import shop.orm.model.*;
//
//import java.math.BigDecimal;
//import java.util.*;
//
//public class LogicAndDatabaseConnectionTest {
//    public static void main(String[] args) {
//        //Client Registering, Client selecting
//
//
//        Client client = TestData.getClient1();
//        ClientRegisterManager clientRegisterManager = new ClientRegisterManager();
//        clientRegisterManager.clientRegister(client);
//        clientRegisterManager.clientRegister(client );
//
//        List<Client> allClients = clientRegisterManager.getAllClients();
//        System.out.println(allClients.toString());
//        //Adding products
//        StockManager stockManager = new StockManager();
//        stockManager.addProductToDatabase( "snickers", BigDecimal.valueOf(15));
//        stockManager.addProductToDatabase( "snickers", BigDecimal.valueOf(15));
//        stockManager.addProductToDatabase( "snickers", BigDecimal.valueOf(15));
//        stockManager.addProductToDatabase( "mars", BigDecimal.valueOf(30));
//        //Purchasing
//        while (true) {
//            List<Product> allProductsAvailable = stockManager.getAllProductsAvailable();
//            if (allProductsAvailable.isEmpty()) {
//                System.out.println("Brak dostepnych produktow");
//                break;
//            }
//
//            Map<String, Integer> productCountMap = new HashMap<>();
//            for (Product product : allProductsAvailable) {
//                String productName = product.getProductName();
//                productCountMap.put(productName, productCountMap.getOrDefault(productName, 0) + 1);
//            }
//
//            Scanner scanner = new Scanner(System.in);
//            List<Product> productList = new ArrayList<>();
//            while (true) {
//                productCountMap = new HashMap<>();
//                System.out.println("Available Products:");
//                for (Product product : allProductsAvailable) {
//                    String productName = product.getProductName();
//                    productCountMap.put(productName, productCountMap.getOrDefault(productName, 0) + 1);
//                }
//                productCountMap.forEach((name, count) -> System.out.printf("%s -> %d sztuk%n", name, count));
//                System.out.print("Enter product name to purchase (or type 'exit' to finish): ");
//                String productName = scanner.nextLine();
//
//                if ("exit".equalsIgnoreCase(productName)) {
//
//                    return;
//                }
//                if (!productCountMap.containsKey(productName)) {
//                    System.out.println("Product not available. Try again.");
//                    continue;
//                }
//                System.out.print("Enter quantity: ");
//                int quantity = Integer.parseInt(scanner.nextLine());
//
//                if (quantity <= 0 || quantity > productCountMap.get(productName)) {
//                    System.out.println("Invalid quantity. Please try again.");
//                    continue;
//                }
//                for (int i = 0; i < quantity; i++) {
//                    if (allProductsAvailable.isEmpty()) {
//                        break;
//                    }
//                    Product selectedProduct = allProductsAvailable.stream()
//                            .filter(p -> p.getProductName().equals(productName))
//                            .findFirst()
//                            .orElse(null);
//                    allProductsAvailable.remove(selectedProduct);
//                    productList.add(selectedProduct);
//                }
//                System.out.print("Do you want to continue adding products to the cart? (yes/no): ");
//                String continueAdding = scanner.nextLine();
//                if ("no".equalsIgnoreCase(continueAdding)) {
//                    break;
//                }
//            }
//            if (!productList.isEmpty()) {
//                Purchase purchase = new Purchase(client, productList);
//                PurchaseManager purchaseManager = new PurchaseManager();
//                purchaseManager.makeAPurchase( purchase);
//
//                System.out.println("Your purchase was successful!");
//                System.out.println(purchaseManager.getAllPurchasesByClient(entityManager, client.getId()));
//                break;
//            } else {
//                System.out.println("No products were added to the cart.");
//            }
//            if (entityManagerFactory.isOpen()){
//                entityManagerFactory.close();
//            }
//            if (entityManager.getTransaction().isActive()){
//                entityManager.close();
//            }
//        }
//    }
//}
