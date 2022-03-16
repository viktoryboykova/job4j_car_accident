package ru.job4j.accident.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbc;

    private final RowMapper<Accident> accidentRowMapper = (rs, row) -> {
        Accident accident = new Accident();
        accident.setId(rs.getInt("id"));
        accident.setName(rs.getString("name"));
        accident.setText(rs.getString("text"));
        accident.setAddress(rs.getString("address"));
        accident.setType(findTypeById(rs.getInt("type_id")));
        Set<Rule> rules = findRulesByAccident_id(rs.getInt("id"));
        accident.setRules(rules);
        return accident;
    };

    private final RowMapper<AccidentType> accidentTypeRowMapper = (rs, row) -> {
        AccidentType accidentType = new AccidentType();
        accidentType.setId(rs.getInt("id"));
        accidentType.setName(rs.getString("name"));
        return accidentType;
    };

    private final RowMapper<Rule> ruleRowMapper = (rs, row) -> {
        Rule rule = new Rule();
        rule.setId(rs.getInt("id"));
        rule.setName(rs.getString("name"));
        return rule;
    };

    public AccidentJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Accident save(Accident accident, String[] ids) {
        if (accident.getId() == 0) {
            create(accident, ids);
        } else {
            update(accident, ids);
        }
        return accident;
    }

    private void create(Accident accident, String[] ids) {
        jdbc.update("insert into accidents (name, text, address, type_id) values (?, ?, ?, ?)",
                accident.getName(), accident.getText(), accident.getAddress(), accident.getType().getId());
        saveAccidentRules(accident, ids);
    }

    private void update(Accident accident, String[] ids) {
        deleteAccidentRules(accident.getId());
        jdbc.update("update accidents set name = ?, text = ?, address = ?, type_id = ? where id = ?",
                accident.getName(), accident.getText(),
                accident.getAddress(), accident.getType().getId(), accident.getId());
        saveAccidentRules(accident, ids);
    }

    public List<Accident> getAllAccidents() {
        return jdbc.query("select id, name, text, address, type_id from accidents",
                accidentRowMapper);
    }

    public Accident findAccidentById(int id) {
        return jdbc.queryForObject(
                "select id, name, text, address, type_id from accidents where id = ?",
                accidentRowMapper, id);
    }

    private void saveAccidentRules(Accident accident, String[] ids) {
        List<Rule> rules = new ArrayList<>();
        for (String ruleId : ids) {
            rules.add(findRuleById(Integer.parseInt(ruleId)));
        }
        for (Rule rule : rules) {
            jdbc.update("insert into accidents_rules (rules_id, accident_id) values (?, ?)",
                    rule.getId(),
                    accident.getId());
        }
    }

    private void deleteAccidentRules(int id) {
        jdbc.update("delete from accidents_rules where accident_id = ?", id);
    }

    public List<AccidentType> getAllTypes() {
        return jdbc.query("select id, name from accidents_types",
                accidentTypeRowMapper);
    }

    public AccidentType findTypeById(int id) {
        return jdbc.queryForObject(
                "select id, name from accidents_types where id = ?",
                accidentTypeRowMapper, id);
    }

    public List<Rule> getAllRules() {
        return jdbc.query("select id, name from rules",
                ruleRowMapper);
    }

    public Rule findRuleById(int id) {
        return jdbc.queryForObject(
                "select id, name from rules where id = ?",
                ruleRowMapper, id);
    }

    public Set<Rule> findRulesByAccident_id(int accidentId) {
        Set<Rule> rules = new HashSet<>();
        for (int ruleId : findRulesIds(accidentId)) {
            rules.add(findRuleById(ruleId));
        }
        return rules;
    }

    private List<Integer> findRulesIds(int accidentId) {
        return jdbc.query("select rules_id from accidents_rules where accident_id = ?",
                (rs, row) -> rs.getInt("rules_id"), accidentId);
    }
}