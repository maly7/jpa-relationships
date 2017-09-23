package com.github.maly7.data.jpa;

import com.github.maly7.domain.BookAuthor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface BookAuthorRepository extends CrudRepository<BookAuthor, Long> {
}
