package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private static AtomicInteger ACCIDENT_ID = new AtomicInteger(0);
    private HashMap<Integer, Accident> accidents = new HashMap<>();

    public AccidentMem() {
        save(Accident.of("А275ВВ", "Пересек двойную сплошную", "Москва, ул. Ленина, д.2"));
        save(Accident.of("Т825ВХ", "Проехал на красный вет", "Москва, ул. Вавилова, д.20"));
    }

    public void save(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(ACCIDENT_ID.incrementAndGet());
        }
        accidents.put(accident.getId(), accident);
    }

    public Accident get(int id) {
        return accidents.get(id);
    }

    public Collection<Accident> getAll() {
        return accidents.values();
    }

    private Accident replace(Accident accident) {
        return accidents.replace(accident.getId(), accident);
    }
}
