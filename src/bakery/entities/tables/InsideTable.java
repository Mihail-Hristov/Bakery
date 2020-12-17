package bakery.entities.tables;

public class InsideTable extends BaseTable{
    private static final double pricePerPerson = 2.5;

    public InsideTable(int tableNumber, int capacity) {
        super(tableNumber, capacity, pricePerPerson);
    }


}
