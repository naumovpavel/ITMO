package handlers;

import models.LabWork;
import models.Model;
import models.Person;
import utils.*;

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
        for(var x : collection) {
            if(x.getId().equals(id)) {
                collection.remove(x);
                return true;
            }
        }
        return false;
    }

    @Override
    public LabWork removeHead() {
        return collection.poll();
    }

    @Override
    public LabWork maxByName() {
        LabWork labWork = null;
        String max = "";
        for(var x : collection) {
            if(max.compareTo(x.getName()) < 0) {
                labWork = x;
                max = x.getName();
            }
        }
        return labWork;
    }

    @Override
    public void printFieldDescending() {
        ArrayList<Long> tunedInWorks = new ArrayList<>();
        for(var x : collection) {
            tunedInWorks.add(x.getTunedInWorks());
        }
        tunedInWorks.sort((x, y) -> x <= y ? 1 : -1);
        for(var x : tunedInWorks) {
            System.out.println(x);
        }
    }

    @Override
    public void add(LabWork obj) {
        collection.add(obj);
    }

    @Override
    public int countLess(Person obj) {
        int res = 0;
        for (var x : collection) {
            if(x.getAuthor().compareTo(obj) >= 0) {
                res++;
            }
        }
        return res;
    }

    @Override
    public <U extends Model> int countLessThan(U obj) {
        return 0;
    }

    @Override
    public void update(LabWork obj, Long id) {
        for(var x : collection) {
            if(x.getId().equals(id)) {
                collection.remove(x);
                add(obj);
                break;
            }
        }
    }

    @Override
    public void removeLower(LabWork obj) {
        collection.removeIf(x -> obj.compareTo(x) > 0);
    }

    @Override
    public boolean hasId(Long id) {
        for(var x : collection) {
            if(x.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
