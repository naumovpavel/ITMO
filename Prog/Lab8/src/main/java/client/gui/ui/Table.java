package client.gui.ui;

import client.gui.frames.MainWindow;
import client.handlers.CollectionSubscriber;
import common.models.Model;
import common.utils.*;
import common.utils.Event;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Table extends JPanel implements CollectionSubscriber {
    public ModelTree tree;
    private ArrayList<Predicate<? super Model>> predicates = new ArrayList<>();
    private ArrayList<Comparator<? super Model>> comparators = new ArrayList<>();
    private Collection<Model> rowCollection = new ArrayList<>();
    private Collection<Model> collection = new ArrayList<>();

    private DefaultTableModel tableModel;
    private JTable dataTable;

    public Table(ModelTree tree) {
        this.tree = tree;
        setLayout(new BorderLayout());
        setBackground(new Color(0x242F3D));
        add(renderTable(), BorderLayout.CENTER);
    }

    public JScrollPane renderTable() {
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        addColumns(tree, tableModel, "");

        dataTable = new JTable(tableModel);
        dataTable.setBackground(new Color(0x242F3D));
        dataTable.setForeground(new Color(0xE7ECF1));
        dataTable.setSelectionBackground(new Color(0x3E546A));
        dataTable.getTableHeader().setBackground(new Color(0x17212B));
        dataTable.getTableHeader().setForeground(new Color(0xE7ECF1));
        dataTable.setRowHeight(30);

        // Create the scroll pane for the table
        JScrollPane tableScrollPane = new JScrollPane(dataTable);
        tableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tableScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        tableScrollPane.setBackground(new Color(0x242F3D));
        return tableScrollPane;
    }

    public synchronized void render() {
        prepareCollection();
        tableModel.setRowCount(collection.size());
        int i = 0;

        for(var x : collection) {
            ArrayList<Object> row = new ArrayList<>();
            renderRow(tableModel, tree, x.getValues(), row, 0, i);
            i++;
        }
    }

    private void prepareCollection() {
        collection = new ArrayList<>();
        collection = rowCollection.stream().filter(x -> {
            for(var predicate : predicates) {
                if(!predicate.test(x))
                    return false;
            }
            return true;
        }).collect(Collectors.toList());
        comparators.forEach(x -> collection = collection.stream().sorted(x).collect(Collectors.toList()));
    }

    public int renderRow(DefaultTableModel tableModel, ModelTree tree, Map<String, Object> values, ArrayList<Object> row, int col, int rowc) {
        for(var field : tree.getFields()) {
            if(!field.isPrimitive()) {
                col = renderRow(tableModel, field, ((Model)values.get(field.getName())).getValues(), row, col, rowc);
                continue;
            }
            row.add(values.get(field.getName()));
            tableModel.setValueAt(row.get(col), rowc, col);
            col++;
        }
        return col;
    }

    public void addColumns(ModelTree tree, DefaultTableModel table, String pref) {
        for(var field : tree.getFields()) {
            if(!field.isPrimitive()) {
                addColumns(field, table, pref + field.getName() + ".");
                continue;
            }

            table.addColumn(pref + field.getName());
        }
    }

    @Override
    public synchronized void update(List<Pair<Model, Event>> events) {
        if(events.size() == 0) {
            return;
        }
        for(var event : events) {
            switch (event.getRight()) {
                case ADD -> rowCollection.add(event.getLeft());
                case DELETE -> rowCollection.removeIf(x -> x.getId() == event.getLeft().getId());
                case UPDATE -> {
                    rowCollection.removeIf(x -> x.getId() == event.getLeft().getId());
                    rowCollection.add(event.getLeft());
                }
            }
        }

        if(events.size() > 0)
            SwingUtilities.invokeLater(this::render);
    }

    private List<Pair<Model, Event>> checkEvents(List<Pair<Model, Event>> events) {
        List<Pair<Model, Event>> allowedEvents = new ArrayList<>();
        for(var event : events) {
            boolean good = true;
            for(var predicate : predicates) {
                if(!predicate.test(event.getLeft())) {
                    good = false;
                }
            }
            if(good)
                allowedEvents.add(event);
        }
        return allowedEvents;
    }

    @Override
    public synchronized void setCollection(Collection<Model> collection) {
        this.rowCollection = collection;
        SwingUtilities.invokeLater(this::render);
    }

    public void deletePredicate() {
        predicates = new ArrayList<>();
        SwingUtilities.invokeLater(this::render);
    }

    public void deleteComparator() {
        comparators = new ArrayList<>();
        SwingUtilities.invokeLater(this::render);
    }

    public <T> void addPredicate(String path, Class<T> type, T value, FilterTypes filterTypes) {
        predicates.add(model -> {
            String[] nodes = path.split("\\.");
            HashMap<String, Object> values = model.getValues();
            for (int i = 0; i < nodes.length-1; i++) {
                values = ((Model)values.get(nodes[i])).getValues();
            }
            var value1 = Converter.convert(type, values.get(nodes[nodes.length - 1]).toString());

            switch (filterTypes) {
                case EQUAL -> {
                    return ((Comparable<T>) value).compareTo(value1) == 0;
                }
                case GREATER -> {
                    return ((Comparable<T>) value).compareTo(value1) < 0;
                }
                case LESS -> {
                    return ((Comparable<T>) value).compareTo(value1) > 0;
                }
            }
            return false;
        });
        SwingUtilities.invokeLater(this::render);
    }

    public <T> void addComparator(String path, Class<T> type, SortTypes sortTypes) {
        comparators.add(new Comparator<Model>() {
            @Override
            public int compare(Model o1, Model o2) {
                var o1Value = getValue(path, type, o1);
                var o2Value = getValue(path, type, o2);
                switch (sortTypes) {
                    case GREATER -> {return ((Comparable<T>) o1Value).compareTo(o2Value);}
                    case LESS -> {return (-1* ((Comparable<T>) o1Value).compareTo(o2Value));}
                }
                return 0;
            }

            private <T> T getValue(String path, Class<T> type, Model model) {
                String[] nodes = path.split("\\.");
                HashMap<String, Object> values = model.getValues();
                for (int i = 0; i < nodes.length-1; i++) {
                    values = ((Model)values.get(nodes[i])).getValues();
                }
                return Converter.convert(type, values.get(nodes[nodes.length - 1]).toString());
            }
        });
        SwingUtilities.invokeLater(this::render);
    }

    public JTable getDataTable() {
        return dataTable;
    }
}
