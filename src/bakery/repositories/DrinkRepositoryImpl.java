package bakery.repositories;

import bakery.entities.drinks.interfaces.Drink;
import bakery.repositories.interfaces.DrinkRepository;
import bakery.repositories.interfaces.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DrinkRepositoryImpl<T extends Drink> implements DrinkRepository<T> {
    private List<T> models;

    public DrinkRepositoryImpl() {
        this.models = new ArrayList<>();
    }

    @Override
    public T getByNameAndBrand(String drinkName, String drinkBrand) {
        T drink = null;
        for (T d: this.models) {
            if (d.getName().equals(drinkName) && d.getBrand().equals(drinkBrand)) {
                drink = d;
            }
        }
        return drink;
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
