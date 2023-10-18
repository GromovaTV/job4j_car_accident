package ru.job4j.accident.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.accident.model.Accident;

public interface AccidentRepository extends CrudRepository<Accident, Integer> {

    @Override
    @Query("select distinct a from accident a JOIN a.type LEFT JOIN fetch a.rules")
    Iterable<Accident> findAll();
}