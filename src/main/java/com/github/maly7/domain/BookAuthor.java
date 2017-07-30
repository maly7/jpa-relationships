package com.github.maly7.domain;

import javax.persistence.*;

@Entity
@Table(name = "BOOK_AUTHOR", schema = "PUBLIC", catalog = "APP")
public class BookAuthor {
    private Long authorId;
    private Book bookByBookId;

    @Id
    @Column(name = "AUTHOR_ID")
    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BookAuthor that = (BookAuthor) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(authorId, that.authorId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                .append(authorId)
                .toHashCode();
    }

    @ManyToOne
    @JoinColumn(name = "BOOK_ID", referencedColumnName = "ID", nullable = false)
    public Book getBookByBookId() {
        return bookByBookId;
    }

    public void setBookByBookId(Book bookByBookId) {
        this.bookByBookId = bookByBookId;
    }
}
