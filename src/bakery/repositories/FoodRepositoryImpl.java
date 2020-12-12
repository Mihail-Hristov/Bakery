package bakery.repositories;

import bakery.entities.bakedFoods.interfaces.BakedFood;
import bakery.repositories.interfaces.FoodRepository;
import bakery.repositories.interfaces.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FoodRepositoryImpl<T extends BakedFood> implements FoodRepository<T> {
    private List<T> models;

    public FoodRepositoryImpl() {
        this.models = new ArrayList<>();
    }

    @Override
    public T getByName(String name) {
        T food = null;
        for (T f: models) {
            if (f.getName().equals(name)) {
                food = f;
            }
        }
        return food;
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
