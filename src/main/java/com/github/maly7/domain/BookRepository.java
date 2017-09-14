package com.github.maly7.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Long> {

    Set<Book> findAllByLabelsContaining(Label label);
}
