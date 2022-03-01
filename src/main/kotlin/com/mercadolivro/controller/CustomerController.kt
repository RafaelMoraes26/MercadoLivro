package com.mercadolivro.controller

import com.mercadolivro.controller.request.PostCustomerRequest
import com.mercadolivro.controller.request.PutCustomerRequest
import com.mercadolivro.model.CustomerModel
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers")
class CustomerController {

    val customers = mutableListOf<CustomerModel>()


    @GetMapping
    fun getAllCustomers(@RequestParam name: String?): List<CustomerModel> {
        name?.let { return customers.filter { it.name.contains(name, true) } }
        return customers
    }

    @GetMapping("/{id}")
    fun getSpecificCustomer(@PathVariable id: Int): CustomerModel? {
        return customers.find { c -> c.id == id }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(@RequestBody customer: PostCustomerRequest) {
        val lastId: Int = customers.findLast { true }?.id ?: 0
        customers.add(CustomerModel(lastId + 1, customer.name, customer.email))
    }

    @PostMapping("/multiple")
    @ResponseStatus(HttpStatus.CREATED)
    fun createMultipleCustomers(@RequestBody customer: List<PostCustomerRequest>) {
        customer.forEach { c ->
            val lastId: Int = customers.findLast { true }?.id ?: 0
            customers.add(CustomerModel(lastId + 1, c.name, c.email))
        }

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCustomer(@PathVariable id: Int) {
        customers.removeIf { it.id == id }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateCustomer(@PathVariable id: Int, @RequestBody customer: PutCustomerRequest) {
        customers.find { c -> c.id == id }.let {
            it?.name = customer.name
            it?.email = customer.email
        }
    }
}