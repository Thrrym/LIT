package de.tuberlin.tkn.lit.storage_litobjects;

import de.tuberlin.tkn.lit.model.lit.Paper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaperRepository extends CrudRepository<Paper, Long> {
}
