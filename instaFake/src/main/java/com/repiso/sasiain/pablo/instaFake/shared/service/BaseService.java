package com.repiso.sasiain.pablo.instaFake.shared.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public abstract class BaseService<T,ID, R extends JpaRepository<T,ID>> {
    @Autowired
    protected R repository;

    public List<T> findAll() {
        return repository.findAll();
    }

    public Page<T> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    public T save(T t) {
        return repository.save(t);
    }

    public T edit(T t) {
        return save(t);
    }

    public void delete(T t) {
        repository.delete(t);
    }

    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    public List<T> saveAll(Iterable<T> iterable) {
        return repository.saveAll(iterable);
    }

    public void deleteAll(Iterable<T> iterable){repository.deleteAll(iterable);}
}