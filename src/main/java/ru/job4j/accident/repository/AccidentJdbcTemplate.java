package ru.job4j.accident.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbc;

    public AccidentJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Accident> accidentRowMapper = (resultSet, rowNum) -> {
        Accident accident = new Accident();
        accident.setId(resultSet.getInt("aId"));
        accident.setName(resultSet.getString("aName"));
        accident.setText(resultSet.getString("aText"));
        accident.setAddress(resultSet.getString("aAddress"));
        AccidentType type = new AccidentType();
        type.setId(resultSet.getInt("tId"));
        type.setName(resultSet.getString("tName"));
        accident.setType(type);
        Set<Rule> rules = new HashSet<>();
        do {
            String ruleName = resultSet.getString("rName");
            int ruleId = resultSet.getInt("rId");
            if (ruleName != null) {
                Rule rule = new Rule();
                rule.setId(ruleId);
                rule.setName(ruleName);
                rules.add(rule);
            }
        } while (resultSet.next() && resultSet.getInt("aId") == accident.getId());
        accident.setRules(rules);
        return accident;
    };

    private final RowMapper<AccidentType> typeRowMapper = (resultSet, rowNum) -> {
        AccidentType type = new AccidentType();
        type.setId(resultSet.getInt("tId"));
        type.setName(resultSet.getString("tName"));
        return type;
    };

    private final RowMapper<Rule> ruleRowMapper = (resultSet, rowNum) -> {
        Rule rule = new Rule();
        rule.setId(resultSet.getInt("rId"));
        rule.setName(resultSet.getString("rName"));
        return rule;
    };

    public Accident add(Accident accident) {
        int generatedId = jdbc.update("insert into accident (name, text, address, accident_type_id) " +
                        "values (?, ?, ?, ?)",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId());
        accident.setId(generatedId);
        for (Rule rule : accident.getRules()) {
            jdbc.update("insert into accident_rule (accident_id, rule_id) " +
                            "values (?, ?)", generatedId, rule.getId());
        }
        return accident;
    }

    public AccidentType add(AccidentType type) {
        int generatedId = jdbc.update("insert into accident_type (name) " +
                        "values (?)",
                type.getName());
        type.setId(generatedId);
        return type;
    }

    public Rule add(Rule rule) {
        int generatedId = jdbc.update("insert into rule (name) " +
                        "values (?)",
                rule.getName());
        rule.setId(generatedId);
        return rule;
    }

    public AccidentType update(AccidentType type) {
        jdbc.update("update accident_type set name = ? where id = ?",
                type.getName(),
                type.getId());
        return type;
    }

    public Rule update(Rule rule) {
        jdbc.update("update rule set name = ? where id = ?",
                rule.getName(),
                rule.getId());
        return rule;
    }

    public Accident update(Accident accident) {
        jdbc.update("update accident set name = ?, text = ?, address = ?, accident_type_id = ? " +
                        "where id = ?",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId());
        jdbc.update("delete from accident_rule where accident_id = ?", accident.getId());
        if (accident.getRules() != null) {
            for (Rule rule : accident.getRules()) {
                jdbc.update("insert into accident_rule (accident_id, rule_id) " +
                        "values (?, ?)", accident.getId(), rule.getId());
            }
        }
        return accident;
    }

    public AccidentType save(AccidentType type) {
        if (type.getId() == 0) {
            return add(type);
        } else {
            return update(type);
        }
    }

    public Rule save(Rule rule) {
        if (rule.getId() == 0) {
            return add(rule);
        } else {
            return update(rule);
        }
    }

    public Accident save(Accident accident) {
        if (accident.getId() == 0) {
            return add(accident);
        } else {
            return update(accident);
        }
    }

    public List<Accident> getAll() {
        List<Accident> res = jdbc.query(
                "select a.id as aId, a.name as aName, a.text as aText, a.address as aAddress, " +
                        "at.name as tName, at.id as tId, r.id as rId, r.name as rName\n" +
                        "from accident a \n" +
                        "JOIN accident_type at ON a.accident_type_id = at.id \n" +
                        "LEFT JOIN accident_rule ar ON a.id = ar.accident_id \n" +
                        "LEFT JOIN rule r ON ar.rule_id = r.id\n",
                accidentRowMapper);
        return res;
    }

    public List<AccidentType> getAllTypes() {
        return jdbc.query("select id tId, name tName from accident_type", typeRowMapper);
    }

    public List<Rule> getAllRules() {
        return jdbc.query("select id rId, name rName from rule", ruleRowMapper);
    }

    public Accident get(int id) {
        Accident res = jdbc.queryForObject(
                "select a.id as aId, a.name as aName, a.text as aText, a.address as aAddress, " +
                        "at.name as tName, at.id as tId, r.id as rId, r.name as rName " +
                        "from accident a " +
                        "JOIN accident_type at ON a.accident_type_id = at.id " +
                        "LEFT JOIN accident_rule ar ON a.id = ar.accident_id " +
                        "LEFT JOIN rule r ON ar.rule_id = r.id " +
                        "where a.id = ?",
                accidentRowMapper, id);

        return res;
    }

    public AccidentType getType(int id) {
        return jdbc.queryForObject("select id tId, name tName from accident_type where id = ?",
                typeRowMapper, id);
    }

    public Rule getRule(int id) {
        return jdbc.queryForObject("select id rId, name rName from rule where id = ?",
                ruleRowMapper, id);
    }

    public void delete(AccidentType type) {
        jdbc.update("delete from accident_type where id = ?",
                type.getId());
    }

    public void delete(Rule rule) {
        jdbc.update("delete from rule where id = ?",
                rule.getId());
    }

    public void delete(Accident accident) {
        for (Rule rule : accident.getRules()) {
            jdbc.update("delete from accident_rule where  accident_id = ?", accident.getId());
        }
        jdbc.update("delete from accident  where id = ?", accident.getId());
    }
}