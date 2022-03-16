package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import org.springframework.util.AutoPopulatingList;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private HashMap<Integer, Accident> accidents = new HashMap<>();
    private List<AccidentType> types = new ArrayList<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    public AccidentMem() {
        types.add(AccidentType.of(1, "Две машины"));
        types.add(AccidentType.of(2, "Машина и человек"));
        types.add(AccidentType.of(3, "Машина и велосипед"));
    }

    public Collection<Accident> getAllAccidents() {
        return accidents.values();
    }

    public void save(Accident accident) {
        accident.setType(findTypeById(accident.getType().getId()));
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
}
