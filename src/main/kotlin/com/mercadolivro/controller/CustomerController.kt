package com.mercadolivro.controller

import com.mercadolivro.controller.request.PostCustomerRequest
import com.mercadolivro.controller.request.PutCustomerRequest
import com.mercadolivro.extension.toCustomerModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.Optional

@RestController
@RequestMapping("/customers")
class CustomerController (val customerService: CustomerService) {

    @GetMapping
    fun getAllCustomers(@RequestParam name: String?): List<CustomerModel> {
        return customerService.findAllCustomers(name)
    }

    @GetMapping("/specific")
    fun getSpecificCustomer(@RequestParam customer_id: Int): Optional<CustomerModel> {
        return customerService.findSpecificCustomerById(customer_id)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(@RequestBody customer: PostCustomerRequest) {
        customerService.createCustomer(customer.toCustomerModel())
    }

    @PostMapping("/multiple")
    @ResponseStatus(HttpStatus.CREATED)
    fun createMultipleCustomers(@RequestBody customer: List<PostCustomerRequest>) {
        val customers = mutableListOf<CustomerModel>()
        customer.forEach { c -> customers.add(c.toCustomerModel() )}
        customerService.createMultipleCustomers(customers)
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateCustomer(@RequestParam customer_id: Int, @RequestBody customer: PutCustomerRequest) {
        val customerSaved = customerService.findSpecificCustomerById(customer_id)
        if(customerSaved.isPresent) customerService.updateCustomer(customer.toCustomerModel(customerSaved.get()))
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCustomer(@RequestParam customer_id: Int) {
        customerService.deleteCustomer(customer_id)
    }
}