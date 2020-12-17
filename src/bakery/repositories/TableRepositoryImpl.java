package bakery.repositories;

import bakery.entities.tables.BaseTable;
import bakery.entities.tables.interfaces.Table;
import bakery.repositories.interfaces.TableRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TableRepositoryImpl<T extends Table> extends Repository<T> implements TableRepository<T> {

    public TableRepositoryImpl() {
        super();
    }

    @Override
    public T getByNumber(int number) {
        T table = null;
        for (T t: this.getAll()) {
            if (t.getTableNumber() == number) {
                table = t;
            }
        }
        return table;
    }

}
