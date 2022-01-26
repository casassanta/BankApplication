package com.gcc.bankapplication.service

import com.gcc.bankapplication.model.Customer
import com.gcc.bankapplication.repository.CustomerRepository
import org.springframework.stereotype.Service


@Service
class CustomerService(
    val customerRepository: CustomerRepository
) {

    fun findAll(): List<Customer>{
        return customerRepository.findAll()
    }

    fun createCustomer(customer: Customer){
        customerRepository.save(customer)
    }
}