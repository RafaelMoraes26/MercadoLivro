package com.mercadolivro.controller

import com.mercadolivro.controller.request.PostBookRequest
import com.mercadolivro.controller.request.PutBookRequest
import com.mercadolivro.extension.toBookModel
import com.mercadolivro.model.BookModel
import com.mercadolivro.service.BookService
import com.mercadolivro.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.Optional

@RestController
@RequestMapping("books")
class BookController(
    val bookService: BookService,
    val customerService: CustomerService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBook(@RequestBody request: PostBookRequest) {
        val customer = customerService.findSpecificCustomerById(request.customerId)
        bookService.createBook(request.toBookModel(customer.get()))
    }

    @GetMapping
    fun findAllBooks(): List<BookModel> {
        return bookService.findAllBooks()
    }

    @GetMapping("/active")
    fun findActiveBooks(): List<BookModel> {
        return bookService.findActiveBooks()
    }

    @GetMapping("/specific")
    fun findBookById(@RequestParam book_id: Int): Optional<BookModel> {
        return bookService.findSpecificBookById(book_id)
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBook(@RequestParam book_id: Int) {
        bookService.deleteBookById(book_id)
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateBook(@RequestParam book_id: Int, @RequestBody book: PutBookRequest) {
        val bookSaved = bookService.findSpecificBookById(book_id)
        if(bookSaved.isPresent) bookService.updateBook(book.toBookModel(bookSaved.get()))
    }

}