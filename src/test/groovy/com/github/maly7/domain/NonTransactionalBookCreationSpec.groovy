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
        bookWithAuthor.authors.every { it.id && it.name } // This fails because the author name info isn't saved
        /*
        Generated sql for this:
        Hibernate: insert into book (id, title) values (null, ?)
        Hibernate: select book0_.id as id1_0_0_, book0_.title as title2_0_0_ from book book0_ where book0_.id=?
        Hibernate: select authors0_.book_id as book_id2_1_0_, authors0_.author_id as author_i1_1_0_, authors0_.author_id as id1_2_1_, authors0_1_.name as name2_2_1_, authors0_.book_id as book_id2_1_1_ from book_author authors0_ inner join writer authors0_1_ on authors0_.author_id=authors0_1_.id where authors0_.book_id=?
        Hibernate: insert into writer (id, name) values (null, ?)
        Hibernate: insert into book_author (book_id, author_id) values (?, ?)
        */
    }

    void 'Not setting the book on the BookAuthor should persist the BookAuthor'() {
        given: 'A persisted book'
        Book book = bookRepository.save(new Book(title: 'A Storm of Swords'))

        when: 'Adding an author without setting both sides of the relationship'
        book.authors = Collections.singleton(new BookAuthor(name: 'Author Person'))
        Book bookWithAuthor = bookRepository.save(book)

        then: 'The book with teh author should be persisted'
        bookWithAuthor.authors.every { it.id && it.name }
        /*
        This generates the same sql as the previous test
        Hibernate: insert into book (id, title) values (null, ?)
        Hibernate: select book0_.id as id1_0_0_, book0_.title as title2_0_0_ from book book0_ where book0_.id=?
        Hibernate: select authors0_.book_id as book_id2_1_0_, authors0_.author_id as author_i1_1_0_, authors0_.author_id as id1_2_1_, authors0_1_.name as name2_2_1_, authors0_.book_id as book_id2_1_1_ from book_author authors0_ inner join writer authors0_1_ on authors0_.author_id=authors0_1_.id where authors0_.book_id=?
        Hibernate: insert into writer (id, name) values (null, ?)
        Hibernate: insert into book_author (book_id, author_id) values (?, ?)
         */
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
