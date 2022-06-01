package com.rhisco.book;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import com.rhisco.book.Models.Book;
import com.rhisco.book.Repositorys.BooksRepository;
import com.rhisco.book.Services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestBookService {
    @Autowired
    private BookService bookService;

    @MockBean
    private BooksRepository bookRepository;

    @Test
    void testGetDifferenceDaysWithEqualsDate() {
        Date date2 = new Date();
        when(bookRepository.getDateLoan(1L)).thenReturn(date2);
        assert bookService.getDaysDifference(1L) == 1;
    }

    @Test
    void testGetDifferenceDaysWithDifferentDate() {
        Date date = new Date(1346524199000L);
        when(bookRepository.getDateLoan(1L)).thenReturn(date);
        assert bookService.getDaysDifference(1L) != 0;
    }

    @Test
    void testAddBook() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("title");
        book.setAuthor("author");
        book.setDate_creation(new Date());
        book.setDate_loan(new Date());
        assert bookService.addBook(book) == false;
    }

    @Test
    void testUpdateDateWhenIdNotExist() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("title");
        book.setAuthor("author");
        book.setDate_creation(new Date());
        book.setDate_loan(new Date());
        // Retornara false porque el id no existe
        assert bookService.updateDate(book) == false;
    }

    @Test
    void testUpdateDateWhenIdExist() {
        Book book = new Book();
        Optional<Book> bookOptional = Optional.of(book);
        book.setId(1L);
        book.setTitle("title");
        book.setAuthor("author");
        book.setDate_creation(new Date());
        book.setDate_loan(new Date());
        when(bookRepository.existsById(1L)).thenReturn(true);
        when(bookRepository.findById(1L)).thenReturn(bookOptional);
        when(bookRepository.save(book)).thenReturn(book);
        // Retornara true porque el id existe
        assert bookService.updateDate(book) == true;
    }

    @Test
    void testDeleteBookWhenIdNotExist() {
        // Retornara false porque el id no existe
        assert bookService.deleteBook(1L) == false;
    }

    @Test
    void testDeleteBookWhenIdExist() {
        Optional<Book> bookOptional = Optional.of(new Book());
        bookOptional.get().setId(1L);
        when(bookRepository.existsById(1L)).thenReturn(true);
        when(bookRepository.findById(1L)).thenReturn(bookOptional);
        // Retornara true porque el id existe
        assert bookService.deleteBook(1L) == true;
    }
}
