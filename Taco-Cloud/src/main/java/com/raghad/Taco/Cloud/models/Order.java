package com.raghad.Taco.Cloud.models;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.NoArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "tacos_order")
@NoArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "street is required")
    private String street;

    @NotBlank(message = "city is required")
    private String city;

    @NotBlank(message = "state is required")
    private String state;

    @NotBlank(message = "zip is required")
    private String zip;

    @Column(name = "cc_number")
    //@CreditCardNumber
    private String ccNumber;

    @Column(name = "cc_expiration")
    //@Pattern(regexp = "^(0[1-9] | 1[0-2]) ([\\/]) ([1-9][0-9])", message = "cExpiration must be in the format MM/YY")
    private String ccExpiration;

    @Column(name = "placed_at")
    private LocalDateTime placedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "order_tacos", joinColumns = @JoinColumn(name = "order_id"),
                                     inverseJoinColumns = @JoinColumn(name = "taco_id"))
    @Size(min = 1, message = "you must choose at least one taco")
    private List<Taco> tacos = new ArrayList<>();

    @PrePersist
    public void saveCreationDate() {
        this.placedAt = LocalDateTime.now();
    }
}
