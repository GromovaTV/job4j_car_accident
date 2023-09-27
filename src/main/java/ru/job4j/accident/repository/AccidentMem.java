package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private static AtomicInteger ACCIDENT_ID = new AtomicInteger(0);
    private static AtomicInteger TYPE_ID = new AtomicInteger(0);
    private HashMap<Integer, Accident> accidents = new HashMap<>();
    private HashMap<Integer, AccidentType> types = new HashMap<>();

    public AccidentMem() {
        save(AccidentType.of(0, "Две машины"));
        save(AccidentType.of(0, "Машина и человек"));
        save(AccidentType.of(0, "Машина и велосипед"));
        save(Accident.of("А275ВВ", "Пересек двойную сплошную",
                "Москва, ул. Ленина, д.2", getType(1)));
        save(Accident.of("Т825ВХ", "Проехал на красный свет",
                "Москва, ул. Вавилова, д.20", getType(2)));
    }

    public void save(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(ACCIDENT_ID.incrementAndGet());
        }
        accidents.put(accident.getId(), accident);
    }

    public void save(AccidentType type) {
        if (type.getId() == 0) {
            type.setId(TYPE_ID.incrementAndGet());
        }
        types.put(type.getId(), type);
    }

    public Accident get(int id) {
        return accidents.get(id);
    }

    public AccidentType getType(int id) {
        return types.get(id);
    }

    public Collection<Accident> getAll() {
        return accidents.values();
    }

    public Collection<AccidentType> getAllTypes() {
        return types.values();
    }

    private Accident replace(Accident accident) {
        return accidents.replace(accident.getId(), accident);
    }
}
