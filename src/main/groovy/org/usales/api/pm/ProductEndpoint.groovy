package org.usales.api.pm

import groovy.util.logging.Slf4j
import org.usales.api.common.Message
import ratpack.groovy.handling.GroovyChainAction
import ratpack.jackson.Jackson

import javax.inject.Inject

/**
 * Created by steven on 28/12/2016.
 */
@Slf4j
class ProductEndpoint extends GroovyChainAction {

    private ProductRepository repository

    @Inject
    ProductEndpoint(ProductRepository repository) {
        this.repository = repository
    }

    @Override
    void execute() throws Exception {
        path {
            byMethod {
                get {
                    def name = request.queryParams.name
                    def cid = request.queryParams.cid as Long

                    repository.find(name, cid)

                    render "get product: [name:$name][cid:$cid]"
                }
                post {
                    parse(Jackson.fromJson(CreateProductCommand)).flatMap {
                        Product p = new Product(it.properties)
                        repository.create(p)
                    }.then {
                        render it
                    }
                }
            }
        }

        prefix(":pid") {
            all {
                Long pid = allPathTokens.asLong("pid")
                repository.get(pid).onNull {
                    render new Message(status: 404, message: "Cannot find Category $cid.")
                }.then {
                    next(single(it))
                }
            }

            path {
                byMethod {
                    def product = get(Product)

                    get {
                        render product
                    }
                    patch {
                        parse(Jackson.fromJson(CreateProductCommand)).flatMap { cmd ->

                            product.name = cmd.name ?: product.name
                            product.desc = cmd.desc ?: product.desc
                            product.price = cmd.price ?: product.price
                            product.url = cmd.url ?: product.url
                            product.cid = cmd.cid ?: product.cid

                            repository.save(product)
                        }.then {
                            render it
                        }
                    }
                }
            }
        }
    }
}
