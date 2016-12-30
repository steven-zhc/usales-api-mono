package org.usales.api.pm

import ratpack.groovy.handling.GroovyChainAction

/**
 * Created by steven on 28/12/2016.
 */
class ProductEndpoint extends GroovyChainAction {
    @Override
    void execute() throws Exception {
        path {
            byContent {
                json {
                    byMethod {
                        get {
                            def name = request.queryParams.name
                            def cid = request.queryParams.cid

                            render "get product: [name:$name][cid:$cid]"
                        }
                        post {
                            def body = request.body

                            render "post product"
                        }
                    }
                }
            }
        }

        prefix(":pid") {
            all {
                String pid = allPathTokens["pid"]
                next()
            }

            path {
                byContent {
                    json {
                        byMethod {
                            get {
                                String pid = allPathTokens["pid"]
                                render "get product/$pid"
                            }
                            patch {
                                String pid = allPathTokens["pid"]
                                render "patch product/$pid"
                            }
                        }
                    }
                }
            }
        }
    }
}
