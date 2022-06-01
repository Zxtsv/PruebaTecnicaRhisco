package com.rhisco.book;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.Optional;

import com.rhisco.book.Models.Book;
import com.rhisco.book.Repositorys.BooksRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TestBookRepository {
    @Autowired
    private BooksRepository bookRepository;

    @Test
    public void testSaveBook() {
        // Test para saber si se guarda un libro
        Book book = new Book(1L,"Test Book", new Date(), new Date(), "Test Author");
        Book saveBook = bookRepository.save(book);
        assertNotNull(saveBook);
    }

    @Test
    public void testGetDateLoan() {
        // Test para saber si se obtiene la fecha de prestamo
        Book book = new Book(1L,"Test Book", new Date(), new Date(), "Test Author");
        bookRepository.save(book);
        Date saveBook = bookRepository.getDateLoan(book.getId());
        assertNotNull(saveBook);
    }

    @Test void testFindAll() {
        // Test para saber si se obtienen todos los libros
        Book book = new Book(1L,"Test Book", new Date(), new Date(), "Test Author");
        bookRepository.save(book);
        assertNotNull(bookRepository.findAll());
    }

    @Test void testFindById() {
        // Test para saber si se obtiene un libro por id
        Book book = new Book(1L,"Test Book", new Date(), new Date(), "Test Author");
        bookRepository.save(book);
        assertNotNull(bookRepository.findById(book.getId()));
    }

    @Test void testDeleteById() {
        // Test para saber si se elimina un libro por id
        Book book = new Book(1L,"Test Book", new Date(), new Date(), "Test Author");
        bookRepository.save(book);
        bookRepository.deleteById(book.getId());
        assertNotNull(bookRepository.findById(book.getId()));
    }

    @Test void testExistsById() {
        // Test para saber si existe un libro por id
        Book book = new Book(1L,"Test Book", new Date(), new Date(), "Test Author");
        bookRepository.save(book);
        assertNotNull(bookRepository.existsById(book.getId()));
    }

    @Test void testIsPresent() {
        // Test para saber si existe un libro en una lista filtrada
        Book book = new Book(1L,"Test Book", new Date(), new Date(), "Test Author");
        bookRepository.save(book);
        Optional<Book> bookOptional = bookRepository.findAll().stream().filter(b -> b.getId().equals(book.getId())).findFirst();
        assertNotNull(bookOptional.isPresent());
    }
}
