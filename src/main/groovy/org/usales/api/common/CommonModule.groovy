package org.usales.api.common

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import jdk.nashorn.internal.runtime.Scope

/**
 * Created by steven on 28/12/2016.
 */
class CommonModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(MessageRender).in(Scopes.SINGLETON)
    }
}
