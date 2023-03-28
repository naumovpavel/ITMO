package server.handlers;

import common.models.LabWork;
import common.models.Model;
import common.models.Person;
import common.utils.ModelTree;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class LabWorkHandler extends CollectionHandler<PriorityQueue<LabWork>, LabWork>{
    //private PriorityQueue<LabWork> labWorks;


    public LabWorkHandler(ModelTree tree) {
        super(tree);
        this.collection = new PriorityQueue<>();
        this.type = LabWork.class;
        load();
    }

//    public PriorityQueue<LabWork> getLabWorks() {
//        return collection;
//    }

    public void setLabWorks(PriorityQueue<LabWork> labWorks) {
        this.collection = labWorks;
    }

    @Override
    public PriorityQueue<LabWork> getCollection() {
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
    public void clear() {
        collection.clear();
    }

    @Override
    public boolean removeById(Long id) {
        return collection.removeIf(x -> x.getId().equals(id));
    }

    @Override
    public LabWork removeHead() {
        return collection.poll();
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
    public void add(Model obj) {
        collection.add((LabWork) obj);
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
    public LabWork getById(Long id) {
        return  (LabWork) collection.stream().filter(x -> x.getId().equals(id)).toArray()[0];
    }

    @Override
    public void removeLower(Model obj) {
        collection.removeIf(x -> ((LabWork)obj).compareTo((LabWork) x) > 0);
    }

    @Override
    public boolean hasId(Long id) {
        return collection.stream().anyMatch(x -> x.getId().equals(id));
    }
}
