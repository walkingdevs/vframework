package filer;

import com.vaadin.addon.jpacontainer.EntityManagerProvider;

import javax.persistence.EntityManager;

public class LazyHibernateEntityManagerProvider implements EntityManagerProvider{
    private static ThreadLocal <EntityManager>
        entityManagerThreadLocal = new ThreadLocal<>();
    @Override
    public EntityManager getEntityManager() {
        return entityManagerThreadLocal.get();
    }
    public static void setCurrentEntityManager (EntityManager em) {
        entityManagerThreadLocal.set (em);
    }
}
