package com.mercadolivro.service

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.CustomerRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerService(val customerRepository: CustomerRepository, val bookService: BookService) {
    val customers = mutableListOf<CustomerModel>()


    fun findAllCustomers(name: String?): List<CustomerModel> {
        name?.let { return customerRepository.findByNameContaining(it) }
        return customerRepository.findAll().toList()
    }

    fun findSpecificCustomerById(id: Int): Optional<CustomerModel> {
        return customerRepository.findById(id)
    }

    fun createCustomer(customer: CustomerModel) {
        customerRepository.save(customer)
    }

    fun createMultipleCustomers(customer: List<CustomerModel>) {
        customer.forEach { c ->
            customerRepository.save(c)
        }
    }

    fun updateCustomer(customer: CustomerModel) {
        if(!customerRepository.existsById(customer.id!!)){
            throw Exception()
        }
        customerRepository.save(customer)
    }

    fun deleteCustomer(id: Int) {
        val customer = findSpecificCustomerById(id)
        if(customer.isPresent) {
            customer.get().status = CustomerStatus.INATIVO
            bookService.deleteBookByCustomer(customer)
            customerRepository.save(customer.get())
        }
    }

}