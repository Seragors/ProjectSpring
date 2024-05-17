package spring.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.entity.Task;

import java.util.List;

@Repository
public class TaskDao {
    private final SessionFactory sessionFactory;

    public TaskDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<Task> getAll(int offset, int limit) {
        Query<Task> query = getSession().createQuery("select t from Task t", Task.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Task getById(int id) {
        Query<Task> query = getSession().createQuery("select t from Task t where t.id = :Id", Task.class);
        query.setParameter("Id", id);
        return query.uniqueResult();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int getAllCount() {
        Query<Long> query = getSession().createQuery("select count(*) from Task t", Long.class);
        return Math.toIntExact(query.uniqueResult());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrEdit(Task task) {
        getSession().persist(task);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Task task) {
        getSession().remove(task);
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
