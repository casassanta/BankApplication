package com.gcc.bankapplication.service

import com.gcc.bankapplication.model.Address
import com.gcc.bankapplication.model.Customer
import com.gcc.bankapplication.repository.AddressRepository
import org.springframework.stereotype.Service

@Service
class AddressService(
    val addressRepository: AddressRepository
) {

    fun findByCustomer(customer: Customer): List<Address>{
        return addressRepository.findByCustomer(customer)
    }
}