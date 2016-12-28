package org.usales.api.pm

import groovy.util.logging.Slf4j
import ratpack.groovy.handling.GroovyChainAction

/**
 * Created by hzhang on 12/28/2016.
 */
@Slf4j
class CategoryEndpoint extends GroovyChainAction {

    @Override
    void execute() throws Exception {
        path {
            byContent {
                json {
                    byMethod {
                        get {
                            def name = request.queryParams.name
                            render "get category: [name:$name]"
                        }
                        post {
                            def body = request.body
                            render "post category"
                        }
                    }
                }
                noMatch {
                    response.status 400
                    render "negotiation not possible."
                }
            }
        }

        prefix(":cid") {
            all {
                String cid = allPathTokens["cid"]
                next()
            }

            path {
                byContent {
                    json {
                        byMethod {
                            get {
                                String cid = allPathTokens["cid"]
                                render "get category/$cid"
                            }
                            patch {
                                String cid = allPathTokens["cid"]
                                render "patch category/$cid"
                            }
                        }
                    }
                    noMatch {
                        response.status 400
                        render "negotiation not possible."
                    }
                }
            }
        }
    }
}
