package server.database;

import common.models.Model;
import common.utils.Converter;
import common.utils.ModelTree;
import server.main.Main;
import server.utils.input.Loader;

import java.sql.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class DatabaseManager<E extends Model> implements Loader<E> {
    private ModelTree tree;
    private Logger logger;
    private HashMap<String, String> statements = new HashMap<>();
    private String selectStatement;
    private HashMap<String, String> insertStatements = new HashMap<>();
    private HashMap<String, String> updateStatements = new HashMap<>();

    public DatabaseManager(ModelTree tree) {
        this.tree = tree;
        generateSelectStatements(tree);
        generateInsertStatements(tree);
        generateUpdateStatements(tree);
    }

    public int add(Model model) {
        try (var connection = ConnectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            int id = add(model, connection, tree);
            connection.commit();
            return id;
        } catch (SQLException e) {
            Main.logger.warn(e.getMessage());
        }
        return -1;
    }

    public boolean update(Model model) {
        try (var connection = ConnectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            HashMap<String, Object> values = model.getValues();
            String statement = statements.get(tree.getName());
            PreparedStatement preparedStatement = connection.prepareStatement(updateStatements.get(tree.getName()));
            update(values, preparedStatement, connection, tree, model.getId());
            preparedStatement.executeUpdate();
            connection.commit();
            model.init(values);
            return true;
        } catch (SQLException e) {
            Main.logger.warn(e.getMessage());
        }
        return false;
    }

    public boolean delete(Set<Integer> ids) {
        try (var connection = ConnectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            String statement = "delete from " + tree.getName() + " where id in ";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            int cnt = 0;
            for(var id : ids) {
                if(cnt > 0)
                    statement += " , ";
                statement += id;
                cnt++;
            }
            preparedStatement.execute();
            connection.commit();
            return true;
        } catch (SQLException e) {
            Main.logger.warn(e.getMessage());
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private void update(HashMap<String, Object> values, PreparedStatement statement,Connection connection, ModelTree tree, int id) throws SQLException {
        int i = 1;
        ResultSet resultSet = connection.createStatement().executeQuery("select * from " + tree.getType().getSimpleName() + " where id = " + id);
        resultSet.next();
        for(var field : tree.getFields()) {
            if(field.isAutoGenerated()) {
                continue;
            }
            if(field.isPrimitive()) {
                addValue(statement, field.getType(), values.get(field.getName()), i);
                i++;
            } else {
                int refId = resultSet.getInt(field.getName()+"_id");
                PreparedStatement preparedStatement = connection.prepareStatement(updateStatements.get(field.getName()));
                update(((Model) values.get(field.getName())).getValues(), preparedStatement, connection, field, refId);
                preparedStatement.executeUpdate();
            }
        }
        addValue(statement, int.class, id, i);
    }

    private int add(Model model, Connection connection, ModelTree tree) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(insertStatements.get(tree.getName()), PreparedStatement.RETURN_GENERATED_KEYS);
        int i = 1;
        HashMap<String, Object> values = model.getValues();
        for(var field : tree.getFields()) {
            if(field.isAutoGenerated()) {
                continue;
            }
            if(field.isPrimitive()) {
                addValue(statement, field.getType(), values.get(field.getName()), i);
            } else {
                int id = add((Model) values.get(field.getName()), connection, field);
                addValue(statement, int.class, id, i);
            }
            i++;
        }
        statement.execute();
        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        return resultSet.getInt(resultSet.findColumn("id"));
    }

    private void addValue(PreparedStatement statement, Class<?> type, Object value, int i) throws SQLException {
        if(type.isEnum()) {
            statement.setObject(i, value, Types.OTHER);
            return;
        }
        switch (type.getSimpleName()) {
            case "int", "Integer" -> {
                statement.setInt(i, (int)value);
            }
            case "long", "Long" -> {
                statement.setLong(i, (long)value);
            }
            case "Date" -> {
                statement.setDate(i, (Date) value);
            }
            case "double", "Double" -> {
                statement.setDouble(i, (double)value);
            }
            case "float", "Float" -> {
                statement.setFloat(i, (float)value);
            }
            case "String" -> {
                statement.setString(i, (String) value);
            }
        }
    }

    private void generateInsertStatements(ModelTree tree) {
        StringBuilder statement = new StringBuilder("insert into " + tree.getType().getSimpleName() + " (");
        int cnt = 0;
        for(var field : tree.getFields()) {
            if(field.isAutoGenerated()) {
                continue;
            }
            if(cnt != 0) {
                statement.append(", ");
            }
            if(field.isPrimitive()) {
                statement.append(field.getName());
            }
            if(!field.isPrimitive()) {
                statement.append(field.getName()).append("_id");
                generateInsertStatements(field);
            }
            cnt++;
        }
        statement.append(") values(");
        for(int i = 0; i < cnt; i++) {
            statement.append("?");
            if(i != cnt - 1) {
                statement.append(", ");
            }
        }
        statement.append(")");
        insertStatements.put(tree.getName(), statement.toString());
    }

    private void generateUpdateStatements(ModelTree tree) {
        StringBuilder statement = new StringBuilder("update " + tree.getType().getSimpleName() + " set ");
        int cnt = 0;
        for(var field : tree.getFields()) {
            if(field.isAutoGenerated()) {
                continue;
            }
            if(field.isPrimitive()) {
                if(cnt != 0) {
                    statement.append(", ");
                }
                statement.append(field.getName()).append(" = ?");
                cnt++;
            }
            if(!field.isPrimitive()) {
                generateUpdateStatements(field);
            }
        }
        statement.append(" where id = ? ");
        updateStatements.put(tree.getName(), statement.toString());
    }

    private void generateSelectStatements(ModelTree tree) {
        StringBuilder statement = new StringBuilder("select * from " + tree.getType().getSimpleName());
        for(var field : tree.getFields()) {
            if(!field.isPrimitive()) {
                generateSelectStatements(field);
            }
        }

        if(tree != this.tree) {
            statement.append(" where id = ? ");
        }
        statements.put(tree.getType().getSimpleName(), statement.toString());
    }

    @Override
    public <T extends Collection<E>> void load(T collection) {
        try (var connection = ConnectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(statements.get(tree.getType().getSimpleName()));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                collection.add(loadModel(resultSet, tree, connection));
            }
        } catch (SQLException e) {
            Main.logger.warn(e.getMessage());
        }
    }

    /**
     * Method that  build fully initialized and validated model object from Json file
     * @param tree model tree
     * @param <T> element type
     */
    @SuppressWarnings("unchecked")
    private <T extends Model> T loadModel(ResultSet resultSet, ModelTree tree, Connection connection) throws SQLException {
        T obj = (T) tree.constructor.get();
        HashMap<String, Object> values = new HashMap<>();
        for(var field : tree.getFields()) {
            if(field.isPrimitive()) {
                if(field.isEnum())
                    values.put(field.getName(), field.getEnumConstants().get(resultSet.getObject(field.getName()).toString()));
                else
                    values.put(field.getName(),Converter.convert(field.getType(), resultSet.getObject(field.getName()).toString()));
            } else {
                PreparedStatement statement = connection.prepareStatement(statements.get(field.getType().getSimpleName()));
                statement.setInt(1, resultSet.getInt(field.getName()+"_id"));
                ResultSet fieldSet = statement.executeQuery();
                fieldSet.next();
                values.put(field.getName(), loadModel(fieldSet, field, connection));
            }
        }
        obj.init(values);
        return obj;
    }
}
