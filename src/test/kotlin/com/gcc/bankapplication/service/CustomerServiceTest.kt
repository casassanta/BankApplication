package com.gcc.bankapplication.service

import com.gcc.bankapplication.model.Customer
import com.gcc.bankapplication.repository.CustomerRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import java.lang.RuntimeException
import java.util.UUID
import javax.persistence.EntityNotFoundException

class CustomerServiceTest{

    private val customerRepository = mockk<CustomerRepository>()
    private val addressService = mockk<AddressService>()
    private val customerService = CustomerService(
        customerRepository = customerRepository,
        addressService = addressService)

    @Nested
    inner class FindAllTests {

        @Test
        fun `should return all customers properly`() {
            val customer = mockk<Customer>()
            every { customerRepository.findAll() } returns listOf(customer)

            val listCustomers = customerService.findAll()

            assertEquals(listOf(customer), listCustomers)
        }

        @Test
        fun `should return empty list of customers properly`() {
            every { customerRepository.findAll() } returns emptyList<Customer>()

            val listCustomer = customerService.findAll()

            assertEquals(emptyList<Customer>(), listCustomer)
        }

        @Test
        fun `should return exception if database is not working properly`() {
            every { customerRepository.findAll() } throws RuntimeException("Database down")

            assertThrows(RuntimeException::class.java) { customerService.findAll() }
        }

    }

    @Test
    fun `should delete a customer from database properly`(){
        // Arranges
        val customerId = UUID.randomUUID()
        val customer = mockk<Customer>()
        val inactiveCustomer = mockk<Customer>()
        every { customerRepository.getById(customerId)} returns customer
        every { customer.copy(status = Customer.Status.INACTIVE) } returns inactiveCustomer
        every { customerRepository.save(inactiveCustomer) } returns inactiveCustomer

        // Act
        customerService.delete(customerId)

        // Asserts
        verify { customerRepository.save(inactiveCustomer) }

    }

    @Test
    fun `should fail delete customer that doesnt exists on database`(){
        val customerId = UUID.randomUUID()
        every { customerRepository.getById(customerId) } throws JpaObjectRetrievalFailureException(EntityNotFoundException())

        assertThrows(JpaObjectRetrievalFailureException::class.java) { customerService.delete(customerId) }
    }


}