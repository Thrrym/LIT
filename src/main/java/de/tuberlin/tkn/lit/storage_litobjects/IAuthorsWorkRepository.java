package de.tuberlin.tkn.lit.storage_litobjects;

import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubCollection;
import de.tuberlin.tkn.lit.model.lit.AuthorsWork;
import org.springframework.data.repository.CrudRepository;

public interface IAuthorsWorkRepository extends CrudRepository<AuthorsWork, Long> {
}
