package de.tuberlin.tkn.lit.storage;

import de.tuberlin.tkn.lit.model.activitypub.activities.Follow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFollowRepository extends CrudRepository<Follow, Long> {
}
