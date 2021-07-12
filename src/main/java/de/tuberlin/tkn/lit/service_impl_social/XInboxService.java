package de.tuberlin.tkn.lit.service_impl_social;

import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.activitypub.social.Inbox;
import de.tuberlin.tkn.lit.service_interface_social.IInboxService;
import de.tuberlin.tkn.lit.repos_social.IInboxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XInboxService implements IInboxService {

    @Override
    public IInboxRepository getRepository() {
        return repository;
    }

    @Autowired
    private IInboxRepository repository;

    @Override
    public List<Inbox> findAll() {

        return (List<Inbox>) repository.findAll();
    }
}
