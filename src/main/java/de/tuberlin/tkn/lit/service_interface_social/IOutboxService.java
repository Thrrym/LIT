package de.tuberlin.tkn.lit.service_interface_social;

import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.activitypub.social.Outbox;
import de.tuberlin.tkn.lit.repos_social.IOutboxRepository;

import java.util.List;

public interface IOutboxService {

    List<Outbox> findAll();

    IOutboxRepository getRepository();
}
