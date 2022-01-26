package com.gcc.bankapplication.service

import com.gcc.bankapplication.model.CustomerModel
import com.gcc.bankapplication.repository.CustomerRepository
import org.springframework.stereotype.Service


@Service
class CustomerService(
    val customerRepository: CustomerRepository
) {

    fun findAll(): List<CustomerModel>{
        return customerRepository.findAll()
    }
}