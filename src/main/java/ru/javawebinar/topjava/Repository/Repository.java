package ru.javawebinar.topjava.Repository;

import java.util.List;

public interface Repository<T> {

    T add(T obj);

    T update(T obj);

    void delete(int id);

    T get(Integer id);

    List<T> getAll();
}
