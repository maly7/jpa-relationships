package com.github.maly7.domain

import com.github.maly7.support.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.Rollback

import javax.transaction.Transactional

@Transactional
@Rollback
class BookLabelSpec extends IntegrationSpec {

    @Autowired
    BookRepository bookRepository

    @Autowired
    LabelRepository labelRepository

    void 'we should be able to persist book label relationships'() {
        given: 'Some books and labels'
        Book book = bookRepository.save(new Book(title: 'The Way of Kings'))
        Label fantasy = labelRepository.save(new Label(name: 'fantasy'))
        Label epic = labelRepository.save(new Label(name: 'epic'))

        when: 'Adding labels to the book'
        book.labels = [fantasy, epic]
        bookRepository.save(book)
        Book labeledBook = bookRepository.findOne(book.id)

        then: 'The persisted book has labels'
        labeledBook.labels.containsAll([fantasy, epic])
    }

    void 'we should be able to remove labels from a book'() {
        given: 'A book with some labels'
        Book book = bookRepository.save(new Book(title: 'The Way of Kings'))
        Label fantasy = labelRepository.save(new Label(name: 'fantasy'))
        Label epic = labelRepository.save(new Label(name: 'epic'))

        book.labels = [fantasy, epic]
        bookRepository.save(book)
        Book labeledBook = bookRepository.findOne(book.id)

        when: 'Removing a label'
        labeledBook.labels.remove(fantasy)
        bookRepository.save(labeledBook)
        Book bookWithOnlyOneLabel = bookRepository.findOne(book.id)

        then: 'The label has been removed'
        bookWithOnlyOneLabel.labels.size() == 1
        !bookWithOnlyOneLabel.labels.contains(fantasy)

        when: 'Retrieving the label ids in the join table'
        List<BigInteger> labelIds = labelRepository.findAllBookLabelLabelIds()

        then: 'The removed label is not found'
        labelIds.contains(BigInteger.valueOf(epic.id))
        !labelIds.contains(BigInteger.valueOf(fantasy.id))
    }
}
