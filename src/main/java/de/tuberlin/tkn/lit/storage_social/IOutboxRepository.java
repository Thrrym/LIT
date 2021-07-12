package de.tuberlin.tkn.lit.storage_social;

import de.tuberlin.tkn.lit.model.activitypub.social.Outbox;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOutboxRepository extends CrudRepository<Outbox, Long> {
}