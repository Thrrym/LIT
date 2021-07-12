package de.tuberlin.tkn.lit.service_impl_activities;

import de.tuberlin.tkn.lit.model.activitypub.activities.Create;
import de.tuberlin.tkn.lit.service_interface_activities.ICreateService;
import de.tuberlin.tkn.lit.repos_activities.ICreateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XCreateService implements ICreateService {

    @Override
    public ICreateRepository getRepository() {
        return repository;
    }

    @Autowired
    private ICreateRepository repository;

    @Override
    public List<Create> findAll() {

        return (List<Create>) repository.findAll();
    }
}
