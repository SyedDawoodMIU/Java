package com.waa.assignments.repo;

import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BaseRepository<T> {

    List<T> findAll();

    void save(T t);

    T getById(int id);

    void delete(T t);

    int update(T t);

    void delete(int id);

    void update(int id, T t);





}
