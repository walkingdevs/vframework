package brains.vframework.security;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SecuritySessionListener implements HttpSessionListener {
//    @Inject
//    SecurityContext securityContext;

    @Override
    public void sessionCreated(final HttpSessionEvent httpSessionEvent) {
        System.out.println("-------------------------------- Session Created");
        System.out.println(httpSessionEvent.getSession().getId());
        System.out.println(httpSessionEvent.getSession().getClass());
    }

    @Override
    public void sessionDestroyed(final HttpSessionEvent httpSessionEvent) {
        System.out.println("-------------------------------- Session Destroyed");
        System.out.println(httpSessionEvent.getSession().getId());
        System.out.println(httpSessionEvent.getSession().getClass());

//        securityContext.
    }
}
