package org.usales.api.common

import ratpack.groovy.handling.GroovyContext
import ratpack.groovy.render.GroovyRendererSupport
import ratpack.jackson.Jackson;

/**
 * Created by steven on 28/12/2016.
 */
class MessageRender extends GroovyRendererSupport<Message> {

    @Override
    void render(GroovyContext context, Message message) throws Exception {
        context.byContent {
            json {
                context.response.status message.status
                context.render Jackson.json(message.message)
            }
        }
    }
}
