package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.activities.Accept;
import de.tuberlin.tkn.lit.model.activitypub.activities.Create;
import de.tuberlin.tkn.lit.storage.IAcceptRepository;
import de.tuberlin.tkn.lit.storage.ICreateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XAcceptService implements IAcceptService{

    @Override
    public IAcceptRepository getRepository() {return repository;}

    @Autowired
    private IAcceptRepository repository;

    @Override
    public List<Accept> findAll() {

        return (List<Accept>) repository.findAll();
    }
}
