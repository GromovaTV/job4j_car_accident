package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.repository.AccidentMem;
import java.util.Collection;

@Service
public class AccidentService {
    private AccidentMem accidentMem;

    public AccidentService(AccidentMem accidentMem) {
        this.accidentMem = accidentMem;
    }

    public Collection<Accident> getAll() {
        return accidentMem.getAll();
    }

    public Collection<AccidentType> getAllTypes() {
        return accidentMem.getAllTypes();
    }

    public Accident get(int id) {
        return accidentMem.get(id);
    }

    public AccidentType getType(int id) {
        return accidentMem.getType(id);
    }

    public void save(Accident accident) {
        accidentMem.save(accident);
    }

    public void saveType(AccidentType type) {
        accidentMem.save(type);
    }
}
