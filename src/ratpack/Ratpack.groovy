
import static ratpack.groovy.Groovy.ratpack

ratpack {
    handlers {
        path("category") {
            byContent {
                json {
                    byMethod {
                        get {
                            def name = request.queryParams.name
                            render "get category: $name"
                        }
                        post {
                            def body = request.body
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

                        }
                        patch {

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
                        }
                        post {

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

                        }
                        patch {

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
                        }
                        post {

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

                        }
                        patch {

                        }
                    }
                }
            }

            path("line") {
                byContent {
                    json {
                        byMethod {
                            get {

                            }
                            post {

                            }
                        }
                    }
                }
            }

            path("line/:lid") {
                def lid = pathTokens['lid']

                byContent {
                    json {
                        byMethod {
                            patch {

                            }
                            delete {

                            }
                        }
                    }
                }
            }
        }
    }
}