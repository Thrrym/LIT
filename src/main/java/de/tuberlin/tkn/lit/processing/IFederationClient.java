package de.tuberlin.tkn.lit.processing;

import java.util.concurrent.Future;

import de.tuberlin.tkn.lit.model.activitypub.activities.Activity;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.activitypub.core.OrderedCollection;

public interface IFederationClient{
	Future<Boolean> send(Activity activity, LinkOrObject sendTo);
	Future<OrderedCollection> getOutbox(LinkOrObject getFrom);
	void handleJoinFederation(String knownMember);
}