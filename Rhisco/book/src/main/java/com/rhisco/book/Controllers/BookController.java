package com.rhisco.book.Controllers;

import java.util.HashMap;
import java.util.Map;
import com.rhisco.book.Models.Book;
import com.rhisco.book.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@CrossOrigin(origins = "*")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/days/{id}")
    public Map<String,Integer> getDaysDifference(@PathVariable("id") Long id) {
        Map<String,Integer> response = new HashMap<>();
        response.put("days", bookService.getDaysDifference(id));
        return response;
    }

    @GetMapping("/")
    public Iterable<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable("id") Long id) {
        return bookService.getBook(id);
    }

    @PostMapping("/addBook")
    public Map<String,String> addBook(@RequestBody Book book){
        HashMap<String,String> response = new HashMap<>();
        if(bookService.addBook(book)) {
            response.put("message","Book already exists");
            response.put("status","fail");
            return response;
        }
        else {
            response.put("message","Book added successfully");
            response.put("status","success");
            return response;
        }
    }

    @PutMapping("/updateDate")
    public Map<String,String> updateDate(@RequestBody Book newBook){
        HashMap<String,String> response = new HashMap<>();
        if(bookService.updateDate(newBook)) {
            response.put("message","Book updated successfully");
            response.put("status","success");
            return response;
        }
        else {
            response.put("message","Book does not exist");
            response.put("status","fail");
            return response;
        }
    }

    @DeleteMapping("/deleteBook/{id}")
    public Map<String,String> deleteBook(@PathVariable("id") Long id){
        HashMap<String,String> response = new HashMap<>();
        if(bookService.deleteBook(id)) {
            response.put("message","Book deleted successfully");
            response.put("status","success");
            return response;
        }
        else {
            response.put("message","Book does not exist");
            response.put("status","fail");
            return response;
        }
    }

}
