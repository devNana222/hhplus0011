package com.tdd.ecommerce.customer.infrastructure;

import com.tdd.ecommerce.common.domain.TimeStamped;
import com.tdd.ecommerce.common.exception.BusinessException;
import com.tdd.ecommerce.common.exception.ECommerceException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="customer")
public class Customer extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="customer_id")
    private Long customerId;

    @Column(name="balance")
    private Long balance;

    public Long chargeBalance(Long amount) {
        if(amount < 0)
            throw new BusinessException(ECommerceException.INVALID_AMOUNT);
        return this.balance += amount;
    }

    public void useBalance(Long amount) {
        this.balance -= amount;
    }
}
