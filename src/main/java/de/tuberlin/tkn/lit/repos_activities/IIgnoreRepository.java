package de.tuberlin.tkn.lit.repos_activities;

import de.tuberlin.tkn.lit.model.activitypub.activities.Ignore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IIgnoreRepository extends CrudRepository<Ignore, Long> {
}
