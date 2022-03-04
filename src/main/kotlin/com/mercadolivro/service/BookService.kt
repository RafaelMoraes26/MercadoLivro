package com.mercadolivro.service

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.model.BookModel
import com.mercadolivro.repository.BookRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class BookService(val bookRepository: BookRepository) {

    fun createBook(book: BookModel) {
        bookRepository.save(book)
    }

    fun findAll(): List<BookModel> {
        return bookRepository.findAll().toList()
    }

    fun findActives(): List<BookModel> {
        return bookRepository.findByStatus(BookStatus.ATIVO).toList()
    }

    fun findById(book_id: Int): Optional<BookModel> {
        return bookRepository.findById(book_id)
    }

    fun deleteBookById(bookId: Int) {
        val book = findById(bookId)

        if(book.isPresent) {
            book.get().status = BookStatus.CANCELADO
            updateBook(book.get())
        }

    }

    fun updateBook(book: BookModel) {
        bookRepository.save(book)
    }


}
