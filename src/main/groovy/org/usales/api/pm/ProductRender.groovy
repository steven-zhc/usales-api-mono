package org.usales.api.pm

import ratpack.groovy.handling.GroovyContext
import ratpack.groovy.render.GroovyRendererSupport
import ratpack.jackson.Jackson

/**
 * Created by steven on 31/12/2016.
 */
class ProductRender extends GroovyRendererSupport<Product> {

    @Override
    void render(GroovyContext context, Product object) throws Exception {
        context.render Jackson.json(object)
    }
}
