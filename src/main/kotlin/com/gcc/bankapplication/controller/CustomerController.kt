package com.gcc.bankapplication.controller

import com.gcc.bankapplication.controller.request.PostCustomerRequest
import com.gcc.bankapplication.controller.response.CustomerResponse
import com.gcc.bankapplication.model.Customer
import com.gcc.bankapplication.service.AddressService
import com.gcc.bankapplication.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
class CustomerController(
    val customerService: CustomerService,
    val addressService: AddressService
) {

    @GetMapping("/api/customers")
    fun findAll(): List<Customer> {
        return customerService.findAll()
    }

    @GetMapping("/api/customers/{customerId}")
    fun findById(@PathVariable customerId: String): CustomerResponse {
        val customer = customerService.findById(UUID.fromString(customerId))
        val addresses = addressService.findByCustomer(customer).map { it.toAddressResponse() }
        println(customer.toCustomerResponse(addresses))

        return customer.toCustomerResponse(addresses)
    }

    @PostMapping("/api/customers")
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(@RequestBody customerRequest: PostCustomerRequest){
        customerService.createCustomer(customerRequest.toCustomer(), customerRequest.addresses)
    }
}