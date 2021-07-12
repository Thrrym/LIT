package de.tuberlin.tkn.lit.service_impl_social;

import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.activitypub.social.Outbox;
import de.tuberlin.tkn.lit.service_interface_social.IOutboxService;
import de.tuberlin.tkn.lit.repos_social.IOutboxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XOutboxService implements IOutboxService {

    @Override
    public IOutboxRepository getRepository() {
        return repository;
    }

    @Autowired
    private IOutboxRepository repository;

    @Override
    public List<Outbox> findAll() {

        return (List<Outbox>) repository.findAll();
    }
}
