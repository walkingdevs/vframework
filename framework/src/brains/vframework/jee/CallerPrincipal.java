package brains.vframework.jee;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

@Stateless
public class CallerPrincipal {
    @Resource
    SessionContext sessionContext;

    public String get() {
        return sessionContext.getCallerPrincipal().getName();
    }
}
