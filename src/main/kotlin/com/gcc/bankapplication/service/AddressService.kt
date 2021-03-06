package com.gcc.bankapplication.service

import com.gcc.bankapplication.model.Address
import com.gcc.bankapplication.model.Customer
import com.gcc.bankapplication.repository.AddressRepository
import org.springframework.stereotype.Service

@Service
class AddressService(
    private val addressRepository: AddressRepository
) {

    fun findByCustomer(customer: Customer): List<Address> {
        return addressRepository.findByCustomer(customer)
    }

    fun createAddresses(addresses: List<Address>) {
        addresses.map { address -> addressRepository.save(address) }
    }


}