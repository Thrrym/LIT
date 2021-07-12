package de.tuberlin.tkn.lit.service_impl_activities;

import de.tuberlin.tkn.lit.model.activitypub.activities.Accept;
import de.tuberlin.tkn.lit.service_interface_activities.IAcceptService;
import de.tuberlin.tkn.lit.repos_activities.IAcceptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XAcceptService implements IAcceptService {

    @Override
    public IAcceptRepository getRepository() {return repository;}

    @Autowired
    private IAcceptRepository repository;

    @Override
    public List<Accept> findAll() {

        return (List<Accept>) repository.findAll();
    }
}
