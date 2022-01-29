package com.gcc.bankapplication.service

import com.gcc.bankapplication.controller.request.PostAddressRequest
import com.gcc.bankapplication.model.Customer
import com.gcc.bankapplication.model.enums.CustomerStatus
import com.gcc.bankapplication.repository.AddressRepository
import com.gcc.bankapplication.repository.CustomerRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional


@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val addressRepository: AddressRepository
) {

    fun findAll(): List<Customer>{
        return customerRepository.findAll()
    }

    @Transactional
    fun createCustomer(customer: Customer, addresses: List<PostAddressRequest>){
        val customerSaved = customerRepository.save(customer)
        addresses.map { addressRepository.save(it.toAddress(customerSaved)) }
    }

    fun findById(customerId: UUID): Customer {
        return customerRepository.findById(customerId).orElseThrow()
    }

    fun delete(customerId: UUID){
        var customer = findById(customerId)
        customer.status = CustomerStatus.INACTIVE
        customerRepository.save(customer)
    }
}