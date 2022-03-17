package ru.job4j.accident.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.*;
import java.util.function.Function;

@Repository
public class AccidentHibernate {
    private final SessionFactory sf;

    public AccidentHibernate(SessionFactory sf) {
        this.sf = sf;
    }

    public void saveAccident(Accident accident) {
        tx(session -> {
            session.saveOrUpdate(accident);
            return accident;
        });
    }

    public Accident findAccidentById(int accidentId) {
        return tx(session -> session.get(Accident.class, accidentId));
    }

    public AccidentType findAccidentTypeById(int typeId) {
        return tx(session -> session.get(AccidentType.class, typeId));
    }

    public Rule findRuleById(int ruleId) {
        return tx(session -> session.get(Rule.class, ruleId));
    }

    public Set<Rule> createRulesForCurrentAccident(String[] rIds) {
        Set<Rule> rules = new HashSet<>();
        Arrays.stream(rIds).forEach(rId -> rules.add(findRuleById(Integer.parseInt(rId))));
        return rules;
    }

    public List<Accident> getAllAccidents() {
        return tx(session -> session
                .createQuery("select distinct a from Accident a join fetch a.type join fetch a.rules")
                .list());
    }

    public List<AccidentType> getAllTypes() {
        return tx(session -> session
                .createQuery("from AccidentType", AccidentType.class)
                .list());
    }
    public List<Rule> getAllRules() {
        return tx(session -> session
                .createQuery("from Rule", Rule.class)
                .list());
    }

    private <T> T tx(final Function<Session, T> command) {
        Session session = sf.openSession();
        try {
            T rsl = command.apply(session);
            session.beginTransaction().commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
