package com.github.maly7.domain

import com.github.maly7.support.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired

class NonTransactionalBookCreationSpec extends IntegrationSpec {

    @Autowired
    BookRepository bookRepository

    @Autowired
    BookAuthorRepository bookAuthorRepository

    void 'A book with an author should be persisted'() {
        given: 'A persisted book'
        Book book = bookRepository.save(new Book(title: 'A Storm of Swords'))

        when: 'Adding an author'
        book.authors = Collections.singleton(new BookAuthor(book: book, name: 'Author Person'))
        Book bookWithAuthor = bookRepository.save(book)

        then: 'The book with the author has been persisted'
        bookWithAuthor.authors.every { it.id }
    }

    void 'Not setting the book on the BookAuthor should not persist the BookAuthor'() {
        given: 'A persisted book'
        Book book = bookRepository.save(new Book(title: 'A Storm of Swords'))

        when: 'Adding an author without setting both sides of the relationship'
        book.authors = Collections.singleton(new BookAuthor(name: 'Author Person'))
        Book bookWithAuthor = bookRepository.save(book)

        then: 'Weird stuff happens'
        bookWithAuthor.authors.every { it.id && !it.name }
    }
}
