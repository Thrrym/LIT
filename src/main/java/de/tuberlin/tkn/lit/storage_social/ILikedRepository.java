package de.tuberlin.tkn.lit.storage_social;

import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.model.activitypub.social.Liked;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILikedRepository extends CrudRepository<Liked, Person> {
}