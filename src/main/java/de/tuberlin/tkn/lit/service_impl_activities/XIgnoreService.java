package de.tuberlin.tkn.lit.service_impl_activities;

import de.tuberlin.tkn.lit.model.activitypub.activities.Ignore;
import de.tuberlin.tkn.lit.service_interface_activities.IIgnoreService;
import de.tuberlin.tkn.lit.repos_activities.IIgnoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XIgnoreService implements IIgnoreService {

    @Override
    public IIgnoreRepository getRepository() {
        return repository;
    }

    @Autowired
    private IIgnoreRepository repository;

    @Override
    public List<Ignore> findAll() {

        return (List<Ignore>) repository.findAll();
    }

}
