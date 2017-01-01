package org.usales.api.om

import ratpack.groovy.handling.GroovyContext
import ratpack.groovy.render.GroovyRendererSupport
import ratpack.jackson.Jackson

/**
 * Created by steven on 30/12/2016.
 */
class OrderLineRender extends GroovyRendererSupport<OrderLine> {
    @Override
    void render(GroovyContext context, OrderLine object) throws Exception {
        context.render Jackson.json(object)
    }
}
