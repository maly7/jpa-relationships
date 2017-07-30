package com.github.maly7.domain

import com.github.maly7.support.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class TransactionalBookCreationSpec extends IntegrationSpec {
    @Autowired
    BookRepository bookRepository

    @Autowired
    BookAuthorRepository bookAuthorRepository

    void 'A book with an author should be persisted'() {
        when: 'Persisting a book with an author'
        Book bookWithAuthor = bookRepository.save(new Book(title: 'A Storm of Swords', authors: [new BookAuthor(name: 'Author Person')]))

        then: 'The book with the author has been persisted'
        bookWithAuthor.id
        bookWithAuthor.title
        bookWithAuthor.authors.size() > 0
        bookWithAuthor.authors.every { it.id && it.name }
    }
}
