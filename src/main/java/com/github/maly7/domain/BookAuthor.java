package com.github.maly7.domain;

import javax.persistence.*;

@Entity
@Table(name = "BOOK_AUTHOR")
@PrimaryKeyJoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID")
public class BookAuthor extends Writer {
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "BOOK_ID", referencedColumnName = "ID")
    public Book getBook() {
        return book;
    }

    public void setBook(Book bookByBookId) {
        this.book = bookByBookId;
    }

}
