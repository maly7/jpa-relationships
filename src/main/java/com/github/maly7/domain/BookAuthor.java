package com.github.maly7.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
@Table(name = "BOOK_AUTHOR")
@PrimaryKeyJoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID")
public class BookAuthor extends Writer {
    private Writer author;
    private Book book;

    @OneToOne
    @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    public Writer getAuthor() {
        return author;
    }

    public void setAuthor(Writer writer) {
        this.author = writer;
    }


    @ManyToOne
    @JoinColumn(name = "BOOK_ID", referencedColumnName = "ID")
    public Book getBook() {
        return book;
    }

    public void setBook(Book bookByBookId) {
        this.book = bookByBookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BookAuthor that = (BookAuthor) o;

        return new EqualsBuilder()
                .append(author, that.author)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(author)
                .toHashCode();
    }
}
