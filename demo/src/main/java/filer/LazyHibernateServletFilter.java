package filer;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.*;
import java.io.IOException;

public class LazyHibernateServletFilter implements Filter {
    private EntityManagerFactory entityManagerFactory;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        entityManagerFactory = Persistence
            .createEntityManagerFactory("lazyhibernate");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            LazyHibernateEntityManagerProvider.setCurrentEntityManager(
                entityManagerFactory.createEntityManager()
            );
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            // Reset the entity manager
            LazyHibernateEntityManagerProvider
                .setCurrentEntityManager(null);
        }

    }

    @Override
    public void destroy() {
        entityManagerFactory = null;
    }
}
