package de.tuberlin.tkn.lit.repos_litobjects;

import de.tuberlin.tkn.lit.model.lit.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthorRepository extends CrudRepository<Author, Long> {
}
