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
                    repository
                            .findByName(name)
                            .then { List<Category> list ->
                        render Jackson.json(list)
                    }
                }
                post {
                    parse(Jackson.fromJson(CreateCategoryCommand)).flatMap { cmd ->
                        Category c = new Category(name: cmd.name)
                        repository.create(c)
                    }.then { Category c ->
                        render Jackson.json(c)
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
                    get {
                        def c = get(Category)

                        render Jackson.json(c)
                    }

                    patch {
                        Category c = get(Category)

                        parse(Jackson.fromJson(CreateCategoryCommand)).flatMap { cmd ->
                            if (cmd.name) {
                                c.name = cmd.name
                            }
                            repository.save(c)
                        }.then {
                            render it
                        }
                    }
                }
            }
        }
    }
}
