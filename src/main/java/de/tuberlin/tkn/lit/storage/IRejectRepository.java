package de.tuberlin.tkn.lit.storage;

import de.tuberlin.tkn.lit.model.activitypub.activities.Reject;
import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRejectRepository extends CrudRepository<Reject, Long> {
}
