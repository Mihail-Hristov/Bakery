package bakery.repositories;

import bakery.entities.bakedFoods.BaseFood;
import bakery.entities.bakedFoods.interfaces.BakedFood;
import bakery.entities.tables.BaseTable;
import bakery.repositories.interfaces.FoodRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FoodRepositoryImpl<T extends BakedFood> extends Repository<T> implements FoodRepository<T> {

    public FoodRepositoryImpl() {
        super();
    }

    @Override
    public T getByName(String name) {
        T food = null;
        for (T f: this.getAll()) {
            if (f.getName().equals(name)) {
                food = f;
            }
        }
        return food;
    }
}
