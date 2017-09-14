package com.github.maly7.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Book {
    private Long id;
    private String title;
    private Set<BookAuthor> authors;
    private Set<Label> labels;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<BookAuthor> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<BookAuthor> authors) {
        this.authors = authors;
    }

    @ManyToMany
    @JoinTable(name = "book_label",
            inverseJoinColumns = @JoinColumn(name = "label_id"))
    public Set<Label> getLabels() {
        return labels;
    }

    public void setLabels(Set<Label> labels) {
        this.labels = labels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return new EqualsBuilder()
                .append(id, book.id)
                .append(title, book.title)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(title)
                .toHashCode();
    }
}
