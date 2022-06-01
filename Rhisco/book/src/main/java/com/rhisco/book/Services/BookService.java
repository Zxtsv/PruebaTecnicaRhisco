package com.rhisco.book.Services;

import java.util.Date;
import java.util.Optional;
import com.rhisco.book.Models.Book;
import com.rhisco.book.Repositorys.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.joda.time.DateTime;
import org.joda.time.Days;

@Service
public class BookService {
    private final BooksRepository booksRepository;

    @Autowired
    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public Integer getDaysDifference(Long id) {
        Date date_now = new Date();
        Date date_loan = booksRepository.getDateLoan(id);
        Integer days = Days.daysBetween(new DateTime(date_now), new DateTime(date_loan)).getDays();
        return days+1;
    }

    public boolean addBook(Book book) {
        try {
            // Primer filtro para ver si el titulo ya existe
            Optional<Book> books = booksRepository.findAll().stream().filter(b -> b.getTitle().equals(book.getTitle()))
                    .findFirst();

            // Segundo filtro en caso de que el titulo ya exista pero no sea del mismo autor
            books = books.filter(b -> b.getAuthor().equals(book.getAuthor()));

            // Si el titulo ya existe y el autor es el mismo, no se agrega
            if (books.isPresent()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        // Si no existe, se agrega
        booksRepository.save(book);
        return false;
    }

    public boolean updateDate(Book newBook) {
        // Primer filtro para ver si el titulo existe
        if(booksRepository.existsById(newBook.getId())){
            Book book = booksRepository.findById(newBook.getId()).get();
            book.setDate_loan(newBook.getDate_loan());
            // Si el titulo existe, se actualiza
            booksRepository.save(book);
            return true;
        }
        // Si no existe, no se actualiza
        return false;
    }

    public boolean deleteBook(Long id) {
        // Primer filtro para ver si el titulo existe
        if(booksRepository.existsById(id)){
            booksRepository.deleteById(id);
            return true;
        }
        // Si no existe, no se elimina
        return false;
    }

    public Iterable<Book> getAllBooks() {
        return booksRepository.findAll();
    }

    public Book getBook(Long id) {
        return booksRepository.findById(id).get();
    }
}
