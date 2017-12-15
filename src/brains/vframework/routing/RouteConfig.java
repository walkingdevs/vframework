package brains.vframework.routing;

import brains.vframework.VUI;

public interface RouteConfig {
    VUI route(final Class<? extends VUI> from);
}
