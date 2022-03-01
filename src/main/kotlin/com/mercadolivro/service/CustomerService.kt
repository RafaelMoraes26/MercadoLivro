package com.mercadolivro.service

import com.mercadolivro.model.CustomerModel
import org.springframework.stereotype.Service

@Service
class CustomerService {
    val customers = mutableListOf<CustomerModel>()


    fun getAllCustomers(name: String?): List<CustomerModel> {
        name?.let { return customers.filter { it.name.contains(name, true) } }
        return customers
    }

    fun getSpecificCustomer(id: Int): CustomerModel? {
        return customers.find { c -> c.id == id }
    }

    fun createCustomer(customer: CustomerModel) {
        val lastId: Int = customers.findLast { true }?.id ?: 0
        customers.add(CustomerModel(lastId + 1, customer.name, customer.email))
    }

    fun createMultipleCustomers(customer: List<CustomerModel>) {
        customer.forEach { c ->
            val lastId: Int = customers.findLast { true }?.id ?: 0
            customers.add(CustomerModel(lastId + 1, c.name, c.email))
        }
    }

    fun updateCustomer(customer: CustomerModel) {
        customers.find { c -> c.id == customer.id }.let {
            it?.name = customer.name
            it?.email = customer.email
        }
    }

    fun deleteCustomer(id: Int) {
        customers.removeIf { it.id == id }
    }

}