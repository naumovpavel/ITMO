package server.handlers;

import common.models.LabWork;
import common.models.Model;
import common.models.Person;
import common.utils.Event;
import common.utils.ModelTree;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.Collectors;

public class LabWorkHandler extends CollectionHandler<PriorityBlockingQueue<LabWork>, LabWork>{
    //private PriorityQueue<LabWork> labWorks;


    public LabWorkHandler(ModelTree tree) {
        super(tree);
        this.collection = new PriorityBlockingQueue<>();
        this.type = LabWork.class;
        load();
    }


    @Override
    public PriorityBlockingQueue<LabWork> getCollection() {
        return this.collection;
    }

    @Override
    public Class<LabWork> getType() {
        return LabWork.class;
    }

    @Override
    public LabWork getHead() {
        return collection.peek();
    }

    @Override
    public boolean update(int id, Model obj, int userId) {
        Optional<LabWork> model = collection.stream().filter(x -> x.getId() == id && x.getOwner_id() == userId).findAny();
        if(model.isEmpty()) {
            return false;
        }

        boolean updated = databaseManager.update(obj);
        if(!updated) {
            return false;
        }
        collection.removeIf(x -> x.getId() == id);
        collection.add((LabWork) obj);
        eventHandler.addEvent(obj, Event.UPDATE);
        return true;
    }

    @Override
    public boolean clear(int userId) {
        Set<Integer> ids = collection.stream().filter(x -> x.getOwner_id() == userId).map(LabWork::getId).collect(Collectors.toSet());
        if(!databaseManager.delete(ids)) {
            return false;
        }
        return remove(ids);
    }

    @Override
    public boolean removeById(int id, int userId) {
        Set<Integer> ids = collection.stream().filter(x -> x.getId() == id && x.getOwner_id() == userId).map(LabWork::getId).collect(Collectors.toSet());
        System.out.println(ids);
        if(ids.size() == 0) {
            return false;
        }
        if(!databaseManager.delete((int)ids.toArray()[0]))
            return false;

        return remove(ids);
    }

    private boolean remove(Set<Integer> ids) {
        return collection.removeIf(x -> {
            if(ids.contains(x.getId())) {
                eventHandler.addEvent(x, Event.DELETE);
                return true;
            }
            return false;
        });
    }

    @Override
    public LabWork removeHead(int userId) {
        LabWork labWork = collection.peek();
        if(labWork.getOwner_id() != userId) {
            return null;
        }
        HashSet<Integer> id = new HashSet<>();
        id.add(labWork.getId());
        if(!databaseManager.delete(id))
            return null;
        remove(id);
        return labWork;
    }

    @Override
    public LabWork maxByName() {
        final LabWork[] labWork = {collection.peek()};
        collection.forEach(x -> {if (labWork[0].getName().compareTo(x.getName()) < 0) {labWork[0] = x;}});
        return labWork[0];
    }

    @Override
    public ArrayList<Integer> printFieldDescending() {
        ArrayList<Integer> tunedInWorks = new ArrayList<>();
        collection.forEach(x -> tunedInWorks.add(x.getTunedInWorks()));
        tunedInWorks.sort((x, y) -> x <= y ? 1 : -1);
        return tunedInWorks;
    }

    @Override
    public boolean add(Model obj, int userId) {
        LabWork labWork = (LabWork) obj;
        ((LabWork) obj).setOwner_id(userId);
        int id = databaseManager.add(labWork);
        if(id == -1) {
            return false;
        }
        labWork.setId(id);
        collection.add(labWork);
        eventHandler.addEvent(labWork, Event.ADD);
        return true;
    }

    @Override
    public int countLess(Person obj) {
        return (int)collection.stream().filter(x -> x.getAuthor().compareTo(obj) >= 0).count();
    }

    @Override
    public <U extends Model> int countLessThan(U obj) {
        return 0;
    }

    @Override
    public LabWork getById(int id) {
        return  (LabWork) collection.stream().filter(x -> x.getId() == (id)).findFirst().get();
    }

    @Override
    public boolean removeLower(Model obj, int userId) {
        Set<Integer> ids = collection.stream().filter(x -> ((LabWork)obj).compareTo((LabWork) x) > 0 && x.getOwner_id() == userId).map(LabWork::getId).collect(Collectors.toSet());
        if(!databaseManager.delete(ids))
            return false;
        return remove(ids);
    }

    @Override
    public boolean hasId(int id, int userId) {
        return collection.stream().anyMatch(x -> x.getId() == id && userId == x.getOwner_id());
    }

    @Override
    public PriorityBlockingQueue<LabWork> show(int id) {
        eventHandler.setUserLastEvent(id);
        return collection;
    }
}
