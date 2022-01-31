package com.gcc.bankapplication.service

import com.gcc.bankapplication.controller.request.CreateAddressRequest
import com.gcc.bankapplication.controller.request.UpdateCustomerRequest
import com.gcc.bankapplication.controller.response.CustomerResponse
import com.gcc.bankapplication.model.Address
import com.gcc.bankapplication.model.Customer
import com.gcc.bankapplication.model.enums.Nationality
import com.gcc.bankapplication.repository.AddressRepository
import com.gcc.bankapplication.repository.CustomerRepository
import org.springframework.stereotype.Service
import java.lang.Exception
import java.util.*
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional


@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val addressRepository: AddressRepository,
    private val addressService: AddressService
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
    fun update(customerId: UUID, customerUpdate: UpdateCustomerRequest): CustomerResponse {

        val previousCustomer = findById(customerId)
        val previousAddresses = addressService.findByCustomer(previousCustomer)

        if(previousCustomer.status == Customer.Status.INACTIVE){
            throw(EntityNotFoundException())
        }

        val updatedCustomer = customerUpdate.toCustomerModel(previousCustomer)
        val updatedAddresses = customerUpdate.addresses.map { address ->
            address.toAddress(previousAddresses.first { oldAddress -> oldAddress.type == address.type}, updatedCustomer)
        }

        customerRepository.save(updatedCustomer)
        updatedAddresses.map { address -> addressRepository.save(address) }

        return updatedCustomer.toCustomerResponse(updatedAddresses.map { it.toAddressResponse() })
    }

}