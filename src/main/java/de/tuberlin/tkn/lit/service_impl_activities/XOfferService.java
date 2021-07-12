package de.tuberlin.tkn.lit.service_impl_activities;

import de.tuberlin.tkn.lit.model.activitypub.activities.Offer;
import de.tuberlin.tkn.lit.service_interface_activities.IOfferService;
import de.tuberlin.tkn.lit.storage_activities.IOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XOfferService implements IOfferService {

    @Override
    public IOfferRepository getRepository() {
        return repository;
    }

    @Autowired
    private IOfferRepository repository;

    @Override
    public List<Offer> findAll() {

        return (List<Offer>) repository.findAll();
    }

}
