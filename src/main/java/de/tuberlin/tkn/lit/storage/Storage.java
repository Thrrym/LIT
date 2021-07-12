package de.tuberlin.tkn.lit.storage;

import de.tuberlin.tkn.lit.constants.IActivityConstants;
import de.tuberlin.tkn.lit.constants.ILitObjectConstants;
import de.tuberlin.tkn.lit.constants.ISocialConstants;
import de.tuberlin.tkn.lit.constants.UriConstants;
import de.tuberlin.tkn.lit.model.activitypub.activities.*;
import de.tuberlin.tkn.lit.model.activitypub.activities.Activity;
import de.tuberlin.tkn.lit.model.activitypub.activities.Like;
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
import de.tuberlin.tkn.lit.storage_social.IInboxRepository;
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
    private final Map<String, List<Activity>> federation = new HashMap<>(); // other server uris as keys to pending send tasks

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
    public List<Activity> getPendingActivities(String url) {
        if (federation.containsKey(url)) {
            List<Activity> toReturn = federation.get(url);
            federation.replace(url, new ArrayList<Activity>());
            return toReturn;
        }
        else {
            List<Activity> l = new ArrayList<Activity>();
            federation.put(url, l);
            return l;
        }
    }

    @Override
    public void addPendingActivity(String url, Activity activity) {
        if (federation.containsKey(url)) {
            List<Activity> l = federation.get(url);
            l.add(activity);
            federation.replace(url, l);
        }
        else {
            List<Activity> l = new ArrayList<Activity>();
            l.add(activity);
            federation.put(url, l);
        }

        System.out.print("Add activity as pending for " + url + "\n");
    }

    @Override
    public List<String> getFederatedHosts() {
        List<String> federatedHosts = new ArrayList<>(federation.keySet());
        return federatedHosts;
    }

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
        object.setGenerator(new LinkOrObject(getActor(actorName)));

        if(objectType.equals(ILitObjectConstants.AUTHOR)
                && object instanceof Author) {
            IAuthorRepository authorRepo = authorService.getRepository();
            authorRepo.save((Author) object);
            return object;
        }
        if(object instanceof BibTeXArticle) {
            IBibTeXArticleRepository bibTeXArticleRepository = bibTeXArticleService.getRepository();
            bibTeXArticleRepository.save((BibTeXArticle) object);
            Author newAuthor = new Author();

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

        if(object instanceof Author) {
            IAuthorRepository authorRepo = authorService.getRepository();
            List<Author> authors = (List<Author>) authorRepo.findAll();
            for(Author author : authors) {
                if(author.getId().equals(id)) {
                    Author newAuthor = (Author) object;
                    //authorRepo.delete(author);
                    author.setType("Tombstone");
                    authorRepo.save(newAuthor);
                    return newAuthor;
                }
            }
            return null;
        }
        if(object instanceof BibTeXArticle) {
            IBibTeXArticleRepository bibTeXArticleRepository = bibTeXArticleService.getRepository();
            List<BibTeXArticle> bibTeXArticles = (List<BibTeXArticle>) bibTeXArticleRepository.findAll();
            for(BibTeXArticle bibTeXArticle : bibTeXArticles) {
                if(bibTeXArticle.getId().equals(id)) {
                    BibTeXArticle newBibTeXArticle = (BibTeXArticle) object;
                    bibTeXArticle.setType("Tombstone");
                    bibTeXArticleRepository.save(newBibTeXArticle);
                    return newBibTeXArticle;
                }
            }
            return null;
        }
        if(object instanceof Book) {
            IBookRepository bookRepository = bookService.getRepository();
            List<Book> books = (List<Book>) bookRepository.findAll();
            for(Book book : books) {
                if(book.getId().equals(id)) {
                    Book newBook = (Book) object;
                    //bookRepository.delete(book);
                    book.setType("Tombstone");
                    bookRepository.save(newBook);
                    return newBook;
                }
            }
            return null;
        }
        if(object instanceof Journal) {
            IJournalRepository journalRepository = journalService.getRepository();
            List<Journal> journals = (List<Journal>) journalRepository.findAll();
            for(Journal journal : journals) {
                if(journal.getId().equals(id)) {
                    Journal newJournal = (Journal) object;
                    //journalRepository.delete(journal);
                    journal.setType("Tombstone");
                    journalRepository.save(newJournal);
                    return newJournal;
                }
            }
            return null;
        }
        if(object instanceof Paper) {
            IPaperRepository paperRepository = paperService.getRepository();
            List<Paper> papers = (List<Paper>) paperRepository.findAll();
            for(Paper paper : papers) {
                if(paper.getId().equals(id)) {
                    Paper newPaper = (Paper) object;
                    //paperRepository.delete(paper);
                    paper.setType("Tombstone");
                    paperRepository.save(newPaper);
                    return newPaper;
                }
            }
            return null;
        }

        //Activityproblem
        if(object instanceof ActivityPubCollection) {
            IActivityPubCollectionRepository activityPubCollectionRepository = activityPubCollectionService.getRepository();
            List<ActivityPubCollection> activityPubCollections = (List<ActivityPubCollection>) activityPubCollectionRepository.findAll();
            for(ActivityPubCollection activityPubCollection : activityPubCollections) {
                if(activityPubCollection.getId().equals(id)) {
                    ActivityPubCollection newActivityPubCollection = (ActivityPubCollection) object;
                    //activityPubCollectionRepository.delete(activityPubCollection);
                    activityPubCollection.setType("Tombstone");
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
                    //authorRepo.delete(author);
                    author.setType("Tombstone");
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
                    //bibTeXArticleRepository.delete(bibTeXArticle);
                    bibTeXArticle.setType("Tombstone");
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
                    //bookRepository.delete(book);
                    book.setType("Tombstone");
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
                    //journalRepository.delete(journal);
                    journal.setType("Tombstone");
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
                    //paperRepository.delete(paper);
                    paper.setType("Tombstone");
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
                    //activityPubCollectionRepository.delete(activityPubCollection);
                    activityPubCollection.setType("Tombstone");
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
                returnObjects.add(new LinkOrObject(findObjectInTable(String.valueOf(id), objectType)));
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
        if(accept.size() > 0) {
            List<Accept> a = accept.stream().filter(s -> s.getId().equals(id)).collect(Collectors.toList());
            if (a.size() != 0) return a.get(0);
        }
        List<Create> create = (List<Create>) createService.getRepository().findAll();
        if(create.size() > 0) {
            List<Create> c = create.stream().filter(s -> s.getId().equals(id)).collect(Collectors.toList());
            if (c.size() != 0) return c.get(0);
        }
        List<Delete> delete = (List<Delete>) deleteService.getRepository().findAll();
        if(delete.size() > 0) {
            List<Delete> d = delete.stream().filter(s -> s.getId().equals(id)).collect(Collectors.toList());
            if (d.size() != 0) return d.get(0);
        }
        List<Follow> follow = (List<Follow>) followService.getRepository().findAll();
        if(follow.size() > 0) {
            List<Follow> f = follow.stream().filter(s -> s.getId().equals(id)).collect(Collectors.toList());
            if (f.size() != 0) return f.get(0);
        }
        List<Follow> f = follow.stream().filter(s -> s.getId().equals(id)).collect(Collectors.toList());
        if (f.size() != 0) return f.get(0);

        List<Like> like = (List<Like>) likeService.getRepository().findAll();
        if(like.size() > 0) {
            List<Like> l = like.stream().filter(s -> s.getId().equals(id)).collect(Collectors.toList());
            if (l.size() != 0) return l.get(0);
        }
        List<Reject> reject = (List<Reject>) rejectService.getRepository().findAll();
        if(reject.size() > 0) {
            List<Reject> r = reject.stream().filter(s -> s.getId().equals(id)).collect(Collectors.toList());
            if (r.size() != 0) return r.get(0);
        }
        List<Update> update = (List<Update>) updateService.getRepository().findAll();
        if(update.size() > 0) {
            List<Update> u = update.stream().filter(s -> s.getId().equals(id)).collect(Collectors.toList());
            if (u.size() != 0) return u.get(0);
        }

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
        if (activity instanceof Create) {
            Create create = (Create)activity;
            ICreateRepository repo = createService.getRepository();
            repo.save(create);
            return create;
        }
        if (activity instanceof Delete) {
            Delete delete = (Delete) activity;
            IDeleteRepository repo = deleteService.getRepository();
            repo.save(delete);
            return delete;
        }
        if (activity instanceof Follow) {
            Follow follow = (Follow) activity;
            IFollowRepository repo = followService.getRepository();
            repo.save(follow);
            return follow;
        }
        if (activity instanceof Like) {
            Like like = (Like) activity;
            ILikeRepository repo = likeService.getRepository();
            repo.save(like);
            return like;
        }
        if (activity instanceof Reject) {
            Reject reject = (Reject) activity;
            IRejectRepository repo = rejectService.getRepository();
            repo.save(reject);
            return reject;
        }
        if (activity instanceof Update) {
            Update update = (Update) activity;
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
                returnObjects.add(new LinkOrObject(findObjectInTable(String.valueOf(id), objectType)));
            }
        }
        return new OrderedCollection(returnObjects);
    }
    //Hristina
    @Override
    public void addToLiked(String actorName, LinkOrObject toAdd) {
        Liked liked = new Liked();
        ActivityPubObject obj;
        String link;
        if (toAdd.getLitObject() == null) {
            link = toAdd.getLink();
        } else {
            obj = toAdd.getLitObject();
            if (obj instanceof Author
            || obj instanceof Paper
            || obj instanceof BibTeXArticle
            || obj instanceof Book) {
                liked.setObjectID(obj.getActivityPubID());
                liked.setObjectType(obj.getType());
            }
            if (obj instanceof Activity) {
                long l = ((Activity)obj).getObject().getLitObject().getActivityPubID();
                liked.setObjectID(l);
                liked.setObjectType(((Activity)obj).getObject().getLitObject().getType());
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
                returnObjects.add(new LinkOrObject(findObjectInTable(String.valueOf(id), objectType)));
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
                long id2 = current.getActivityPubID();
                ActivityPubObject xobj = findObjectInTable(String.valueOf(id), objectType);
                returnObjects.add(new LinkOrObject(findObjectInTable(String.valueOf(id), objectType)));
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
                LinkOrObject lor = ((Activity) obj).getObject();
                ActivityPubObject a_obj = lor.getLitObject();
                if(a_obj != null) {
                    ActivityPubObject apo = lor.getLitObject();
                    long l = apo.getActivityPubID();
                    outbox.setObjectID(l);
                    outbox.setObjectType(((Activity) obj).getObject().getLitObject().getType());
                }
                else {
                    String link1 = lor.getLink();
                    System.out.println(link1);

                }
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
                returnObjects.add(new LinkOrObject(findObjectInTable(String.valueOf(id), objectType)));
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
                returnObjects.add(new LinkOrObject(findObjectInTable(String.valueOf(id), objectType)));
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
                if(author.getActivityPubID() == Long.valueOf(id)) return author;
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
        if(tableName.equals("bibtex_article")) {
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
        if(tableName.equals(ISocialConstants.INBOX)) {
            IInboxRepository authorRepo = inboxService.getRepository();
            List<Inbox> inboxes = (List<Inbox>) authorRepo.findAll();
            for(Inbox inbox : inboxes) {
                if(inbox.getObjectID() == Long.valueOf(id)) return inbox;
            }
        }
        if(tableName.equals(ISocialConstants.LIKED)) {
            IAuthorRepository authorRepo = authorService.getRepository();
            List<Author> authors = (List<Author>) authorRepo.findAll();
            for(Author author : authors) {
                if(author.getActivityPubID() == Long.valueOf(id)) return author;
            }
        }
        if(tableName.equals(ISocialConstants.OUTBOX)) {
            IAuthorRepository authorRepo = authorService.getRepository();
            List<Author> authors = (List<Author>) authorRepo.findAll();
            for(Author author : authors) {
                if(author.getActivityPubID() == Long.valueOf(id)) return author;
            }
        }
        if(tableName.equals(ISocialConstants.FOLLOWING)) {
            IAuthorRepository authorRepo = authorService.getRepository();
            List<Author> authors = (List<Author>) authorRepo.findAll();
            for(Author author : authors) {
                if(author.getActivityPubID() == Long.valueOf(id)) return author;
            }
        }
        if(tableName.equals(ISocialConstants.FOLLOWED)) {
            IAuthorRepository authorRepo = authorService.getRepository();
            List<Author> authors = (List<Author>) authorRepo.findAll();
            for(Author author : authors) {
                if(author.getActivityPubID() == Long.valueOf(id)) return author;
            }
        }
        if(tableName.equals(ISocialConstants.RELEVANTOBJECT)) {
            IAuthorRepository authorRepo = authorService.getRepository();
            List<Author> authors = (List<Author>) authorRepo.findAll();
            for(Author author : authors) {
                if(author.getActivityPubID() == Long.valueOf(id)) return author;
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
        for(Inbox inbox : inboxes) if(inbox.getActorname().equals(actor.getName())) {
            inbox.setObjectType("Tombstone");
            inboxService.getRepository().save(inbox);
        }
        List<Outbox> outboxes = (List<Outbox>)outboxService.getRepository().findAll();
        for(Outbox outbox : outboxes) if(outbox.getActorname().equals(actor.getName())) {
            outbox.setObjectType("Tombstone");
            outboxService.getRepository().save(outbox);
        }
    }
}