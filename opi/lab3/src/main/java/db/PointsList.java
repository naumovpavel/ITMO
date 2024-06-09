package db;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import java.util.List;
import java.util.Map;

@Named("pointsList")
@SessionScoped
public class PointsList extends LazyDataModel<Point> {
    @Inject
    private ManagerDB service;

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return service.getPointsCount();
    }

    @Override
    public List<Point> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        return service.getPoints();
    }
}
