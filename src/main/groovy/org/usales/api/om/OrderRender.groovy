package org.usales.api.om

import org.usales.api.pm.Category
import ratpack.groovy.handling.GroovyContext
import ratpack.groovy.render.GroovyRendererSupport
import ratpack.jackson.Jackson

/**
 * Created by steven on 30/12/2016.
 */
class OrderRender extends GroovyRendererSupport<Order> {
    @Override
    void render(GroovyContext context, Order object) throws Exception {
        context.render Jackson.json(object)
    }
}
