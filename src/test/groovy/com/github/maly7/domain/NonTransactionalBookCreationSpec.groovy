package com.github.maly7.domain

import com.github.maly7.support.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.Rollback

@Rollback
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
        bookWithAuthor.authors.every { it.id && it.name }
    }

    void 'Not setting the book on the BookAuthor should persist the BookAuthor'() {
        given: 'A persisted book'
        Book book = bookRepository.save(new Book(title: 'A Storm of Swords'))

        when: 'Adding an author without setting both sides of the relationship'
        book.authors = Collections.singleton(new BookAuthor(name: 'Author Person'))
        Book bookWithAuthor = bookRepository.save(book)

        then: 'The book with teh author should be persisted'
        bookWithAuthor.authors.every { it.id && it.name }
    }

    void 'A book with an author should be persisted at the same time'() {
        when: 'Persisting a book with an author'
        Book bookWithAuthor = bookRepository.save(new Book(title: 'A Storm of Swords', authors: [new BookAuthor(name: 'Author Person')]))

        then: 'The book with the author has been persisted'
        bookWithAuthor.id
        bookWithAuthor.title
        bookWithAuthor.authors.size() > 0
        bookWithAuthor.authors.every { it.id && it.name }
    }

    void 'A persisted book should have a persisted author set'() {
        given: 'A persited book and author'
        Book book = bookRepository.save(new Book(title: 'A Dance with Dragons'))
        BookAuthor bookAuthor = bookAuthorRepository.save(new BookAuthor(name: 'Save me'))

        when: 'Adding the author to the book'
        book.authors = new HashSet<>([bookAuthor])
        Book bookWithAuthor = bookRepository.save(book)

        then: 'The book to author relationship is stored'
        bookWithAuthor.authors
        bookWithAuthor.authors.every { it.id && it.name }

        when: 'Retrieving the book'
        Book retrievedBook = bookRepository.findOne(bookWithAuthor.id)
        BookAuthor author = bookAuthorRepository.findOne(bookWithAuthor.authors.first().id)

        then: 'The info is still there'
        retrievedBook.title
        retrievedBook.authors
        author.name
        author.id
        retrievedBook.authors.every { it.id && it.name }
    }
}
