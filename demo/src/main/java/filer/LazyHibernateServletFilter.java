package filer;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LazyHibernateServletFilter implements Filter {
    private EntityManagerFactory entityManagerFactory;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        entityManagerFactory = Persistence
            .createEntityManagerFactory("lazyhibernate", getProps());

    }

    public Map<String, Object> getProps(){
        Map<String, Object> configOverrides = new HashMap<>();
        configOverrides.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");
        configOverrides.put("javax.persistence.jdbc.url", "jdbc:postgresql://localhost:5432/postgres");
        configOverrides.put("javax.persistence.jdbc.user", "postgres");
        configOverrides.put("javax.persistence.jdbc.password", "");
        configOverrides.put("hibernate.hbm2ddl.auto", "create-drop");
        configOverrides.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configOverrides.put("hibernate.hbm2ddl.auto", "update");
        configOverrides.put("hibernate.show_sql", true);
        configOverrides.put("hibernate.format_sql", true);
        return  configOverrides;
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
