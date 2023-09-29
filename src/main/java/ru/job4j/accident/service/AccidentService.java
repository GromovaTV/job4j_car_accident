package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentHibernate;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccidentService {
    private AccidentHibernate template;

    public AccidentService(AccidentHibernate template) {
        this.template = template;
    }

    public Collection<Accident> getAll() {
        return template.getAll();
    }

    public Collection<AccidentType> getAllTypes() {
        return template.getAllTypes();
    }

    public Collection<Rule> getAllRules() {
        return template.getAllRules();
    }

    public Accident get(int id) {
        return template.get(id);
    }

    public AccidentType getType(int id) {
        return template.getType(id);
    }

    public Rule getRule(int id) {
        return template.getRule(id);
    }

    public void save(Accident accident) {
        template.save(accident);
    }

    public void save(Accident accident, HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("type_id"));
        accident.setType(template.getType(id));
        String[] ids = req.getParameterValues("rIds");
        if (ids != null) {
            Set<Rule> rules = Arrays.stream(ids)
                    .map(i -> template.getRule(Integer.parseInt(i)))
                    .collect(Collectors.toSet());
            accident.setRules(rules);
        }
        template.save(accident);
    }

    public void saveType(AccidentType type) {
        template.save(type);
    }

    public void saveRule(Rule rule) {
        template.save(rule);
    }
}
