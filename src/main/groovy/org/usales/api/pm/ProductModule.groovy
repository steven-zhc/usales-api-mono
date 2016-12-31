package org.usales.api.pm

import com.google.inject.AbstractModule
import com.google.inject.Scopes

/**
 * Created by steven on 28/12/2016.
 */
class ProductModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CategoryRepository).to(CategoryRepositoryImpl).in(Scopes.SINGLETON)
        bind(CategoryEndpoint).in(Scopes.SINGLETON)
        bind(CategoryRender).in(Scopes.SINGLETON)

        bind(ProductEndpoint).in(Scopes.SINGLETON)

    }
}
