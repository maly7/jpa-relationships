package com.github.maly7.data.jpa;

import com.github.maly7.domain.Label;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface LabelRepository extends CrudRepository<Label, Long> {

    @Query(value = "SELECT label_id FROM book_label",
            nativeQuery = true)
    List<BigInteger> findAllBookLabelLabelIds();
}
