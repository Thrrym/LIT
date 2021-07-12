package de.tuberlin.tkn.lit.service_impl_activities;

import de.tuberlin.tkn.lit.model.activitypub.activities.Follow;
import de.tuberlin.tkn.lit.service_interface_activities.IFollowService;
import de.tuberlin.tkn.lit.storage_activities.IFollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XFollowService implements IFollowService {

    @Override
    public IFollowRepository getRepository() {
        return repository;
    }

    @Autowired
    private IFollowRepository repository;

    @Override
    public List<Follow> findAll() {

        return (List<Follow>) repository.findAll();
    }
}
