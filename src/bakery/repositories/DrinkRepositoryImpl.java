package bakery.repositories;

import bakery.entities.drinks.BaseDrink;
import bakery.entities.drinks.interfaces.Drink;
import bakery.repositories.interfaces.DrinkRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DrinkRepositoryImpl<T extends Drink> extends Repository<T> implements DrinkRepository<T> {

    public DrinkRepositoryImpl() {
        super();
    }

    @Override
    public T getByNameAndBrand(String drinkName, String drinkBrand) {
        T drink = null;
        for (T d: this.getAll()) {
            if (d.getName().equals(drinkName) && d.getBrand().equals(drinkBrand)) {
                drink = d;
            }
        }
        return drink;
    }
}
