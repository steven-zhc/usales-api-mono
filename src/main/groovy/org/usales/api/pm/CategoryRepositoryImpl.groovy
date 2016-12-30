package org.usales.api.pm

import ratpack.exec.Promise

/**
 * Created by steven on 28/12/2016.
 */
class CategoryRepositoryImpl implements CategoryRepository {
    @Override
    Promise<Category> create(Category category) {
        return Promise.value(new Category(cid: 1, name: "toy"))
    }

    @Override
    Promise<Category> save(Category category) {
        return Promise.value(new Category(cid: 1, name: "toy"))
    }

    @Override
    Promise<Category> find(String cid) {
        return Promise.value(new Category(cid: 1, name: "toy"))
    }

    @Override
    Promise<List<Category>> findByName(String name) {
        List list = [
                new Category(cid: 1, name: "toy"),
                new Category(cid: 2, name: "beauty")
        ]

        return Promise.value(list)
    }

    @Override
    Promise<List<Category>> all() {
        List list = [
                new Category(cid: 1, name: "toy"),
                new Category(cid: 2, name: "beauty")
        ]

        return Promise.value(list)
    }
}
