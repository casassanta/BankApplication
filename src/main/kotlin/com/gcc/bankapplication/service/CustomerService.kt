package com.gcc.bankapplication.service

import com.gcc.bankapplication.controller.request.CreateAddressRequest
import com.gcc.bankapplication.controller.request.UpdateCustomerRequest
import com.gcc.bankapplication.controller.response.CustomerResponse
import com.gcc.bankapplication.exception.InvalidAddressesException
import com.gcc.bankapplication.model.Address
import com.gcc.bankapplication.model.Customer
import com.gcc.bankapplication.model.enums.Nationality
import com.gcc.bankapplication.repository.AddressRepository
import com.gcc.bankapplication.repository.CustomerRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional


@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
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
        val addresses = addresses.map { address -> address.toAddress(customerSaved) }
        addressService.createAddresses(addresses)
    }

    fun findById(customerId: UUID): Customer {
        return customerRepository.getById(customerId)
    }

    fun delete(customerId: UUID) {
        val customer = findById(customerId).copy(status = Customer.Status.INACTIVE)
        customerRepository.save(customer)
    }

    fun update(customerId: UUID, customerUpdate: UpdateCustomerRequest): CustomerResponse {

        val oldCustomer = findById(customerId)
        val newCustomer = customerUpdate.toCustomerModel(customerId, oldCustomer.status)

        if(newCustomer.status == Customer.Status.INACTIVE){
            throw EntityNotFoundException()
        }

        validateAddresses(customerUpdate)

        val oldAddresses = addressService.findByCustomer(oldCustomer)
        val newAddresses = customerUpdate.addresses.map{address ->
            address.toAddress(oldAddresses.first{oldAddress ->  oldAddress.type == address.type}.id, newCustomer)
        }

        saveUpdatedCustomer(newCustomer, newAddresses)

        return newCustomer.toCustomerResponse(newAddresses.map { address -> address.toAddressResponse() })
    }

    private fun validateAddresses(customerUpdate: UpdateCustomerRequest) {
        // pode receber os dois tipos nulable e dentro do método, eu só extraio os dois dentro de uma lista de string e verifico depois
        if (customerUpdate.addresses.none { address -> address.type == Address.Type.BILLING }) {
            throw InvalidAddressesException("Missing Billing Address")
        }
        if (customerUpdate.addresses.none { address -> address.type == Address.Type.DELIVERY }) {
            throw InvalidAddressesException("Missing Delivery Address")
        }

        if(customerUpdate.addresses.size != 2){
            throw InvalidAddressesException("It should have only one Billing Address and only one Delivery Address")
        }
    }

    @Transactional
    fun saveUpdatedCustomer(customer: Customer, addresses: List<Address>){
        customerRepository.save(customer)
        addressService.createAddresses(addresses)
    }

}