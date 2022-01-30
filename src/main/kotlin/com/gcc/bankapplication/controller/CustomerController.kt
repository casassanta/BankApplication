package com.gcc.bankapplication.controller

import com.gcc.bankapplication.controller.request.CreateCustomerRequest
import com.gcc.bankapplication.controller.response.CustomerResponse
import com.gcc.bankapplication.model.enums.Nationality
import com.gcc.bankapplication.service.AddressService
import com.gcc.bankapplication.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
class CustomerController(
    private val customerService: CustomerService,
    private val addressService: AddressService
) {

    @GetMapping("/api/customers")
    fun findAll(@RequestParam nationality: Nationality?): List<CustomerResponse> {

        if (nationality == null) {
            return customerService.findAll().map { customer ->
                customer.toCustomerResponse(
                    addressService.findByCustomer(customer).map { address -> address.toAddressResponse() })
            }
        }

        return customerService.findByNationality(nationality).map { customer ->
            customer.toCustomerResponse(addressService.findByCustomer(customer).map { address -> address.toAddressResponse() })
        }

    }

    @GetMapping("/api/customers/{customerId}")
    fun findById(@PathVariable customerId: UUID): CustomerResponse {
        val customer = customerService.findById(customerId)
        val addresses = addressService.findByCustomer(customer).map { address -> address.toAddressResponse() }

        return customer.toCustomerResponse(addresses)
    }

    @PostMapping("/api/customers")
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(@RequestBody @Valid customerRequest: CreateCustomerRequest) {

        customerService.createCustomer(customerRequest.toCustomer(), customerRequest.addresses)
    }

    @DeleteMapping("/api/customers/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCustomer(@PathVariable customerId: UUID) {
        customerService.delete(customerId)
    }

}