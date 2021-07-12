package de.tuberlin.tkn.lit.storage_social;

import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.model.activitypub.social.Following;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFollowingRepository extends CrudRepository<Following, Person> {
}