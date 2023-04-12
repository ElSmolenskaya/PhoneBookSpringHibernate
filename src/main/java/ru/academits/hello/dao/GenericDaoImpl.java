package ru.academits.hello.dao;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

@Transactional
public class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {
    @PersistenceContext
    protected EntityManager entityManager;

    protected Class<T> clazz;

    public GenericDaoImpl(Class<T> type) {
        this.clazz = type;
    }

    @Transactional
    @Override
    public void create(T obj) {
        entityManager.persist(obj);
    }

    @Transactional
    @Override
    public void update(T obj) {
        entityManager.merge(obj);
    }

    @Transactional
    @Override
    public void remove(T obj) {
        entityManager.remove(obj);
    }

    @Override
    public T getById(PK id) {
        return entityManager.find(clazz, id);
    }

    @Transactional
    @Override
    public List<T> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);

        Root<T> root = cq.from(clazz);

        CriteriaQuery<T> select = cq.select(root);

        TypedQuery<T> q = entityManager.createQuery(select);

        return q.getResultList();
    }

    @Transactional
    @Override
    public List<T> find(String term) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);

        Root<T> root = cq.from(clazz);

        String termForQuery;

        if (term == null || term.equals("")) {
            termForQuery = "%";
        } else {
            termForQuery = "%" + term + "%";
        }

        Predicate[] predicates = new Predicate[3];

        predicates[0] = cb.like(
                cb.lower(root.get("lastName")),
                cb.lower(cb.literal(termForQuery))
        );

        predicates[1] = cb.like(
                cb.lower(root.get("firstName")),
                cb.lower(cb.literal(termForQuery))
        );

        predicates[2] = cb.like(
                cb.lower(root.get("phone")),
                cb.lower(cb.literal(termForQuery))
        );

        Predicate predicate = cb.or(predicates);

        CriteriaQuery<T> select = cq.select(root).where(predicate);

        TypedQuery<T> q = entityManager.createQuery(select);

        return q.getResultList();
    }
}