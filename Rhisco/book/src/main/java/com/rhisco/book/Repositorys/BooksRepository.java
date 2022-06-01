package com.rhisco.book.Repositorys;

import java.util.Date;
import com.rhisco.book.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BooksRepository extends JpaRepository<Book, Long> {
    @Query(value="SELECT date_loan FROM tbl_books WHERE id = :id", nativeQuery = true)
    Date getDateLoan(@Param("id") Long id);
}
