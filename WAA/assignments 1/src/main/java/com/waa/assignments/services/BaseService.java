package com.waa.assignments.services;

public interface BaseService<T> {

    public void save(T t);

    public T getById(int id);

    public void delete(T t);

    public int update(T t);

    public void delete(int id);

    public void update(int id, T t);


}
