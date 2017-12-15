package brains.vframework.presenter.route;

import brains.vframework.VContainers;
import brains.vframework.exception.RouteParseException;
import brains.vframework.jee.CallerPrincipal;
import brains.vframework.presenter.CrudPresenter;
import brains.vframework.presenter.api.RouteViewPresenter;
import brains.vframework.view.DefaultCrudView;
import brains.vframework.view.route.EntityExplorerView;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import java.io.Serializable;

public class EntityExplorerPresenter implements RouteViewPresenter<EntityExplorerView>, Serializable {
    private EntityExplorerView view;
    private CrudPresenter<?, DefaultCrudView> crudPresenter = new CrudPresenter<>();

//    @Inject
//    AuthorityProvider authorityProvider;

    @Inject
    CallerPrincipal callerPrincipal;

    @Override
    public EntityExplorerView view() {
        return view;
    }

    @Override
    public void view(final EntityExplorerView view) {
        this.view = view;

        crudPresenter.view(view.defaultCrudView);
    }

    @Override
    public void onRequest(final String request) throws RouteParseException {
        if (crudPresenter.container() != null) return;
        try {
            final Class<?> forClass = Class.forName(request);
            final RolesAllowed rolesAllowed = forClass.getAnnotation(RolesAllowed.class);
            if (rolesAllowed != null) {
//                final List<String> roles = authorityProvider.get(callerPrincipal.get());
//                for (final String role : rolesAllowed.value()) {
//                    if (roles.contains(role)) {
//                        crudPresenter.container(VContainers.createContainerUntyped(forClass));
//                        return;
//                    }
//                }
//                throw new RuntimeException("403");
            }
            crudPresenter.container(VContainers.createContainerUntyped(forClass));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("404");
        }
    }

    @Override
    public void onReload() {
    }
}
