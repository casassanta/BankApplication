package com.gcc.bankapplication.repository

import com.gcc.bankapplication.model.Address
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AddressRepository: JpaRepository<Address, UUID> {
}