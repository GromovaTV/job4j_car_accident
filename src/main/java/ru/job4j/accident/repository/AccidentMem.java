package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {

    private static AtomicInteger accidentId = new AtomicInteger(0);
    private static AtomicInteger typeId = new AtomicInteger(0);
    private static AtomicInteger ruleId = new AtomicInteger(0);
    private HashMap<Integer, Accident> accidents = new HashMap<>();
    private HashMap<Integer, AccidentType> types = new HashMap<>();
    private HashMap<Integer, Rule> rules = new HashMap<>();

    public AccidentMem() {
        save(Rule.of(0, "Статья 1"));
        save(Rule.of(0, "Статья 2"));
        save(Rule.of(0, "Статья 3"));
        Set<Rule> set1 = Set.of(getRule(1), getRule(2));
        Set<Rule> set2 = Set.of(getRule(2), getRule(3));
        save(AccidentType.of(0, "Две машины"));
        save(AccidentType.of(0, "Машина и человек"));
        save(AccidentType.of(0, "Машина и велосипед"));
        save(Accident.of("А275ВВ", "Пересек двойную сплошную",
                "Москва, ул. Ленина, д.2", getType(1), set1));
        save(Accident.of("Т825ВХ", "Проехал на красный свет",
                "Москва, ул. Вавилова, д.20", getType(2), set2));
    }

    public void save(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(accidentId.incrementAndGet());
        }
        accidents.put(accident.getId(), accident);
    }

    public void save(AccidentType type) {
        if (type.getId() == 0) {
            type.setId(typeId.incrementAndGet());
        }
        types.put(type.getId(), type);
    }

    public void save(Rule rule) {
        if (rule.getId() == 0) {
            rule.setId(ruleId.incrementAndGet());
        }
        rules.put(rule.getId(), rule);
    }

    public Accident get(int id) {
        return accidents.get(id);
    }

    public AccidentType getType(int id) {
        return types.get(id);
    }

    public Rule getRule(int id) {
        return rules.get(id);
    }

    public Collection<Accident> getAll() {
        return accidents.values();
    }

    public Collection<AccidentType> getAllTypes() {
        return types.values();
    }

    public Collection<Rule> getAllRules() {
        return rules.values();
    }

    private Accident replace(Accident accident) {
        return accidents.replace(accident.getId(), accident);
    }
}
