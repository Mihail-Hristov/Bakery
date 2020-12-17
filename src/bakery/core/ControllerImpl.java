package bakery.core;

import bakery.common.ExceptionMessages;
import bakery.common.OutputMessages;
import bakery.core.interfaces.Controller;
import bakery.entities.bakedFoods.Bread;
import bakery.entities.bakedFoods.Cake;
import bakery.entities.bakedFoods.interfaces.BakedFood;
import bakery.entities.drinks.Tea;
import bakery.entities.drinks.Water;
import bakery.entities.drinks.interfaces.Drink;
import bakery.entities.tables.InsideTable;
import bakery.entities.tables.OutsideTable;
import bakery.entities.tables.interfaces.Table;
import bakery.repositories.FoodRepositoryImpl;
import bakery.repositories.interfaces.*;

import java.util.List;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private FoodRepository<BakedFood> foodRepository;
    private DrinkRepository<Drink> drinkRepository;
    private TableRepository<Table> tableTableRepository;
    private double totalIncome;

    public ControllerImpl(FoodRepository<BakedFood> foodRepository, DrinkRepository<Drink> drinkRepository, TableRepository<Table> tableRepository) {
        this.foodRepository = foodRepository;
        this.drinkRepository = drinkRepository;
        this.tableTableRepository = tableRepository;
        this.totalIncome = 0;
    }


    @Override
    public String addFood(String type, String name, double price) {

        if (foodRepository.getByName(name) != null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.FOOD_OR_DRINK_EXIST, type, name));
        }

        BakedFood food = null;

        switch (type) {
            case "Bread":
                food = new Bread(name, price);
                break;
            case "Cake":
                food = new Cake(name, price);
                break;
        }


        this.foodRepository.add(food);
        return String.format(OutputMessages.FOOD_ADDED, name, type);
    }

    @Override
    public String addDrink(String type, String name, int portion, String brand) {
        if (drinkRepository.getByNameAndBrand(name, brand) != null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.FOOD_OR_DRINK_EXIST, type, name));
        }

        Drink drink = null;

        switch (type) {
            case "Tea":
                drink = new Tea(name, portion, brand);
                break;
            case "Water":
                drink = new Water(name, portion, brand);
                break;
        }


        this.drinkRepository.add(drink);

        return String.format(OutputMessages.DRINK_ADDED, name, brand);
    }

    @Override
    public String addTable(String type, int tableNumber, int capacity) {
        if (tableTableRepository.getByNumber(tableNumber) != null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.TABLE_EXIST, tableNumber));
        }

        Table table = null;

        switch (type) {
            case "InsideTable":
                table = new InsideTable(tableNumber, capacity);
                break;
            case "OutsideTable":
                table = new OutsideTable(tableNumber, capacity);
                break;
        }

        this.tableTableRepository.add(table);

        return String.format(OutputMessages.TABLE_ADDED, tableNumber);
    }

    @Override
    public String reserveTable(int numberOfPeople) {
        Table currentTable = null;

        for (Table t : tableTableRepository.getAll()) {
            if (!t.isReserved() && t.getCapacity() >= numberOfPeople) {
                currentTable = t;
                break;
            }
        }

        if (currentTable == null) {
            return String.format(OutputMessages.RESERVATION_NOT_POSSIBLE, numberOfPeople);
        }

        currentTable.reserve(numberOfPeople);
        return String.format(OutputMessages.TABLE_RESERVED, currentTable.getTableNumber(), numberOfPeople);
    }

    @Override
    public String orderFood(int tableNumber, String foodName) {
        Table table = tableTableRepository.getByNumber(tableNumber);
        BakedFood food = foodRepository.getByName(foodName);

        if (table == null || !table.isReserved()) {
            return String.format(OutputMessages.WRONG_TABLE_NUMBER, tableNumber);
        }

        if (food == null) {
            return String.format(OutputMessages.NONE_EXISTENT_FOOD, foodName);
        }

        table.orderFood(food);
        return String.format(OutputMessages.FOOD_ORDER_SUCCESSFUL, tableNumber, foodName);
    }

    @Override
    public String orderDrink(int tableNumber, String drinkName, String drinkBrand) {
        Table table = tableTableRepository.getByNumber(tableNumber);
        Drink drink = drinkRepository.getByNameAndBrand(drinkName, drinkBrand);

        if (table == null || !table.isReserved()) {
            return String.format(OutputMessages.WRONG_TABLE_NUMBER, tableNumber);
        }

        if (drink == null) {
            return String.format(OutputMessages.NON_EXISTENT_DRINK, drinkName, drinkBrand);
        }

        table.orderDrink(drink);
        return String.format(OutputMessages.DRINK_ORDER_SUCCESSFUL,tableNumber, drinkName, drinkBrand);

    }

    @Override
    public String leaveTable(int tableNumber) {
        Table table = tableTableRepository.getByNumber(tableNumber);
        double bill = table.getBill();
        this.totalIncome += bill;
        table.clear();

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Table: %d", tableNumber)).append(System.lineSeparator());
        sb.append(String.format("Bill: %.2f", bill));

        return sb.toString().trim();
    }

    @Override
    public String getFreeTablesInfo() {
        List<Table> notReserved = tableTableRepository.getAll().stream().filter(t -> !t.isReserved()).collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();
        for (Table table : notReserved) {
            sb.append(table.getFreeTableInfo());
            sb.append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

    @Override
    public String getTotalIncome() {

        return String.format("Total income: %.2flv", this.totalIncome);
    }
}
