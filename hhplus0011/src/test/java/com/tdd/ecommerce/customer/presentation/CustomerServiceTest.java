package com.tdd.ecommerce.customer.presentation;

import com.tdd.ecommerce.customer.application.CustomerService;
import com.tdd.ecommerce.customer.application.CustomerServiceResponse;
import com.tdd.ecommerce.customer.domain.CustomerRepository;
import com.tdd.ecommerce.customer.infrastructure.Customer;
import com.tdd.ecommerce.common.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    @DisplayName("🟢유효한 고객의 잔액 조회")
    void getBalance_SUCCESS() {
        //given
        Customer customer = new Customer(1L, 10000L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        CustomerServiceResponse response = customerService.getCustomerBalance(1L);


        // then
        assertNotNull(response);
        assertEquals(1L, response.getCustomerId());
        assertEquals(customer.getBalance(), response.getBalance());
    }

    @Test
    @DisplayName("🔴 유효하지 않은 고객에 대한 예외")
    void getCustomerBalance_InvalidCustomer() {
        // given
        Long customerId = 2L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(BusinessException.class, () -> customerService.getCustomerBalance(customerId));
    }

    @Test
    @DisplayName("🟢유효한 고객의 잔액 충전")
    void chargeBalance_SUCCESS() {
        Customer customer = new Customer( 1L, 10000L);
        Long chargeAmount = 500L;
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        CustomerServiceResponse response = customerService.chargeCustomerBalance(customer.getCustomerId(), chargeAmount);

        assertNotNull(response);
        assertEquals(1L, response.getCustomerId());
        assertEquals(customer.getBalance(), response.getBalance());
    }
    @Test
    @DisplayName("🔴 유효하지 않은 고객에 대한 예외")
    void chargeBalance_INVALIDCUSTOMER() {
        Customer customer = new Customer(1L, 10000L);
        Long chargeAmount = 500L;

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> customerService.chargeCustomerBalance(customer.getCustomerId(), chargeAmount));
    }

}