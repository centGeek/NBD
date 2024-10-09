package shop.orm.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@Setter
@Getter
@Builder
@NoArgsConstructor
@EqualsAndHashCode
@Access(AccessType.FIELD)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String product_name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

}
