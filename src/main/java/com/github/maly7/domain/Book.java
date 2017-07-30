package com.github.maly7.domain;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Book {
    private Long id;
    private String title;
    private Collection<BookAuthor> bookAuthorsById;

    @Id
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(id, book.id)
                .append(title, book.title)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                .append(id)
                .append(title)
                .toHashCode();
    }

    @OneToMany(mappedBy = "bookByBookId")
    public Collection<BookAuthor> getBookAuthorsById() {
        return bookAuthorsById;
    }

    public void setBookAuthorsById(Collection<BookAuthor> bookAuthorsById) {
        this.bookAuthorsById = bookAuthorsById;
    }
}
