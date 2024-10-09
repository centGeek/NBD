package shop.orm.menagers;

import jakarta.persistence.EntityManager;
import shop.orm.model.Product;

import java.math.BigDecimal;

public class PurchaseManager {
    public void deleteProduct(Product product){

    };
    public void updateProduct(EntityManager entityManager, Product productToUpdate, BigDecimal new_price){
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
    }


}
