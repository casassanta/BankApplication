package com.gcc.bankapplication.service

import com.gcc.bankapplication.controller.request.CreateAddressRequest
import com.gcc.bankapplication.model.Address
import com.gcc.bankapplication.model.Customer
import com.gcc.bankapplication.model.enums.Nationality
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

    fun findAll(): List<Customer> {
        return customerRepository.findAll()
    }

    fun findByNationality(nationality: Nationality): List<Customer> {
        return customerRepository.findByNationality(nationality)
    }

    @Transactional
    fun createCustomer(customer: Customer, addresses: List<CreateAddressRequest>) {
        val customerSaved = customerRepository.save(customer)
        addresses.map { addressRepository.save(it.toAddress(customerSaved)) }
    }

    fun findById(customerId: UUID): Customer {
        return customerRepository.getById(customerId)
    }

    fun delete(customerId: UUID) {
        val customer = findById(customerId).copy(status = Customer.Status.INACTIVE)
        customerRepository.save(customer)
    }

    @Transactional
    fun update(customer: Customer, addresses: List<Address>){
        customerRepository.save(customer)
        addresses.map { addressRepository.save(it) }
    }

}