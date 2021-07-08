package de.tuberlin.tkn.lit.storage;

import de.tuberlin.tkn.lit.model.lit.Journal;
import org.springframework.data.repository.CrudRepository;

public interface IJournalRepository extends CrudRepository<Journal, Long> {
}
