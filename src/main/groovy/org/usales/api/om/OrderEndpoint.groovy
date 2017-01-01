package org.usales.api.om

import ratpack.groovy.handling.GroovyChainAction

import javax.inject.Inject

/**
 * Created by steven on 28/12/2016.
 */
class OrderEndpoint extends GroovyChainAction {

    private OrderRepository repository

    @Inject
    OrderEndpoint(OrderRepository repository) {
        this.repository = repository
    }

    @Override
    void execute() throws Exception {
        path {
            byContent {
                json {
                    byMethod {
                        get {
                            def name = request.queryParams.name
                            def status = request.queryParams.status

                            render "get order: [name:$name] [status:$status]"
                        }
                        post {
                            render "post order"
                        }
                    }
                }
            }
        }

        prefix(":oid") {
            all {
                String oid = allPathTokens['oid']
                next()
            }

            path {
                byContent {
                    json {
                        byMethod {
                            get {
                                String oid = allPathTokens['oid']
                                render "get order/$oid"
                            }
                            patch {
                                String oid = allPathTokens['oid']
                                render "patch order/$oid"
                            }
                        }
                    }
                }
            }

            prefix("line") {
                path {
                    byContent {
                        json {
                            byMethod {
                                get {
                                    String oid = allPathTokens['oid']
                                    render "get order/$oid/line"
                                }
                                post {
                                    String oid = allPathTokens['oid']
                                    render "post order/$oid/line"
                                }
                            }
                        }
                    }
                }

                prefix(":lid") {
                    all {
                        String lid = allPathTokens['lid']
                        next()
                    }

                    path {
                        byContent {
                            json {
                                byMethod {
                                    get {
                                        String oid = allPathTokens['oid']
                                        String lid = allPathTokens['lid']
                                        render "get order/$oid/line/$lid"
                                    }
                                    patch {
                                        String oid = allPathTokens['oid']
                                        String lid = allPathTokens['lid']
                                        render "patch order/$oid/line/$lid"
                                    }
                                    delete {
                                        String oid = allPathTokens['oid']
                                        String lid = allPathTokens['lid']
                                        render "delete order/$oid/line/$lid"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
