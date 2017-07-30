package com.github.maly7.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookAuthorRepository extends CrudRepository<BookAuthor, Long> {
}