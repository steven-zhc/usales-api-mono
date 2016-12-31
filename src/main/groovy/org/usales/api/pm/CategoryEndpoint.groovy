package org.usales.api.pm

import groovy.util.logging.Slf4j
import org.usales.api.common.Message
import ratpack.groovy.handling.GroovyChainAction
import ratpack.jackson.Jackson

import javax.inject.Inject


/**
 * Created by hzhang on 12/28/2016.
 */
@Slf4j
class CategoryEndpoint extends GroovyChainAction {

    private CategoryRepository repository

    @Inject
    CategoryEndpoint(CategoryRepository repository) {
        this.repository = repository
    }

    @Override
    void execute() throws Exception {
        path {
            byMethod {
                get {
                    def name = request.queryParams.name
                    if (name) {
                        repository
                                .all()
                                .then { render Jackson.json(it) }

                    } else {
                        repository
                                .findByName(name)
                                .then { render Jackson.json(it) }
                    }
                }
                post {
                    parse(Jackson.fromJson(CreateCategoryCommand)).flatMap {
                        Category c = new Category(name: it.name)
                        repository.create(c)
                    }.then {
                        render it
                    }
                }
            }
        }

        prefix(":cid") {
            all {
                String cid = allPathTokens["cid"]
                repository.find(cid).onNull {
                    render new Message(status: 404, message: "Cannot find Category $cid.")
                }.then { Category category ->
                    next(single(category))
                }
            }
            path {
                byMethod {
                    def category = get(Category)

                    get {
                        render category
                    }

                    patch {

                        parse(Jackson.fromJson(CreateCategoryCommand)).flatMap { cmd ->
                            if (cmd.name) {
                                category.name = cmd.name
                            }
                            repository.save(category)
                        }.then {
                            render it
                        }
                    }
                }
            }
        }
    }
}
