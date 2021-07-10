package de.tuberlin.tkn.lit.storage;

import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFollowedRepository extends CrudRepository<LinkOrObject, Long> {
}