package com.rhisco.book.Models;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "tbl_books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    private Long id_lessor;
    private String title;
    private Date date_creation;
    private Date date_loan;
    private String author;

    public Book() {
    }

    public Book(Long id, Long id_lessor, String title, Date date_creation, Date date_loan, String author) {
        this.id = id;
        this.id_lessor = id_lessor;
        this.title = title;
        this.date_creation = date_creation;
        this.date_loan = date_loan;
        this.author = author;
    }

    public Book(Long id_lessor, String title, Date date_creation, Date date_loan, String author) {
        this.id_lessor = id_lessor;
        this.title = title;
        this.date_creation = date_creation;
        this.date_loan = date_loan;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_lessor() {
        return id_lessor;
    }

    public void setId_lessor(Long id_lessor) {
        this.id_lessor = id_lessor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public Date getDate_loan() {
        return date_loan;
    }

    public void setDate_loan(Date date_loan) {
        this.date_loan = date_loan;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book [author=" + author + ", date_creation=" + date_creation + ", date_loan=" + date_loan + ", id=" + id
                + ", id_lessor=" + id_lessor + ", title=" + title + "]";
    }

}
