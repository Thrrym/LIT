package de.tuberlin.tkn.lit.storage;

import de.tuberlin.tkn.lit.constants.ILitObjectConstants;
import de.tuberlin.tkn.lit.constants.UriConstants;
import de.tuberlin.tkn.lit.model.activitypub.activities.Activity;
import de.tuberlin.tkn.lit.model.activitypub.actors.Actor;
import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubCollection;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.activitypub.core.OrderedCollection;
import de.tuberlin.tkn.lit.model.lit.*;
import de.tuberlin.tkn.lit.service.*;
import de.tuberlin.tkn.lit.util.UriUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class Storage implements IStorage {

    @Autowired
    IPersonService personService;

    //# LitObjects
    @Autowired
    IAuthorService authorService;
    @Autowired
    IBibTeXArticleService bibTeXArticleService;
    @Autowired
    IBookService bookService;
    @Autowired
    IJournalService journalService;
    @Autowired
    IPaperService paperService;
    @Autowired
    ILikedService likedService;
    @Autowired
    IFollowingService followingService;
    @Autowired
    IFollowedService followedService;
    @Autowired
    IInboxService iInboxService;
    @Autowired
    IOutboxService iOutboxService;
    @Autowired
    IActivityPubCollectionService activityPubCollectionService;

    private final Map<String, OrderedCollection> outboxes = new HashMap<>();
    private final Map<String, OrderedCollection> inboxes = new HashMap<>();
    private final Map<String, OrderedCollection> followingCollections = new HashMap<>();
    private final Map<String, OrderedCollection> followersCollections = new HashMap<>();
    private final Map<String, Set<String>> relevantObjects = new HashMap<>();
    private final Map<String, Set<String>> liked = new HashMap<>();
    private final Map<String, Actor> actors = new HashMap<>();
    private final Map<UUID, Activity> activities = new HashMap<>();
    private final Map<String, ActivityPubObject> objects = new HashMap<>();
    @Value("${server.port}")
    private int serverPort;


    //# TODO ACTOR

    @Override
    public Actor createActor(Actor actor) {

        IPersonRepository personRepository = personService.getRepository();
        List<Person> persons = (List<Person>) personRepository.findAll();
        for(Person p : persons) {
            if (p.getName().equals(actor.getName())) { return null; }
        }

        actor.setId(UriUtilities.generateId(new String[]{actor.getName()}, serverPort, false));
        actor.setInbox(UriUtilities.generateId(new String[]{actor.getName(), UriConstants.INBOX}, serverPort, false));
        actor.setOutbox(UriUtilities.generateId(new String[]{actor.getName(), UriConstants.OUTBOX}, serverPort, false));
        actor.setFollowers(UriUtilities.generateId(new String[]{actor.getName(), UriConstants.FOLLOWERS}, serverPort, false));
        actor.setFollowing(UriUtilities.generateId(new String[]{actor.getName(), UriConstants.FOLLOWING}, serverPort, false));
        actor.setLiked(UriUtilities.generateId(new String[]{actor.getName(), UriConstants.LIKED}, serverPort, false));
        actors.put(actor.getName(), actor);
        outboxes.put(actor.getName(), new OrderedCollection(new ArrayList<>()));
        inboxes.put(actor.getName(), new OrderedCollection(new ArrayList<>()));
        relevantObjects.put(actor.getName(), new HashSet<>());
        followingCollections.put(actor.getName(), new OrderedCollection(new ArrayList<>()));
        followersCollections.put(actor.getName(), new OrderedCollection(new ArrayList<>()));
        liked.put(actor.getName(), new HashSet<>());

        personRepository.save((Person)actor);

        return actors.get(actor.getName());
    }

    @Override
    public Actor getActor(String actorName) {
        IPersonRepository personRepository = personService.getRepository();
        List<Person> persons = (List<Person>) personRepository.findAll();
        for(Person p :  persons) {
            if(p.getName().equals(actorName)) return p;
        }
        throw new NullPointerException();
    }

    //# TODO
    @Override
    public boolean removeActor(Actor actor) {
        IPersonRepository personRepository = personService.getRepository();
        List<Person> persons = (List<Person>) personRepository.findAll();
        for(Person p :  persons) {
            if(p.getName().equals(actor.getName())) {
                //# TODO remove actor from inboxes & outboxes
                return true;
            }
        }
        return false;
    }

    @Override
    public ActivityPubCollection getActors() {
        IPersonRepository personRepository = personService.getRepository();
        List<Person> persons = (List<Person>) personRepository.findAll();
        return new ActivityPubCollection(persons.stream().map(LinkOrObject::new).collect(Collectors.toList()));
    }

    @Override
    public boolean existsByUsername(String actorName) {
        IPersonRepository personRepository = personService.getRepository();
        List<Person> persons = (List<Person>) personRepository.findAll();
        for(Person p :  persons) {
            if(p.getName().equals(actorName)) { return true; }
        }
        return false;
    }

    @Override
    public ActivityPubObject createObject(String actorName, String objectType, ActivityPubObject object) {
        UUID uuid = UUID.randomUUID();
        String id = UriUtilities.generateId(new String[]{actorName, objectType}, serverPort, uuid);
        object.setId(id);
        String actorId = getActor(actorName).getId();
        object.setGenerator(new LinkOrObject(actorId));

        if(objectType.equals(ILitObjectConstants.AUTHOR)
                && object instanceof Author) {
            IAuthorRepository authorRepo = authorService.getRepository();
            authorRepo.save((Author) object);
            return object;
        }
        if(objectType.equals(ILitObjectConstants.BIBTEXARTICLE)
                && object instanceof BibTeXArticle) {
            IBibTeXArticleRepository bibTeXArticleRepository = bibTeXArticleService.getRepository();
            bibTeXArticleRepository.save((BibTeXArticle) object);
            return object;
        }
        if(objectType.equals(ILitObjectConstants.BOOK)
                && object instanceof Book) {
            IBookRepository bookRepository = bookService.getRepository();
            bookRepository.save((Book) object);
            return object;
        }
        if(objectType.equals(ILitObjectConstants.JOURNAL)
                && object instanceof Journal) {
            IJournalRepository journalRepository = journalService.getRepository();
            journalRepository.save((Journal) object);
            return object;
        }
        if(objectType.equals(ILitObjectConstants.PAPER)
                && object instanceof Paper) {
            IPaperRepository paperRepository = paperService.getRepository();
            paperRepository.save((Paper) object);
            return object;
        }
        if(objectType.equals(ILitObjectConstants.ACTIVITYPUBCOLLECTION)
                && object instanceof ActivityPubCollection) {
            IActivityPubCollectionRepository activityPubCollectionRepository = activityPubCollectionService.getRepository();
            activityPubCollectionRepository.save((ActivityPubCollection) object);
            return object;
        }
       return null;
    }

    @Override
    public ActivityPubObject getObject(String id) {

        String[] parts = id.split("/");
        String objectType = parts[2];
        if(objectType.equals(ILitObjectConstants.AUTHOR)) {
            IAuthorRepository authorRepo = authorService.getRepository();
            List<Author> authors = (List<Author>) authorRepo.findAll();
            for(Author author : authors) {
                if(author.getId().equals(id)) return author;
            }
            return null;
        }
        if(objectType.equals(ILitObjectConstants.BIBTEXARTICLE)) {
            IBibTeXArticleRepository bibTeXArticleRepository = bibTeXArticleService.getRepository();
            List<BibTeXArticle> bibTeXArticles = (List<BibTeXArticle>) bibTeXArticleRepository.findAll();
            for(BibTeXArticle bibTeXArticle : bibTeXArticles) {
                if(bibTeXArticle.getId().equals(id)) return bibTeXArticle;
            }
            return null;
        }
        if(objectType.equals(ILitObjectConstants.BOOK)) {
            IBookRepository bookRepository = bookService.getRepository();
            List<Book> books = (List<Book>) bookRepository.findAll();
            for(Book bibTeXArticle : books) {
                if(bibTeXArticle.getId().equals(id)) return bibTeXArticle;
            }
            return null;
        }
        if(objectType.equals(ILitObjectConstants.JOURNAL)) {
            IJournalRepository journalRepository = journalService.getRepository();
            List<Journal> journals = (List<Journal>) journalRepository.findAll();
            for(Journal journal : journals) {
                if(journal.getId().equals(id)) return journal;
            }
            return null;
        }
        if(objectType.equals(ILitObjectConstants.PAPER)) {
            IPaperRepository paperRepository = paperService.getRepository();
            List<Paper> papers = (List<Paper>) paperRepository.findAll();
            for(Paper paper : papers) {
                if(paper.getId().equals(id)) return paper;
            }
            return null;
        }
        if(objectType.equals(ILitObjectConstants.ACTIVITYPUBCOLLECTION)) {
            IActivityPubCollectionRepository activityPubCollectionRepository = activityPubCollectionService.getRepository();
            List<ActivityPubCollection> activityPubCollections = (List<ActivityPubCollection>) activityPubCollectionRepository.findAll();
            for(ActivityPubCollection activityPubCollection : activityPubCollections) {
                if(activityPubCollection.getId().equals(id)) return activityPubCollection;
            }
            return null;
        }
        return null;
    }

    @Override
    public ActivityPubObject updateObject(String actorName, ActivityPubObject object) {
        String id = object.getId();
        String objectType = object.getType();

        if(objectType.equals(ILitObjectConstants.AUTHOR)) {
            IAuthorRepository authorRepo = authorService.getRepository();
            List<Author> authors = (List<Author>) authorRepo.findAll();
            for(Author author : authors) {
                if(author.getId().equals(id)) {
                    Author newAuthor = (Author) object;
                    authorRepo.delete(author);
                    authorRepo.save(newAuthor);
                    return newAuthor;
                }
            }
            return null;
        }
        if(objectType.equals(ILitObjectConstants.BIBTEXARTICLE)) {
            IBibTeXArticleRepository bibTeXArticleRepository = bibTeXArticleService.getRepository();
            List<BibTeXArticle> bibTeXArticles = (List<BibTeXArticle>) bibTeXArticleRepository.findAll();
            for(BibTeXArticle bibTeXArticle : bibTeXArticles) {
                if(bibTeXArticle.getId().equals(id)) {
                    BibTeXArticle newBibTeXArticle = (BibTeXArticle) object;
                    bibTeXArticleRepository.delete(bibTeXArticle);
                    bibTeXArticleRepository.save(newBibTeXArticle);
                    return newBibTeXArticle;
                }
            }
            return null;
        }
        if(objectType.equals(ILitObjectConstants.BOOK)) {
            IBookRepository bookRepository = bookService.getRepository();
            List<Book> books = (List<Book>) bookRepository.findAll();
            for(Book book : books) {
                if(book.getId().equals(id)) {
                    Book newBook = (Book) object;
                    bookRepository.delete(book);
                    bookRepository.save(newBook);
                    return newBook;
                }
            }
            return null;
        }
        if(objectType.equals(ILitObjectConstants.JOURNAL)) {
            IJournalRepository journalRepository = journalService.getRepository();
            List<Journal> journals = (List<Journal>) journalRepository.findAll();
            for(Journal journal : journals) {
                if(journal.getId().equals(id)) {
                    Journal newJournal = (Journal) object;
                    journalRepository.delete(journal);
                    journalRepository.save(newJournal);
                    return newJournal;
                }
            }
            return null;
        }
        if(objectType.equals(ILitObjectConstants.PAPER)) {
            IPaperRepository paperRepository = paperService.getRepository();
            List<Paper> papers = (List<Paper>) paperRepository.findAll();
            for(Paper paper : papers) {
                if(paper.getId().equals(id)) {
                    Paper newPaper = (Paper) object;
                    paperRepository.delete(paper);
                    paperRepository.save(newPaper);
                    return newPaper;
                }
            }
            return null;
        }
        if(objectType.equals(ILitObjectConstants.ACTIVITYPUBCOLLECTION)) {
            IActivityPubCollectionRepository activityPubCollectionRepository = activityPubCollectionService.getRepository();
            List<ActivityPubCollection> activityPubCollections = (List<ActivityPubCollection>) activityPubCollectionRepository.findAll();
            for(ActivityPubCollection activityPubCollection : activityPubCollections) {
                if(activityPubCollection.getId().equals(id)) {
                    ActivityPubCollection newActivityPubCollection = (ActivityPubCollection) object;
                    activityPubCollectionRepository.delete(activityPubCollection);
                    activityPubCollectionRepository.save(newActivityPubCollection);
                    return newActivityPubCollection;
                }
            }
            return null;
        }
        return null;
    }

    @Override
    public ActivityPubCollection getObjects() {
        IActivityPubCollectionRepository activityPubCollectionRepository = activityPubCollectionService.getRepository();
        List<ActivityPubCollection> activityPubCollections = (List<ActivityPubCollection>) activityPubCollectionRepository.findAll();
        return new ActivityPubCollection(activityPubCollections.stream().map(LinkOrObject::new).collect(Collectors.toList()));
    }

    //# TODO
    @Override
    public ActivityPubObject createObject(String id, ActivityPubObject object) {
        //objects.put(id, object);
        //return objects.get(id);
        String objectType = object.getType();
        if(objectType.equals(ILitObjectConstants.AUTHOR)) {
            IAuthorRepository authorRepo = authorService.getRepository();
            List<Author> authors = (List<Author>) authorRepo.findAll();
            for(Author author : authors) {
                if(author.getId().equals(id)) {
                    Author newAuthor = (Author) object;
                    authorRepo.delete(author);
                    authorRepo.save(newAuthor);
                    return newAuthor;
                }
            }
            return null;
        }
        if(objectType.equals(ILitObjectConstants.BIBTEXARTICLE)) {
            IBibTeXArticleRepository bibTeXArticleRepository = bibTeXArticleService.getRepository();
            List<BibTeXArticle> bibTeXArticles = (List<BibTeXArticle>) bibTeXArticleRepository.findAll();
            for(BibTeXArticle bibTeXArticle : bibTeXArticles) {
                if(bibTeXArticle.getId().equals(id)) {
                    BibTeXArticle newBibTeXArticle = (BibTeXArticle) object;
                    bibTeXArticleRepository.delete(bibTeXArticle);
                    bibTeXArticleRepository.save(newBibTeXArticle);
                    return newBibTeXArticle;
                }
            }
            return null;
        }
        if(objectType.equals(ILitObjectConstants.BOOK)) {
            IBookRepository bookRepository = bookService.getRepository();
            List<Book> books = (List<Book>) bookRepository.findAll();
            for(Book book : books) {
                if(book.getId().equals(id)) {
                    Book newBook = (Book) object;
                    bookRepository.delete(book);
                    bookRepository.save(newBook);
                    return newBook;
                }
            }
            return null;
        }
        if(objectType.equals(ILitObjectConstants.JOURNAL)) {
            IJournalRepository journalRepository = journalService.getRepository();
            List<Journal> journals = (List<Journal>) journalRepository.findAll();
            for(Journal journal : journals) {
                if(journal.getId().equals(id)) {
                    Journal newJournal = (Journal) object;
                    journalRepository.delete(journal);
                    journalRepository.save(newJournal);
                    return newJournal;
                }
            }
            return null;
        }
        if(objectType.equals(ILitObjectConstants.PAPER)) {
            IPaperRepository paperRepository = paperService.getRepository();
            List<Paper> papers = (List<Paper>) paperRepository.findAll();
            for(Paper paper : papers) {
                if(paper.getId().equals(id)) {
                    Paper newPaper = (Paper) object;
                    paperRepository.delete(paper);
                    paperRepository.save(newPaper);
                    return newPaper;
                }
            }
            return null;
        }
        if(objectType.equals(ILitObjectConstants.ACTIVITYPUBCOLLECTION)) {
            IActivityPubCollectionRepository activityPubCollectionRepository = activityPubCollectionService.getRepository();
            List<ActivityPubCollection> activityPubCollections = (List<ActivityPubCollection>) activityPubCollectionRepository.findAll();
            for(ActivityPubCollection activityPubCollection : activityPubCollections) {
                if(activityPubCollection.getId().equals(id)) {
                    ActivityPubCollection newActivityPubCollection = (ActivityPubCollection) object;
                    activityPubCollectionRepository.delete(activityPubCollection);
                    activityPubCollectionRepository.save(newActivityPubCollection);
                    return newActivityPubCollection;
                }
            }
            return null;
        }
        return null;

    }

    //# TODO
    @Override
    public OrderedCollection getObjectsCreatedByActor(String actorName) {
        String actorId = getActor(actorName).getId();
        List<LinkOrObject> results = objects.values().stream().filter(value -> value.getGenerator().getLink().equals(actorId)).map(LinkOrObject::new).collect(Collectors.toList());
        return new OrderedCollection(results);
    }

    //# TODO
    @Override
    public OrderedCollection getRelevantObjects(String actorName) {
        return new OrderedCollection(relevantObjects.get(actorName).stream().map((id) -> new LinkOrObject(objects.get(id))).collect(Collectors.toList()));
    }

    //# TODO
    public void addToRelevantObjects(String actorName, LinkOrObject toAdd) {
        relevantObjects.get(actorName).add(toAdd.getId());
    }

    //# TODO ACTIVITY
    @Override
    public Activity getActivity(UUID id) {
        return activities.get(id);
    }

    @Override
    public Activity createActivity(String actorName, Activity activity) {
        UUID uuid = UUID.randomUUID();
        String id = UriUtilities.generateId(new String[]{actorName}, serverPort, uuid);
        activity.setId(id);
        activities.put(uuid, activity);

        return activities.get(uuid);
    }

    //# TODO LIKED
    @Override
    public OrderedCollection getLikedCollection(String actorName) {
        return new OrderedCollection(liked.get(actorName).stream().map((id) -> new LinkOrObject(objects.get(id))).collect(Collectors.toList()));
    }

    @Override
    public void addToLiked(String actorName, LinkOrObject toAdd) {
        liked.get(actorName).add(toAdd.getId());
    }

    //# TODO FOLLOWING
    @Override
    public OrderedCollection getFollowingCollection(String actorName) {
        return followingCollections.get(actorName);
    }

    @Override
    public void addToFollowing(String actorName, LinkOrObject toAdd) {
        followingCollections.get(actorName).getOrderedItems().add(toAdd);
    }

    //# TODO FOLLOWERS
    @Override
    public OrderedCollection getFollowersCollection(String actorName) {
        return followersCollections.get(actorName);
    }

    @Override
    public void addToFollowers(String actorName, LinkOrObject toAdd) {
        followersCollections.get(actorName).getOrderedItems().add(toAdd);
    }

    //# TODO INBOX
    @Override
    public OrderedCollection getInbox(String actorName) {
        OrderedCollection orderedCollection = inboxes.get(actorName);
        if (orderedCollection == null) {
            throw new NullPointerException();
        }
        return orderedCollection;
    }

    @Override
    public void addToInbox(String actorName, LinkOrObject toAdd) {
        inboxes.get(actorName).getOrderedItems().add(toAdd);
    }

    //# TODO OUTBOX
    @Override
    public OrderedCollection getOutbox(String actorName) {
        return outboxes.get(actorName);
    }

    @Override
    public void addToOutbox(String actorName, LinkOrObject toAdd) {
        outboxes.get(actorName).getOrderedItems().add(toAdd);
    }


}