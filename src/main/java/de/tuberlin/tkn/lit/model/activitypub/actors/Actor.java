package de.tuberlin.tkn.lit.model.activitypub.actors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.tuberlin.tkn.lit.constants.UriConstants;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;

public abstract class Actor extends ActivityPubObject {

    private String inbox;
    private String outbox;
    private String following;
    private String followers;
    private String liked;
    @JsonIgnore
    private String password;

    public Actor() {
    }

    public Actor(String inbox, String outbox, String following, String followers, String liked) {
        this.inbox = inbox;
        this.outbox = outbox;
        this.following = following;
        this.followers = followers;
        this.liked = liked;
    }

    public Actor(Actor actor) {
        this.inbox = actor.inbox;
        this.outbox = actor.outbox;
        this.following = actor.following;
        this.followers = actor.followers;
        this.liked = actor.liked;
    }

    public Actor(String actorName) {
        setName(actorName);
        inbox = UriConstants.HOST + actorName + UriConstants.INBOX;
        outbox = UriConstants.HOST + actorName + UriConstants.OUTBOX;
        following = UriConstants.HOST + actorName + UriConstants.FOLLOWING;
        followers = UriConstants.HOST + actorName + UriConstants.FOLLOWERS;
        liked = UriConstants.HOST + actorName + UriConstants.LIKED;
    }

    public Actor(String actorName,String password) {
        setName(actorName);
        inbox = UriConstants.HOST + actorName + UriConstants.INBOX;
        outbox = UriConstants.HOST + actorName + UriConstants.OUTBOX;
        following = UriConstants.HOST + actorName + UriConstants.FOLLOWING;
        followers = UriConstants.HOST + actorName + UriConstants.FOLLOWERS;
        liked = UriConstants.HOST + actorName + UriConstants.LIKED;
        this.password = password;
    }

    public String getInbox() {
        return inbox;
    }

    public void setInbox(String inbox) {
        this.inbox = inbox;
    }

    public String getOutbox() {
        return outbox;
    }

    public void setOutbox(String outbox) {
        this.outbox = outbox;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
