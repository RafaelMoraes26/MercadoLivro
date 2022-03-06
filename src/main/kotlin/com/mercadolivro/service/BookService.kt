package com.mercadolivro.service

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.BookRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class BookService(val bookRepository: BookRepository) {

    fun createBook(book: BookModel) {
        bookRepository.save(book)
    }

    fun findAllBooks(): List<BookModel> {
        return bookRepository.findAll().toList()
    }

    fun findActiveBooks(): List<BookModel> {
        return bookRepository.findByStatus(BookStatus.ATIVO).toList()
    }

    fun findSpecificBookById(book_id: Int): Optional<BookModel> {
        return bookRepository.findById(book_id)
    }

    fun deleteBookById(bookId: Int) {
        val book = findSpecificBookById(bookId)

        if(book.isPresent) {
            book.get().status = BookStatus.CANCELADO
            updateBook(book.get())
        }

    }

    fun updateBook(book: BookModel) {
        bookRepository.save(book)
    }

    fun deleteBookByCustomer(customer: Optional<CustomerModel>) {
        val books = bookRepository.findByCustomer(customer)
        books.forEach { book -> if(book.status == BookStatus.ATIVO) book.status = BookStatus.DELETADO }
        bookRepository.saveAll(books)
    }


}
