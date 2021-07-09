package de.tuberlin.tkn.lit.storage;

import de.tuberlin.tkn.lit.model.activitypub.activities.Delete;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDeleteRepository extends CrudRepository <Delete, Long>{
}
