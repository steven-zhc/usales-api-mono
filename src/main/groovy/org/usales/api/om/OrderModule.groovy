package org.usales.api.om

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import org.usales.api.pm.CategoryEndpoint
import org.usales.api.pm.CategoryRender
import org.usales.api.pm.CategoryRepository
import org.usales.api.pm.ProductEndpoint

/**
 * Created by steven on 28/12/2016.
 */
class OrderModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(OrderRepository).in(Scopes.SINGLETON)
        bind(OrderRender).in(Scopes.SINGLETON)
        bind(OrderLineRender).in(Scopes.SINGLETON)
        bind(OrderEndpoint).in(Scopes.SINGLETON)

    }
}
