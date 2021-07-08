package de.tuberlin.tkn.lit.storage;

import de.tuberlin.tkn.lit.model.activitypub.activities.Like;
import de.tuberlin.tkn.lit.model.lit.Journal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILikeRepository extends CrudRepository<Like, Long> {
}
