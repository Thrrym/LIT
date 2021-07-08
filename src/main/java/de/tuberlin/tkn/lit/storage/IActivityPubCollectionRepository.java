package de.tuberlin.tkn.lit.storage;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubCollection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IActivityPubCollectionRepository extends CrudRepository<ActivityPubCollection, Long> {
}