package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.repository.AccidentMem;
import java.util.Collection;

@Service
public class AccidentService {
    private AccidentMem accidentMem = new AccidentMem();

    public Collection<Accident> getAll() {
        return accidentMem.getAll();
    }

    public Accident get(int id) {
        return accidentMem.get(id);
    }

    public void save(Accident accident) {
        accidentMem.save(accident);
    }
}
