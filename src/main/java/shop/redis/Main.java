package shop.redis;

import shop.redis.model.*;
import shop.redis.decorators.ClientRegisterDecorator;

//package shop.orm;
//
//
//import shop.orm.menagers.ClientRegisterManager;
//import shop.orm.menagers.PurchaseManager;
//import shop.orm.menagers.StockManager;
//import shop.orm.model.*;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.*;
//
//
//public class Main {
//
//    private static Client registerClientMain() {
//        Client newClient;
//        int typeChoice;
//        String city, country, street, postalCode, street_number;
//        Address address;
//        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
//        System.out.print("\nChoose new client type:\n1: Individual\n2: Company\n");
//
//        typeChoice = Integer.parseInt(scanner.nextLine());
//        switch (typeChoice) {
//            case 1:
//                String pesel, email;
//                LocalDate localDate;
//                System.out.print("Type a new client pesel: ");
//                pesel = scanner.nextLine();
//                System.out.print("email:");
//                email = scanner.nextLine();
//                System.out.print("Birth date yyyy-mm-dd: ");
//                localDate = LocalDate.parse(scanner.nextLine());
//                System.out.print("City: ");
//                city = scanner.nextLine();
//                System.out.print("Country: ");
//                country = scanner.nextLine();
//                System.out.print("Street: ");
//                street = scanner.nextLine();
//                System.out.print("PostalCode: ");
//                postalCode = scanner.nextLine();
//                System.out.print("Street number: ");
//                street_number = scanner.nextLine();
//
//                address = new Address(city, country, postalCode, street, street_number);
//                ClientType newIndividualClientType = new IndividualClient(pesel, email, localDate);
//                newClient = new Client(address, newIndividualClientType);
//                break;
//            case 2:
//                System.out.print("City: ");
//                city = scanner.nextLine();
//                System.out.print("Country: ");
//                country = scanner.nextLine();
//                System.out.print("Street: ");
//                street = scanner.nextLine();
//                System.out.print("PostalCode: ");
//                postalCode = scanner.nextLine();
//                System.out.print("Street number: ");
//                street_number = scanner.nextLine();
//                address = new Address(city, country, postalCode, street, street_number);
//                CompanyClient companyClient = new CompanyClient();
//                newClient = new Client(address, companyClient);
//                break;
//            default:
//                throw new RuntimeException("Client not created!");
//        }
//        return newClient;
//    }
//
//    public static void main(String[] args) {
//        //Client Registering, Client selecting
//        try (ClientRegisterManager clientRegisterManager = new ClientRegisterManager()) {
//
//
//            Client client = registerClientMain();
//            clientRegisterManager.clientRegister(client);
//
//
//            List<Client> allClients = clientRegisterManager.getAllClients();
//            System.out.println(allClients.toString());
//            //Adding products
//            StockManager stockManager = new StockManager();
//            stockManager.addProductToDatabase("snickers", BigDecimal.valueOf(15));
//            stockManager.addProductToDatabase("snickers", BigDecimal.valueOf(15));
//            stockManager.addProductToDatabase("snickers", BigDecimal.valueOf(15));
//            stockManager.addProductToDatabase("mars", BigDecimal.valueOf(30));
//            //Purchasing
//
//            while (true) {
//                List<Product> allProductsAvailable = stockManager.getAllProductsAvailable();
//                if (allProductsAvailable.isEmpty()) {
//                    System.out.println("Brak dostepnych produktow");
//                    break;
//                }
//
//                Map<String, Integer> productCountMap = new HashMap<>();
//                for (Product product : allProductsAvailable) {
//                    String productName = product.getProductName();
//                    productCountMap.put(productName, productCountMap.getOrDefault(productName, 0) + 1);
//                }
//
//                Scanner scanner = new Scanner(System.in);
//                List<Product> productList = new ArrayList<>();
//                while (true) {
//                    productCountMap = new HashMap<>();
//                    System.out.println("Available Products:");
//                    for (Product product : allProductsAvailable) {
//                        String productName = product.getProductName();
//                        productCountMap.put(productName, productCountMap.getOrDefault(productName, 0) + 1);
//                    }
//                    productCountMap.forEach((name, count) -> System.out.printf("%s -> %d sztuk%n", name, count));
//                    System.out.print("Enter product name to purchase (or type 'exit' to finish): ");
//                    String productName = scanner.nextLine();
//
//                    if ("exit".equalsIgnoreCase(productName)) {
//
//                        return;
//                    }
//                    if (!productCountMap.containsKey(productName)) {
//                        System.out.println("Product not available. Try again.");
//                        continue;
//                    }
//                    System.out.print("Enter quantity: ");
//                    int quantity = Integer.parseInt(scanner.nextLine());
//
//                    if (quantity <= 0 || quantity > productCountMap.get(productName)) {
//                        System.out.println("Invalid quantity. Please try again.");
//                        continue;
//                    }
//                    for (int i = 0; i < quantity; i++) {
//                        if (allProductsAvailable.isEmpty()) {
//                            break;
//                        }
//                        Product selectedProduct = allProductsAvailable.stream()
//                                .filter(p -> p.getProductName().equals(productName))
//                                .findFirst()
//                                .orElse(null);
//                        allProductsAvailable.remove(selectedProduct);
//                        productList.add(selectedProduct);
//                    }
//                    System.out.print("Do you want to continue adding products to the cart? (yes/no): ");
//                    String continueAdding = scanner.nextLine();
//                    if ("no".equalsIgnoreCase(continueAdding)) {
//                        break;
//                    }
//                }
//                if (!productList.isEmpty()) {
//                    Purchase purchase = new Purchase(client, productList);
//                    PurchaseManager purchaseManager = new PurchaseManager();
//                    purchaseManager.makeAPurchase(purchase);
//
//                    System.out.println("Your purchase was successful!");
//                    System.out.println(purchaseManager.getAllPurchasesByClient(client.getId()));
//                    break;
//                } else {
//                    System.out.println("No products were added to the cart.");
//                }
//                if (entityManagerFactory.isOpen()) {
//                    entityManagerFactory.close();
//                }
//                if (entityManager.getTransaction().isActive()) {
//                    entityManager.close();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
public class Main {
    public static void main(String[] args) {

        var clientRegisterManager = new ClientRegisterDecorator();
        Address address = new Address("Lodz", "Poland", "06-323", "Lodzka", "32");
        ClientType clientType = new CompanyClient("3231", 323124124324234L, "Politechnika Lodzka");
        var client1 = new Client(address, clientType);
        clientRegisterManager.clientRegister(client1);

        System.out.println(clientRegisterManager.getClientByPesel(clientType.getPesel()));

    }
}
