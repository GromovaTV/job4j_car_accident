package ru.job4j.accident.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.Collection;
import java.util.function.Function;

@Repository
public class AccidentHibernate {

    private final SessionFactory sf;

    public AccidentHibernate(SessionFactory sf) {
        this.sf = sf;
    }

    private <T> T create(T t) {
        return execute(session -> {
            session.save(t);
            return t;
        });
    }

    private <T> void update(T t) {
        execute(session -> {
            session.update(t);
            return t;
        });
    }

    private <T> T execute(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void save(Accident accident) {
        execute(
                session -> {
                    session.saveOrUpdate(accident);
                    return accident;
                }
        );
    }

    public void save(AccidentType type) {
        execute(
                session -> {
                    session.saveOrUpdate(type);
                    return type;
                }
        );
    }

    public void save(Rule rule) {
        execute(
                session -> {
                    session.saveOrUpdate(rule);
                    return rule;
                }
        );
    }

    public Collection<Accident> getAll() {
        return execute(
                session -> {
                    final Query query = session.createQuery("select distinct a from accident a "
                            + "LEFT JOIN fetch a.rules", Accident.class);
                    return query.list();
                }
        );
    }

    public Collection<AccidentType> getAllTypes() {
        return execute(
                session -> {
                    final Query query = session.createQuery("select distinct t from type t",
                            AccidentType.class);
                    return query.list();
                }
        );
    }

    public Collection<Rule> getAllRules() {
        return execute(
                session -> {
                    final Query query = session.createQuery("select distinct r from rule r",
                            Rule.class);
                    return query.list();
                }
        );
    }

    public Accident get(int id) {
        return execute(
                session -> {
                    final Query query = session.createQuery("select distinct a from accident a "
                            + "JOIN a.type at "
                            + "LEFT JOIN a.rules "
                            + "where a.id=:id");
                    query.setParameter("id", id);
                    Accident res = (Accident) query.uniqueResult();
                    return res;
                }
        );
    }

    public Rule getRule(int id) {
        return execute(session -> session.get(Rule.class, id));
    }

    public AccidentType getType(int id) {
        return execute(session -> session.get(AccidentType.class, id));
    }
}