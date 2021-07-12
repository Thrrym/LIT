package de.tuberlin.tkn.lit.storage_social;

import de.tuberlin.tkn.lit.model.activitypub.social.RelevantObject;
import org.springframework.data.repository.CrudRepository;

public interface IRelevantObjectRepository extends CrudRepository<RelevantObject, Long> {
}
