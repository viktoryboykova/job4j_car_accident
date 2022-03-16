package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import org.springframework.util.AutoPopulatingList;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private HashMap<Integer, Accident> accidents = new HashMap<>();
    private List<AccidentType> types = new ArrayList<>();
    private List<Rule> rules = new ArrayList<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    public AccidentMem() {
        types.add(AccidentType.of(1, "Две машины"));
        types.add(AccidentType.of(2, "Машина и человек"));
        types.add(AccidentType.of(3, "Машина и велосипед"));
        rules.add(Rule.of(1, "Статья. 1"));
        rules.add(Rule.of(2, "Статья. 2"));
        rules.add(Rule.of(3, "Статья. 3"));
    }

    public Collection<Accident> getAllAccidents() {
        return accidents.values();
    }

    public void save(Accident accident, String[] ids) {
        accident.setType(findTypeById(accident.getType().getId()));
        Set<Rule> rulesOfCurrentAccident = new HashSet<>();
        for (String id : ids) {
            rulesOfCurrentAccident.add(findRuleById(Integer.parseInt(id)));
        }
        accident.setRules(rulesOfCurrentAccident);
        if (accident.getId() == 0) {
            accident.setId(counter.incrementAndGet());
            accidents.put(accident.getId(), accident);
        } else {
            accidents.replace(accident.getId(), accident);
        }
    }

    public Accident findAccidentById(int id) {
        return accidents.get(id);
    }

    public List<AccidentType> getAllTypes() {
        return types;
    }

    public AccidentType findTypeById(int id) {
        return types.get(id - 1);
    }

    public List<Rule> getAllRules() {
        return rules;
    }

    public Rule findRuleById(int id) {
        return rules.get(id - 1);
    }
}
