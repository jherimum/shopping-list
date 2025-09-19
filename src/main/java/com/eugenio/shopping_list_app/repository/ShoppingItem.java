package com.eugenio.shopping_list_app.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;

@Getter
@Entity
@Table(name = "shopping_items")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class) // Crucial for auditing listeners
public class ShoppingItem {

    @Setter
    private String name;
    @Setter
    private BigDecimal price;
    @Setter
    private int quantity;
    @Setter
    private String category;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime lastModifiedAt;



    @Builder
    public ShoppingItem(String name, BigDecimal price, int quantity, String category) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    @JsonIgnore
    public boolean isPersisted() {
        return null != this.id;
    }

    public BigDecimal getCost() {
        return this.price.multiply(BigDecimal.valueOf(this.quantity)).setScale(2);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ShoppingItem.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("price=" + price)
                .add("quantity=" + quantity)
                .add("category='" + category + "'")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingItem that = (ShoppingItem) o;
        return quantity == that.quantity && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(price, that.price) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, quantity, category);
    }
}
