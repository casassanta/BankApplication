package com.gcc.bankapplication.controller

import com.gcc.bankapplication.controller.request.PostCustomerRequest
import com.gcc.bankapplication.model.Customer
import com.gcc.bankapplication.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus

@Controller
class CustomerController(
    val customerService: CustomerService
) {

    @GetMapping("/api/customers")
    fun findAll(): List<Customer> {
        return customerService.findAll()
    }

    @PostMapping("/api/customers")
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(@RequestBody customerRequest: PostCustomerRequest){
        customerService.createCustomer(customerRequest.toCustomer())
    }
}