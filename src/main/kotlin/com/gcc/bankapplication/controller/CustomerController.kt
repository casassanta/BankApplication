package com.gcc.bankapplication.controller

import com.gcc.bankapplication.controller.request.PostCustomerRequest
import com.gcc.bankapplication.controller.response.CustomerResponse
import com.gcc.bankapplication.service.AddressService
import com.gcc.bankapplication.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*
import javax.validation.Valid

@RestController
class CustomerController(
    private val customerService: CustomerService,
    private val addressService: AddressService
) {

    @GetMapping("/api/customers")
    fun findAll(): List<CustomerResponse> {
        return customerService.findAll().map { customer ->
            customer.toCustomerResponse(addressService.findByCustomer(customer).map { address -> address.toAddressResponse() })
        }
    }

    @GetMapping("/api/customers/{customerId}")
    fun findById(@PathVariable customerId: String): CustomerResponse {
        val customer = customerService.findById(UUID.fromString(customerId))
        val addresses = addressService.findByCustomer(customer).map { address -> address.toAddressResponse() }

        return customer.toCustomerResponse(addresses)
    }

    @PostMapping("/api/customers")
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(@RequestBody @Valid customerRequest: PostCustomerRequest){

        try{
            DateTimeFormatter.ofPattern("uuuu-MM-dd").parse(customerRequest.birthDate.toString())
        }catch(e: DateTimeParseException){
            null
        }

        customerService.createCustomer(customerRequest.toCustomer(), customerRequest.addresses)
    }

}