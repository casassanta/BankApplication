package com.gcc.bankapplication.service

import com.gcc.bankapplication.model.Address
import org.springframework.stereotype.Service

@Service
class AddressService(

) {

    fun createAddresses(addresses: List<Address>){
        println(addresses)
    }
}