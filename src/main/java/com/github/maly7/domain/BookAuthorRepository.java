package com.github.maly7.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface BookAuthorRepository extends CrudRepository<BookAuthor, Long> {
}
