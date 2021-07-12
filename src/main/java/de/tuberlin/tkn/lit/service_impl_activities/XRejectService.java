package de.tuberlin.tkn.lit.service_impl_activities;

import de.tuberlin.tkn.lit.model.activitypub.activities.Reject;
import de.tuberlin.tkn.lit.service_interface_activities.IRejectService;
import de.tuberlin.tkn.lit.repos_activities.IRejectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XRejectService implements IRejectService {

    @Override
    public IRejectRepository getRepository() {
        return repository;
    }

    @Autowired
    private IRejectRepository repository;

    @Override
    public List<Reject> findAll() {

        return (List<Reject>) repository.findAll();
    }
}
