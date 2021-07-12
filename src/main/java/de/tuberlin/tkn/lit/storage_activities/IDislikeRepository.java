package de.tuberlin.tkn.lit.storage_activities;

import de.tuberlin.tkn.lit.model.activitypub.activities.Dislike;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDislikeRepository extends CrudRepository<Dislike, Long> {
}
