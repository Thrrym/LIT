package de.tuberlin.tkn.lit.storage;

import de.tuberlin.tkn.lit.constants.IActivityConstants;
import de.tuberlin.tkn.lit.constants.ILitObjectConstants;
import de.tuberlin.tkn.lit.constants.UriConstants;
import de.tuberlin.tkn.lit.model.activitypub.activities.*;
import de.tuberlin.tkn.lit.model.activitypub.actors.Actor;
import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubCollection;
import de.tuberlin.tkn.lit.model.activitypub.core.ActivityPubObject;
import de.tuberlin.tkn.lit.model.activitypub.core.LinkOrObject;
import de.tuberlin.tkn.lit.model.activitypub.core.OrderedCollection;
import de.tuberlin.tkn.lit.model.activitypub.social.*;
import de.tuberlin.tkn.lit.model.lit.Author;
import de.tuberlin.tkn.lit.model.lit.*;
import de.tuberlin.tkn.lit.service_interface_activities.*;
import de.tuberlin.tkn.lit.service_interface_litobjects.*;
import de.tuberlin.tkn.lit.service_interface_social.*;
import de.tuberlin.tkn.lit.storage_activities.*;
import de.tuberlin.tkn.lit.storage_litobjects.*;
import de.tuberlin.tkn.lit.util.UriUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class Storage implements IStorage {

    @Autowired
    IPersonService personService;

    //# LITOBJECTS
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
    IActivityPubCollectionService activityPubCollectionService;

    //# SOCIAL
    @Autowired
    ILikedService likedService;
    @Autowired
    IFollowingService followingService;
    @Autowired
    IFollowedService followedService;
    @Autowired
    IInboxService inboxService;
    @Autowired
    IOutboxService outboxService;
    @Autowired
    IRelevantObjectService relevantObjectService;



    //# ACTIVITIES
    @Autowired
    private IAcceptService acceptService;
    @Autowired
    private ICreateService createService;
    @Autowired
    private IDeleteService deleteService;
    @Autowired
    private IFollowService followService;
    @Autowired
    private IIgnoreService ignoreService;
    @Autowired
    private ILikeService likeService;
    @Autowired
    private IRejectService rejectService;
    @Autowired
    private IUpdateService updateService;

    private final Map<String, OrderedCollection> outboxes = new HashMap<>();
    private final Map<String, OrderedCollection> inboxes = new HashMap<>();
    private final Map<String, OrderedCollection> followingCollections = new HashMap<>();
    private final Map<String, OrderedCollection> followersCollections = new HashMap<>();
    private final Map<String, Set<String>> relevantObjects = new HashMap<>();
    private final Map<String, Set<String>> liked = new HashMap<>();
    private final Map<String, Actor> actors = new HashMap<>();
    private final Map<String, Activity> activities = new HashMap<>();
    private final Map<String, ActivityPubObject> objects = new HashMap<>();
    @Value("${server.port}")
    private int serverPort;

    @Override
    public OrderedCollection getOffers(String actorName) {
        OrderedCollection orderedCollection = inboxes.get(actorName);
        if (orderedCollection == null) {
            throw new NullPointerException();
        }

        List<LinkOrObject> inboxItems = orderedCollection.getOrderedItems();

        return new OrderedCollection(inboxItems.stream().filter(item -> item.getLitObject().getType().equals("Offer")).collect(Collectors.toList()));
    }

    //# ACTOR
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
        Following newFollowing = new Following();
        newFollowing.setActorname(actor.getName());
        //TODO create social objects for person

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

    @Override
    public boolean removeActor(Actor actor) {
        IPersonRepository personRepository = personService.getRepository();
        List<Person> persons = (List<Person>) personRepository.findAll();
        for(Person p :  persons) {
            if(p.getName().equals(actor.getName())) {
                deleteActorInboxOutbox(p);
                return true;
            }
        }
        return false;
    }

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
    public ActivityPubCollection getAuthors() {
        return new ActivityPubCollection(objects.values().stream().filter(o -> o.getType().equals("Author")).map(LinkOrObject::new).collect(Collectors.toList()));
    }

    @Override
    public boolean authorExists(String orcId) {
        List<LinkOrObject> duplicates = getAuthors().getItems().stream().filter(a -> ((Author) a.getLitObject()).getOrcid().equals(orcId)).collect(Collectors.toList());
        return !duplicates.isEmpty();
    }

    //# OBJECT
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
            IAuthorRepository authorRepo = authorService.getRepository();
            List<Author> authors = (List<Author>) authorRepo.findAll();
            for(Author author : authors) {
                if(author.getId().equals(id)) return author;
            }
            IBibTeXArticleRepository bibTeXArticleRepository = bibTeXArticleService.getRepository();
            List<BibTeXArticle> bibTeXArticles = (List<BibTeXArticle>) bibTeXArticleRepository.findAll();
            for(BibTeXArticle bibTeXArticle : bibTeXArticles) {
                if(bibTeXArticle.getId().equals(id)) return bibTeXArticle;
            }
            IBookRepository bookRepository = bookService.getRepository();
            List<Book> books = (List<Book>) bookRepository.findAll();
            for(Book bibTeXArticle : books) {
                if(bibTeXArticle.getId().equals(id)) return bibTeXArticle;
            }
            IJournalRepository journalRepository = journalService.getRepository();
            List<Journal> journals = (List<Journal>) journalRepository.findAll();
            for(Journal journal : journals) {
                if(journal.getId().equals(id)) return journal;
            }
            IPaperRepository paperRepository = paperService.getRepository();
            List<Paper> papers = (List<Paper>) paperRepository.findAll();
            for(Paper paper : papers) {
                if(paper.getId().equals(id)) return paper;
            }
            IActivityPubCollectionRepository activityPubCollectionRepository = activityPubCollectionService.getRepository();
            List<ActivityPubCollection> activityPubCollections = (List<ActivityPubCollection>) activityPubCollectionRepository.findAll();
            for(ActivityPubCollection activityPubCollection : activityPubCollections) {
                if(activityPubCollection.getId().equals(id)) return activityPubCollection;
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

    //#
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

    @Override
    public OrderedCollection getObjectsCreatedByActor(String actorName) {
        String actorId = getActor(actorName).getId();
        List<LinkOrObject> createdByActor = new ArrayList<LinkOrObject>();
        List<Author> authors = (List<Author>) authorService.getRepository().findAll();
        for(Author author : authors) { if(author.getGenerator().getLitObject().getName().equals(actorName)) createdByActor.add(new LinkOrObject(author)); }
        List<Paper> papers = (List<Paper>) paperService.getRepository().findAll();
        for(Paper paper : papers) { if(paper.getGenerator().getLitObject().getName().equals(actorName)) createdByActor.add(new LinkOrObject(paper)); }
        List<Book> books = (List<Book>) bookService.getRepository().findAll();
        for(Book book : books) { if(book.getGenerator().getLitObject().getName().equals(actorName)) createdByActor.add(new LinkOrObject(book)); }
        List<BibTeXArticle> bibTeXArticles = (List<BibTeXArticle>) bibTeXArticleService.getRepository().findAll();
        for(BibTeXArticle bibTeXArticle : bibTeXArticles) { if(bibTeXArticle.getGenerator().getLitObject().getName().equals(actorName)) createdByActor.add(new LinkOrObject(bibTeXArticle)); }
        List<Journal> journals = (List<Journal>) journalService.getRepository().findAll();
        for(Journal journal : journals) { if(journal.getGenerator().getLitObject().getName().equals(actorName)) createdByActor.add(new LinkOrObject(journal)); }
        //List<LinkOrObject> results = objects.values().stream().filter(value -> value.getGenerator().getLink().equals(actorId)).map(LinkOrObject::new).collect(Collectors.toList());
        return new OrderedCollection(createdByActor);
    }

    @Override
    public OrderedCollection getRelevantObjects(String actorName) {
        List<RelevantObject> relevantObjects = (List<RelevantObject>) relevantObjectService.getRepository().findAll();
        List<LinkOrObject> returnObjects = new ArrayList();
        for(int i = 0; i<relevantObjects.size(); i++) {
            RelevantObject current = relevantObjects.get(i);
            String name = current.getActorname();
            if(name.equals(actorName)) {
                String objectType = current.getObjectType();

                long id = current.getObjectID();
                returnObjects.add(new LinkOrObject(findObjectInTable(String.valueOf(id-1), objectType)));
            }
        }
        if(returnObjects.size() > 0) return new OrderedCollection(returnObjects);

        return new OrderedCollection(returnObjects.stream().map((id) -> new LinkOrObject(objects.get(id))).collect(Collectors.toList()));
    }

    @Override
    public void addToRelevantObjects(String actorName, LinkOrObject toAdd) {
        ActivityPubObject obj;
        String link;
        if(toAdd.getLitObject() == null) {
            link = toAdd.getLink();
        }
        else {
            obj = toAdd.getLitObject();
            RelevantObject relevantObject = new RelevantObject();
            relevantObject.setObjectID(obj.getActivityPubID()); //# TODO: id of object instead of activity
            if(obj instanceof Activity) relevantObject.setObjectType(((Activity) obj).getObject().getLitObject().getType());
            relevantObject.setActorname(actorName);
            relevantObjectService.getRepository().save(relevantObject);
        }
    }

    //# ACTIVITY
    @Override
    public Activity getActivity(String id) {

        List<Accept> accept = (List<Accept>) acceptService.getRepository().findAll();
        List<Accept> a = accept.stream().filter(s -> s.getId().equals(id)).collect(Collectors.toList());
        if (a.size() != 0) return a.get(0);

        List<Create> create = (List<Create>) createService.getRepository().findAll();
        List<Create> c = create.stream().filter(s -> s.getId().equals(id)).collect(Collectors.toList());
        if (c.size() != 0) return c.get(0);

        List<Delete> delete = (List<Delete>) deleteService.getRepository().findAll();
        List<Delete> d = delete.stream().filter(s -> s.getId().equals(id)).collect(Collectors.toList());
        if (d.size() != 0) return d.get(0);

        List<Follow> follow = (List<Follow>) followService.getRepository().findAll();
        List<Follow> f = follow.stream().filter(s -> s.getId().equals(id)).collect(Collectors.toList());
        if (f.size() != 0) return f.get(0);

        List<Ignore> ignore = (List<Ignore>) ignoreService.getRepository().findAll();
        List<Ignore> i = ignore.stream().filter(s -> s.getId().equals(id)).collect(Collectors.toList());
        if (i.size() != 0) return i.get(0);

        List<Like> like = (List<Like>) likeService.getRepository().findAll();
        List<Like> l = like.stream().filter(s -> s.getId().equals(id)).collect(Collectors.toList());
        if (l.size() != 0) return l.get(0);

        List<Reject> reject = (List<Reject>) rejectService.getRepository().findAll();
        List<Reject> r = reject.stream().filter(s -> s.getId().equals(id)).collect(Collectors.toList());
        if (r.size() != 0) return r.get(0);

        List<Update> update = (List<Update>) updateService.getRepository().findAll();
        List<Update> u = update.stream().filter(s -> s.getId().equals(id)).collect(Collectors.toList());
        if (u.size() != 0) return u.get(0);

        return null;
    }

    @Override
    public Activity createActivity(String actorName, Activity activity) {
        UUID uuid = UUID.randomUUID();
        String id = UriUtilities.generateId(new String[]{actorName}, serverPort, uuid);
        activity.setId(id);

        String type = activity.getType();
        if (activity instanceof Accept) {
            Accept accept = (Accept)activity;
            IAcceptRepository repo = acceptService.getRepository();
            repo.save(accept);
            return accept;
        }
        if (type.equals(IActivityConstants.CREATE)) {
            Create create = (Create)activity;
            ICreateRepository repo = createService.getRepository();
            repo.save(create);
            return create;
        }
        if (type.equals(IActivityConstants.DELETE)) {
            Delete delete = new Delete(activity);
            IDeleteRepository repo = deleteService.getRepository();
            repo.save(delete);
            return delete;
        }
        if (type.equals(IActivityConstants.FOLLOW)) {
            Follow follow = new Follow(activity);
            IFollowRepository repo = followService.getRepository();
            repo.save(follow);
            return follow;
        }
        if (type.equals(IActivityConstants.IGNORE)) {
            Ignore ignore = new Ignore(activity);
            IIgnoreRepository repo = ignoreService.getRepository();
            repo.save(ignore);
            return ignore;
        }
        if (type.equals(IActivityConstants.LIKE)) {
            Like like = new Like(activity);
            ILikeRepository repo = likeService.getRepository();
            repo.save(like);
            return like;
        }
        if (type.equals(IActivityConstants.REJECT)) {
            Reject reject = new Reject(activity);
            IRejectRepository repo = rejectService.getRepository();
            repo.save(reject);
            return reject;
        }
        if (type.equals(IActivityConstants.UPDATE)) {
            Update update = new Update(activity);
            IUpdateRepository repo = updateService.getRepository();
            repo.save(update);
            return update;
        }
        return null;
    }

    //# LIKED
    @Override
    public OrderedCollection getLikedCollection(String actorName) {
        List<Liked> likedList = (List<Liked>) likedService.getRepository().findAll();
        List<LinkOrObject> returnObjects = new ArrayList();
        for(int i = 0; i<likedList.size(); i++) {
            Liked current = likedList.get(i);
            String name = current.getActorname();
            if(name.equals(actorName)) {
                String objectType = current.getObjectType();
                long id = current.getObjectID();
                returnObjects.add(new LinkOrObject(findObjectInTable(String.valueOf(id-1), objectType)));
            }
        }
        return new OrderedCollection(returnObjects);
    }
    //Hristina
    @Override
    public void addToLiked(String actorName, LinkOrObject toAdd) {
        ActivityPubObject obj;
        String link;
        if (toAdd.getLitObject() == null) {
            link = toAdd.getLink();
        } else {
            obj = toAdd.getLitObject();
            Liked liked = new Liked();
            if (obj instanceof Activity) {
                long l = ((Activity) obj).getObject().getLitObject().getActivityPubID();
                liked.setObjectID(l);
                liked.setObjectType(((Activity) obj).getObject().getLitObject().getType());
            }
            liked.setActorname(actorName);
            likedService.getRepository().save(liked);
        }
    }

    //# INBOX
    @Override
    public OrderedCollection getInbox(String actorName) {
        List<Inbox> inboxList = (List<Inbox>) inboxService.getRepository().findAll();
        List<LinkOrObject> returnObjects = new ArrayList();
        for(int i = 0; i<inboxList.size(); i++) {
            Inbox current = inboxList.get(i);
            String name = current.getActorname();
            if(name.equals(actorName)) {
                String objectType = current.getObjectType();
                long id = current.getObjectID();
                returnObjects.add(new LinkOrObject(findObjectInTable(String.valueOf(id-1), objectType)));
            }
        }
        return new OrderedCollection(returnObjects);
    }

    //Hristina
    @Override
    public void addToInbox(String actorName, LinkOrObject toAdd) {
        ActivityPubObject obj;
        String link;
        if (toAdd.getLitObject() == null) {
            link = toAdd.getLink();
        } else {
            obj = toAdd.getLitObject();
            Inbox inbox = new Inbox();
            if (obj instanceof Activity) {
                long l = ((Activity) obj).getObject().getLitObject().getActivityPubID();
                inbox.setObjectID(l);
                inbox.setObjectType(((Activity) obj).getObject().getLitObject().getType());
            }
            inbox.setActorname(actorName);
            inboxService.getRepository().save(inbox);
        }
    }

    //# OUTBOX MUSTERBEISPIEL
    @Override
    public OrderedCollection getOutbox(String actorName) {
        List<Outbox> outboxList = (List<Outbox>) outboxService.getRepository().findAll();
        List<LinkOrObject> returnObjects = new ArrayList();
        for(int i = 0; i<outboxList.size(); i++) {
            Outbox current = outboxList.get(i);
            String name = current.getActorname();
            if(name.equals(actorName)) {
                String objectType = current.getObjectType();

                long id = current.getObjectID();
                returnObjects.add(new LinkOrObject(findObjectInTable(String.valueOf(id-1), objectType)));
            }
        }
        return new OrderedCollection(returnObjects);
    }

    @Override
    public void addToOutbox(String actorName, LinkOrObject toAdd) {
        ActivityPubObject obj;
        String link;
        if(toAdd.getLitObject() == null) {
            link = toAdd.getLink();
        }
        else {
            obj = toAdd.getLitObject();
            Outbox outbox = new Outbox();
            if(obj instanceof Activity) {
                long l = ((Activity) obj).getObject().getLitObject().getActivityPubID();
                outbox.setObjectID(l); //# TODO: id of object instead of activity
                outbox.setObjectType(((Activity) obj).getObject().getLitObject().getType());
            }
            outbox.setActorname(actorName);
            outboxService.getRepository().save(outbox);
        }
    }

    //# FOLLOWING
    @Override
    public OrderedCollection getFollowingCollection(String actorName) {
        List<Following> followingList = (List<Following>) followingService.getRepository().findAll();
        List<LinkOrObject> returnObjects = new ArrayList();
        for(int i = 0; i<followingList.size(); i++) {
            Following current = followingList.get(i);
            String name = current.getActorname();
            if(name.equals(actorName)) {
                String objectType = current.getObjectType();

                long id = current.getObjectID();
                returnObjects.add(new LinkOrObject(findObjectInTable(String.valueOf(id-1), objectType)));
            }
        }
        return new OrderedCollection(returnObjects);
    }

    //Karim DONE(X)
    @Override
    public void addToFollowing(String actorName, LinkOrObject toAdd) {
        ActivityPubObject obj;
        String link;
        if(toAdd.getLitObject() == null) {
            link = toAdd.getLink();
        }
        else {
            obj = toAdd.getLitObject();
            Following following = new Following();
            if(obj instanceof Activity) {
                long l = ((Activity) obj).getObject().getLitObject().getActivityPubID();
                following.setObjectID(l); //# TODO: id of object instead of activity
                following.setObjectType(((Activity) obj).getObject().getLitObject().getType());
            }
            following.setActorname(actorName);
            followingService.getRepository().save(following);
        }
    }

    //# FOLLOWERS
    @Override
    public OrderedCollection getFollowersCollection(String actorName) {
        List<Followed> followedList = (List<Followed>) followedService.getRepository().findAll();
        List<LinkOrObject> returnObjects = new ArrayList();
        for(int i = 0; i<followedList.size(); i++) {
            Followed current = followedList.get(i);
            String name = current.getActorname();
            if(name.equals(actorName)) {
                String objectType = current.getObjectType();

                long id = current.getObjectID();
                returnObjects.add(new LinkOrObject(findObjectInTable(String.valueOf(id-1), objectType)));
            }
        }
        return new OrderedCollection(returnObjects);
    }

    //Karim DONE (X)
    @Override
    public void addToFollowers(String actorName, LinkOrObject toAdd) {
        ActivityPubObject obj;
        String link;
        if(toAdd.getLitObject() == null) {
            link = toAdd.getLink();
        }
        else {
            obj = toAdd.getLitObject();
            Followed followed = new Followed();
            if (obj instanceof Activity)
                {
                    long l = ((Activity)obj).getObject().getLitObject().getActivityPubID();
                    followed.setObjectID(l);
                    followed.setObjectType(((Activity)obj).getObject().getLitObject().getType());
                }
            followed.setActorname(actorName);
            followedService.getRepository().save(followed);
        }
    }

    //# STORAGE UTIL
    public ActivityPubObject findObjectInTable(String id, String tableName) {
        if(tableName.equals(ILitObjectConstants.AUTHOR)) {
            IAuthorRepository authorRepo = authorService.getRepository();
            List<Author> authors = (List<Author>) authorRepo.findAll();
            for(Author author : authors) {
                if(author.getId().equals(id)) return author;
            }
        }
        if(tableName.equals(ILitObjectConstants.ACTIVITYPUBCOLLECTION)) {
            IActivityPubCollectionRepository activityPubCollectionRepository = activityPubCollectionService.getRepository();
            List<ActivityPubCollection> activityPubCollections = (List<ActivityPubCollection>) activityPubCollectionRepository.findAll();
            for(ActivityPubCollection activityPubCollection : activityPubCollections) {
                if(activityPubCollection.getId().equals(id)) return activityPubCollection;
            }
        }
        if(tableName.equals(ILitObjectConstants.BOOK)) {
            IBookRepository bookRepository = bookService.getRepository();
            List<Book> books = (List<Book>) bookRepository.findAll();
            for(Book book : books) {
                if(book.getId().equals(id)) return book;
            }
        }
        if(tableName.equals(ILitObjectConstants.JOURNAL)) {
            IJournalRepository journalRepository = journalService.getRepository();
            List<Journal> journals = (List<Journal>) journalRepository.findAll();
            for(Journal journal : journals) {
                if(journal.getId().equals(id)) return journal;
            }
        }
        if(tableName.equals(ILitObjectConstants.BIBTEXARTICLE)) {
            IBibTeXArticleRepository bibTeXArticleRepository = bibTeXArticleService.getRepository();
            List<BibTeXArticle> bibTeXArticles = (List<BibTeXArticle>) bibTeXArticleRepository.findAll();
            for(BibTeXArticle bibTeXArticle : bibTeXArticles) {
                if(bibTeXArticle.getId().equals(id)) return bibTeXArticle;
            }
        }
        if(tableName.equals(ILitObjectConstants.PAPER)) {
            IPaperRepository paperRepository = paperService.getRepository();
            List<Paper> papers = (List<Paper>) paperRepository.findAll();
            for(Paper paper : papers) {
                if(paper.getId().equals(id)) return paper;
            }
        }
        return null;
    }

    public ActivityPubObject findObject(String id) {
        IAuthorRepository authorRepo = authorService.getRepository();
        List<Author> authors = (List<Author>) authorRepo.findAll();
        for(Author author : authors) {
            if(author.getId().equals(id)) return author;
        }
        IBibTeXArticleRepository bibTeXArticleRepository = bibTeXArticleService.getRepository();
        List<BibTeXArticle> bibTeXArticles = (List<BibTeXArticle>) bibTeXArticleRepository.findAll();
        for(BibTeXArticle bibTeXArticle : bibTeXArticles) {
            if(bibTeXArticle.getId().equals(id)) return bibTeXArticle;
        }
        IBookRepository bookRepository = bookService.getRepository();
        List<Book> books = (List<Book>) bookRepository.findAll();
        for(Book book : books) {
            if(book.getId().equals(id)) return book;
        }
        IJournalRepository journalRepository = journalService.getRepository();
        List<Journal> journals = (List<Journal>) journalRepository.findAll();
        for(Journal journal : journals) {
            if(journal.getId().equals(id)) return journal;
        }
        IPaperRepository paperRepository = paperService.getRepository();
        List<Paper> papers = (List<Paper>) paperRepository.findAll();
        for(Paper paper : papers) {
            if(paper.getId().equals(id)) return paper;
        }
        IActivityPubCollectionRepository activityPubCollectionRepository = activityPubCollectionService.getRepository();
        List<ActivityPubCollection> activityPubCollections = (List<ActivityPubCollection>) activityPubCollectionRepository.findAll();
        for(ActivityPubCollection activityPubCollection : activityPubCollections) {
            if(activityPubCollection.getId().equals(id)) return activityPubCollection;
        }
        return null;
    }

    public void deleteActorInboxOutbox(Actor actor) {
        List<Inbox> inboxes = (List<Inbox>)inboxService.getRepository().findAll();
        for(Inbox inbox : inboxes) if(inbox.getActorname().equals(actor.getName())) inboxService.getRepository().delete(inbox);
        List<Outbox> outboxes = (List<Outbox>)outboxService.getRepository().findAll();
        for(Outbox outbox : outboxes) if(outbox.getActorname().equals(actor.getName())) outboxService.getRepository().delete(outbox);
    }
}