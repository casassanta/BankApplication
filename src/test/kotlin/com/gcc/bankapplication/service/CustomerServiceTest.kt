package com.gcc.bankapplication.service

import com.gcc.bankapplication.controller.request.CreateAddressRequest
import com.gcc.bankapplication.controller.request.UpdateAddressRequest
import com.gcc.bankapplication.controller.request.UpdateCustomerRequest
import com.gcc.bankapplication.controller.request.UpdateDocumentRequest
import com.gcc.bankapplication.controller.response.AddressResponse
import com.gcc.bankapplication.controller.response.CustomerResponse
import com.gcc.bankapplication.controller.response.DocumentResponse
import com.gcc.bankapplication.model.Address
import com.gcc.bankapplication.model.Customer
import com.gcc.bankapplication.model.Document
import com.gcc.bankapplication.model.enums.Nationality
import com.gcc.bankapplication.repository.AddressRepository
import com.gcc.bankapplication.repository.CustomerRepository
import com.gcc.bankapplication.utils.ObjectMother
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import java.lang.RuntimeException
import java.time.LocalDate
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

    @Nested
    inner class DeleteCustomer {

        @Test
        fun `should delete a customer from database properly`() {
            // Arranges
            val customerId = UUID.randomUUID()
            val customer = mockk<Customer>()
            val inactiveCustomer = mockk<Customer>()
            every { customerRepository.getById(customerId) } returns customer
            every { customer.copy(status = Customer.Status.INACTIVE) } returns inactiveCustomer
            every { customerRepository.save(inactiveCustomer) } returns inactiveCustomer

            // Act
            customerService.delete(customerId)

            // Asserts
            verify { customerRepository.save(inactiveCustomer) }

        }

        @Test
        fun `should fail delete customer that doesnt exists on database`() {
            val customerId = UUID.randomUUID()
            every { customerRepository.getById(customerId) } throws JpaObjectRetrievalFailureException(
                EntityNotFoundException()
            )

            assertThrows(JpaObjectRetrievalFailureException::class.java) { customerService.delete(customerId) }
        }
    }

    @Test
    fun `should save customer on database properly`(){
        // 3- Given
        val customer = Customer(
            id = UUID.randomUUID(),
            firstName = "Gui",
            lastName = "Casassanta",
            email = "g@gmail.com",
            birthDate = LocalDate.parse("1991-12-10"),
            nationality = Nationality.BRAZIL,
            document = Document(Document.Type.ID, "10565510622"),
            status = Customer.Status.ACTIVE
        )
        val addressesCreateList = listOf(
            CreateAddressRequest("BILLING", "10","João", "100", "ap 101"),
            CreateAddressRequest("DELIVERY", "20","Maria", "200", "ap 202"),
        )
        val addresses = listOf(
            Address(UUID.randomUUID(), Address.Type.BILLING, "10","João", "100", "ap 101", customer),
            Address(UUID.randomUUID(), Address.Type.DELIVERY, "20","Maria", "200", "ap 202", customer)
        )
        every { customerRepository.save(customer) } returns customer
        every { addressService.createAddresses(addresses) } returns Unit

        // 1- When
        customerService.createCustomer(customer, addressesCreateList)

        // 2- Then
        verify { customerRepository.save(customer) }
        verify { addressService.createAddresses(addresses) }
    }

    @Test
    fun `should return exception if customer database is not working properly`() {
        val customer = mockk<Customer>()
        val addresses = mockk<List<CreateAddressRequest>>()
        every { customerRepository.save(customer) } throws JpaObjectRetrievalFailureException(EntityNotFoundException())

        assertThrows(JpaObjectRetrievalFailureException::class.java) { customerService.createCustomer(customer, addresses) }
    }

    @Test
    fun `should update customer properly`() {
        val id = UUID.randomUUID()
        val oldCustomer = ObjectMother.getCustomer(id)
        val oldAddresses = listOf(
            ObjectMother.getBillingAddress(oldCustomer),
            ObjectMother.getDeliveryAddress(oldCustomer)
        )
        val updateCustomer = UpdateCustomerRequest(
            firstName = "Nielsen",
            lastName = "Martins",
            email ="n@b.c",
            birthDate = "2022-01-01",
            nationality = "ARGENTINA",
            document = UpdateDocumentRequest("PASSPORT", "456"),
            addresses = listOf(
                UpdateAddressRequest("BILLING", "3", "3", "3", "3"),
                UpdateAddressRequest("DELIVERY", "4", "4", "4", "4")
            )
        )
        val newCustomer = oldCustomer.copy(
            firstName = updateCustomer.firstName,
            lastName = updateCustomer.lastName,
            email = updateCustomer.email,
            birthDate = LocalDate.parse(updateCustomer.birthDate),
            nationality = Nationality.valueOf(updateCustomer.nationality),
            document = Document(Document.Type.valueOf(updateCustomer.document.type), updateCustomer.document.number),
        )
        val newAddresses = listOf(
            Address(
                id = oldAddresses[0].id,
                type = Address.Type.valueOf(updateCustomer.addresses[0].type),
                postCode = updateCustomer.addresses[0].postCode,
                address = updateCustomer.addresses[0].address,
                number = updateCustomer.addresses[0].number,
                complement = updateCustomer.addresses[0].complement,
                customer = newCustomer
            ),
            Address(
                id = oldAddresses[1].id,
                type = Address.Type.valueOf(updateCustomer.addresses[1].type),
                postCode = updateCustomer.addresses[1].postCode,
                address = updateCustomer.addresses[1].address,
                number = updateCustomer.addresses[1].number,
                complement = updateCustomer.addresses[1].complement,
                customer = newCustomer
            )
        )
        val customerResponse = CustomerResponse(
            id = newCustomer.id,
            firstName = "Nielsen",
            lastName = "Martins",
            email ="n@b.c",
            birthDate = LocalDate.parse("2022-01-01"),
            nationality = Nationality.valueOf("ARGENTINA"),
            document = DocumentResponse(Document.Type.PASSPORT, "456"),
            addresses = listOf(
                AddressResponse(oldAddresses[0].id, Address.Type.BILLING,"3", "3", "3", "3"),
                AddressResponse(oldAddresses[1].id, Address.Type.DELIVERY,"4", "4", "4", "4"))
        )
        every { customerRepository.getById(id) } returns oldCustomer
        every { addressService.findByCustomer(oldCustomer) } returns oldAddresses
        every { customerRepository.save(newCustomer) } returns newCustomer
        every { addressService.createAddresses(newAddresses)} returns Unit

        val result = customerService.update(id, updateCustomer)

        verify { customerRepository.save(newCustomer) }
        verify { addressService.createAddresses(newAddresses)}
        assertEquals( customerResponse, result)
    }

}
