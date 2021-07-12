package de.tuberlin.tkn.lit.service_interface_social;

import de.tuberlin.tkn.lit.model.activitypub.social.Inbox;
import de.tuberlin.tkn.lit.storage_social.IInboxRepository;

import java.util.List;

public interface IInboxService {

    List<Inbox> findAll();

    IInboxRepository getRepository();
}
