
import static ratpack.groovy.Groovy.ratpack

ratpack {
    handlers {
        path("category") {
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
        path("category/:cid") {
            def cid = pathTokens["cid"]

            byContent {
                json {
                    byMethod {
                        get {
                            render "get category/$cid"
                        }
                        patch {
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

        path("product") {
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

        path("product/:pid") {
            def pid = pathTokens["pid"]

            byContent {
                json {
                    byMethod {
                        get {
                            render "get product/$pid"
                        }
                        patch {
                            render "patch product/$pid"
                        }
                    }
                }
            }
        }

        path("order") {
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

        path("order/:oid") {
            def oid = pathTokens['oid']

            byContent {
                json {
                    byMethod {
                        get {
                            render "get order/$oid"
                        }
                        patch {
                            render "patch order/$oid"
                        }
                    }
                }
            }
        }

        path("order/:oid/line") {
            def oid = pathTokens['oid']

            byContent {
                json {
                    byMethod {
                        get {
                            render "get order/$oid/line"
                        }
                        post {
                            render "post order/$oid/line"
                        }
                    }
                }
            }
        }

        path("order/:oid/line/:lid") {
            def oid = pathTokens['oid']
            def lid = pathTokens['lid']

            byContent {
                json {
                    byMethod {
                        get {
                            render "get order/$oid/line/$lid"
                        }
                        patch {
                            render "patch order/$oid/line/$lid"
                        }
                        delete {
                            render "delete order/$oid/line/$lid"
                        }
                    }
                }
            }
        }
    }
}