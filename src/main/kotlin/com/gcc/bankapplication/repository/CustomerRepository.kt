package com.gcc.bankapplication.repository

import com.gcc.bankapplication.model.Customer
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CustomerRepository: JpaRepository<Customer, UUID> {

}