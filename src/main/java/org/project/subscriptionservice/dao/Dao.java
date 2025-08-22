package org.project.subscriptionservice.dao;

import java.util.List;

public interface Dao<T> {

    List<T> queryAll();

    T view(Integer id);

    int create(T entity);

    int update(T newEntity, Integer id);

    int forceDelete(Integer id);
}
