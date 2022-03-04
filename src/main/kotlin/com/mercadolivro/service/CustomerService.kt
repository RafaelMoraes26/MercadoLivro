package com.mercadolivro.service

import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.CustomerRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerService(val customerRepository: CustomerRepository) {
    val customers = mutableListOf<CustomerModel>()


    fun getAllCustomers(name: String?): List<CustomerModel> {
        name?.let { return customerRepository.findByNameContaining(it) }
        return customerRepository.findAll().toList()
    }

    fun getSpecificCustomerById(id: Int): Optional<CustomerModel> {
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
        if(!customerRepository.existsById(id)){
            throw Exception()
        }
        customerRepository.deleteById(id)
    }

}