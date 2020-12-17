package bakery.repositories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Repository<T> implements bakery.repositories.interfaces.Repository<T> {
    private List<T> models;

    protected Repository() {
        this.models = new ArrayList<>();
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
