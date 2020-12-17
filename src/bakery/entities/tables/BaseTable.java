package bakery.entities.tables;

import bakery.common.ExceptionMessages;
import bakery.entities.bakedFoods.interfaces.BakedFood;
import bakery.entities.drinks.interfaces.Drink;
import bakery.entities.tables.interfaces.Table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseTable implements Table {
    private Collection<BakedFood> foodOrders;
    private Collection<Drink> drinkOrders;
    private int tableNumber;
    private int capacity;
    private int numberOfPeople;
    private double pricePerPerson;
    private boolean isReserved;
    private double price;

    protected BaseTable(int tableNumber, int capacity, double pricePerPerson) {
        this.foodOrders = new ArrayList<>();
        this.drinkOrders = new ArrayList<>();
        this.setTableNumber(tableNumber);
        this.setCapacity(capacity);
        this.setPricePerPerson(pricePerPerson);
        this.isReserved = false;
    }


    private void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    private void setCapacity(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_TABLE_CAPACITY);
        }

        this.capacity = capacity;
    }

    private void setNumberOfPeople(int numberOfPeople) {
        if (numberOfPeople <= 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_NUMBER_OF_PEOPLE);
        }

        this.numberOfPeople = numberOfPeople;
    }

    private void setPricePerPerson(double pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    private void setPrice(int numberOfPeople) {
        this.price = this.pricePerPerson * numberOfPeople;
    }


    @Override
    public int getTableNumber() {
        return this.tableNumber;
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public int getNumberOfPeople() {
        return this.numberOfPeople;
    }

    @Override
    public double getPricePerPerson() {
        return this.pricePerPerson;
    }

    @Override
    public boolean isReserved() {
        return this.isReserved;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public void reserve(int numberOfPeople) {
        this.setNumberOfPeople(numberOfPeople);
        this.price = numberOfPeople * this.getPricePerPerson();
        this.isReserved = true;
    }

    @Override
    public void orderFood(BakedFood food) {
        this.foodOrders.add(food);
    }

    @Override
    public void orderDrink(Drink drink) {
        this.drinkOrders.add(drink);
    }

    @Override
    public double getBill() {
        double bill = 0;
        for (BakedFood food : foodOrders) {
            bill += food.getPrice();
        }

        for (Drink drink : drinkOrders) {
            bill += drink.getPrice();
        }

        bill += this.getPrice();

        return bill;
    }

    @Override
    public void clear() {
        this.drinkOrders.clear();
        this.foodOrders.clear();
        this.isReserved = false;
        this.price = 0;
        this.numberOfPeople = 0;
    }

    @Override
    public String getFreeTableInfo() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("Table: %d", this.getTableNumber())).append(System.lineSeparator());
        result.append(String.format("Type: %s", this.getClass().getSimpleName())).append(System.lineSeparator());
        result.append(String.format("Capacity: %d",this.getCapacity())).append(System.lineSeparator());
        result.append(String.format("Price per Person: %.2f", this.getPricePerPerson()));

        return result.toString().trim();
    }
}

