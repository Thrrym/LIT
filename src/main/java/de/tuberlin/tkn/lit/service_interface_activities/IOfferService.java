package de.tuberlin.tkn.lit.service_interface_activities;

import de.tuberlin.tkn.lit.model.activitypub.activities.Offer;
import de.tuberlin.tkn.lit.storage_activities.IOfferRepository;

import java.util.List;

public interface IOfferService {

    List<Offer> findAll();

    IOfferRepository getRepository();
}
