package com.gcc.bankapplication.service

import com.gcc.bankapplication.controller.request.PostAddressRequest
import com.gcc.bankapplication.model.Customer
import com.gcc.bankapplication.repository.AddressRepository
import com.gcc.bankapplication.repository.CustomerRepository
import org.springframework.stereotype.Service


@Service
class CustomerService(
    val customerRepository: CustomerRepository,
    val addressRepository: AddressRepository
) {

    fun findAll(): List<Customer>{
        return customerRepository.findAll()
    }

    fun createCustomer(customer: Customer, addresses: List<PostAddressRequest>){
        val customerSaved = customerRepository.save(customer)

        addresses.map { addressRepository.save(it.toAddress(customerSaved)) }
    }
}