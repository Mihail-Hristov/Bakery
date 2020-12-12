package bakery.repositories;

import bakery.entities.tables.interfaces.Table;
import bakery.repositories.interfaces.Repository;
import bakery.repositories.interfaces.TableRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TableRepositoryImpl<T extends Table> implements TableRepository<T> {
    private List<T> models;

    public TableRepositoryImpl() {
        this.models = new ArrayList<>();
    }

    @Override
    public T getByNumber(int number) {
        T table = null;
        for (T t: models) {
            if (t.getTableNumber() == number) {
                table = t;
            }
        }
        return table;
    }

    @Override
    public Collection<T> getAll() {
        return this.models;
    }

    @Override
    public void add(T t) {
        this.models.add(t);
    }
}
