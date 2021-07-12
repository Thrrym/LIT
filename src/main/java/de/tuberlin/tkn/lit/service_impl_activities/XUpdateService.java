package de.tuberlin.tkn.lit.service_impl_activities;

import de.tuberlin.tkn.lit.model.activitypub.activities.Update;
import de.tuberlin.tkn.lit.service_interface_activities.IUpdateService;
import de.tuberlin.tkn.lit.storage_activities.IUpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XUpdateService implements IUpdateService {

    @Override
    public IUpdateRepository getRepository() {
        return repository;
    }

    @Autowired
    private IUpdateRepository repository;

    @Override
    public List<Update> findAll() {

        return (List<Update>) repository.findAll();
    }
}


