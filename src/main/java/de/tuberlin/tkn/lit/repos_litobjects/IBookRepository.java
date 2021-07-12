package de.tuberlin.tkn.lit.repos_litobjects;

import de.tuberlin.tkn.lit.model.lit.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookRepository extends CrudRepository<Book, Long> {
}
