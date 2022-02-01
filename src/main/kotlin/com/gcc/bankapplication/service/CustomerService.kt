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
import java.time.LocalDate
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

    fun update(customerId: UUID, customerUpdate: UpdateCustomerRequest): CustomerResponse {

        val updatedCustomer = findById(customerId).copy(
            firstName = customerUpdate.firstName,
            lastName = customerUpdate.lastName,
            birthDate = LocalDate.parse(customerUpdate.birthDate),
            nationality = customerUpdate.nationality,
            document = customerUpdate.document.toDocument()
        )

        if(updatedCustomer.status == Customer.Status.INACTIVE){
            throw(EntityNotFoundException())
        }

        val updatedAddresses = addressService.findByCustomer(updatedCustomer).map { address ->
            address.copy(
                type = customerUpdate.addresses.first { newAddress -> newAddress.type == address.type }.type,
                postCode = customerUpdate.addresses.first { newAddress -> newAddress.type == address.type }.postCode,
                address = customerUpdate.addresses.first { newAddress -> newAddress.type == address.type }.address,
                number = customerUpdate.addresses.first { newAddress -> newAddress.type == address.type }.number,
                complement = customerUpdate.addresses.first { newAddress -> newAddress.type == address.type }.complement
            )
        }

        saveUpdatedCustomer(updatedCustomer, updatedAddresses)
        return updatedCustomer.toCustomerResponse(updatedAddresses.map { it.toAddressResponse() })

        //val previousCustomer = findById(customerId)
        //val previousAddresses = addressService.findByCustomer(previousCustomer)

        //if(previousCustomer.status == Customer.Status.INACTIVE){
        //    throw(EntityNotFoundException())
        //}

        //val updatedCustomer = customerUpdate.toCustomerModel(previousCustomer)
        //val updatedAddresses = customerUpdate.addresses.map { address ->
        //    address.toAddress(previousAddresses.first { oldAddress -> oldAddress.type == address.type}, updatedCustomer)
        //}

        //saveUpdatedCustomer(updatedCustomer, updatedAddresses)

        //return updatedCustomer.toCustomerResponse(updatedAddresses.map { it.toAddressResponse() })
    }

    @Transactional
    fun saveUpdatedCustomer(customer: Customer, addresses: List<Address>){
        customerRepository.save(customer)
        addresses.map { address -> addressRepository.save(address) }
    }

}