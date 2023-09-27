package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentMem;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Collection<Rule> getAllRules() {
        return accidentMem.getAllRules();
    }

    public Accident get(int id) {
        return accidentMem.get(id);
    }

    public AccidentType getType(int id) {
        return accidentMem.getType(id);
    }

    public Rule getRule(int id) {
        return accidentMem.getRule(id);
    }

    public void save(Accident accident) {
        accidentMem.save(accident);
    }

    public void save(Accident accident, HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("type_id"));
        accident.setType(accidentMem.getType(id));
        String[] ids = req.getParameterValues("rIds");
        Set<Rule> rules = Arrays.stream(ids)
                .map(i -> accidentMem.getRule(Integer.parseInt(i)))
                .collect(Collectors.toSet());
        accident.setRules(rules);
        accidentMem.save(accident);
    }

    public void saveType(AccidentType type) {
        accidentMem.save(type);
    }

    public void saveRule(Rule rule) {
        accidentMem.save(rule);
    }
}
