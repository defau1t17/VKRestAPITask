package org.vktask.vkrestapitask.service;

import java.util.List;
import java.util.Optional;

public abstract class EntityDAO<T, I> {
    public abstract List<T> findAll();

    public abstract T save(T entity);

    public abstract T update(T entity);

    public abstract void delete(T entity);

    public abstract Optional<T> findByID(I id);

    public abstract boolean validateBeforeSave(T entity);


}
