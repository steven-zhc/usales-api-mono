package org.usales.api.pm

import ratpack.groovy.handling.GroovyContext
import ratpack.groovy.render.GroovyRendererSupport
import ratpack.jackson.Jackson

/**
 * Created by steven on 30/12/2016.
 */
class CategoryRender extends GroovyRendererSupport<Category> {
    @Override
    void render(GroovyContext context, Category object) throws Exception {
        context.render Jackson.json(object)
    }
}
