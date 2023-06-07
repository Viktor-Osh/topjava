package ru.javawebinar.topjava.Repository;

import java.util.List;

public interface Repository<T> {

    void add(T obj);

    void addAll(List<T> list);

    void update(T obj);

    void delete(Integer id);

    List<T> getAll();
}
