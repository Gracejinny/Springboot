package com.github.supercoding.respository.storeSales;

import com.github.supercoding.respository.Items.ItemEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Builder
@Entity
@Table(name = "store_sales")
public class StoreSales {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "store_name", length = 30)
    private String storeName;

    @Column(name = "amount", columnDefinition = "DEFAULT 0 CHECK(amount) >= 0", nullable = false)
    private Integer amount;

    @OneToMany(mappedBy = "storeSales", fetch = FetchType.EAGER)
    private List<ItemEntity> itemEntities;
}
