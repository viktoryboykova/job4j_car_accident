package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private HashMap<Integer, Accident> accidents = new HashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    public Collection<Accident> getAllAccidents() {
        return accidents.values();
    }

    public void save(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(counter.incrementAndGet());
            accidents.put(accident.getId(), accident);
        } else {
            accidents.replace(accident.getId(), accident);
        }
    }

    public Accident findById(int id) {
        return accidents.get(id);
    }
}
