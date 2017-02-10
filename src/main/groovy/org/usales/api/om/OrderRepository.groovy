package org.usales.api.om

import groovy.sql.Sql
import ratpack.exec.Blocking
import ratpack.exec.Operation
import ratpack.exec.Promise

import javax.inject.Inject

/**
 * Created by steven on 01/01/2017.
 */
class OrderRepository {
    private final Sql sql

    @Inject
    OrderRepository(Sql sql) {
        this.sql = sql
    }

    Promise<Order> get(Long id) {

    }

    Promise<List<Order>> find(String name, Integer status) {

    }

    Promise<Order> create(Order order) {
        Blocking.get {
            sql.executeInsert("""
INSERT INTO `order` (`date_created`, `note`, `status`, `profit`, `total`)
VALUES (NOW(), $order.note, $order.status, $order.profit, $order.total)
""", ['id'])
        }.flatMap {
            order.oid = it[0][0] as long

            Operation.of {
                order.lines.each { l ->
                    Blocking.get {
                        sql.executeInsert("""
INSERT INTO `order_line_t` (`date_created`, `line_profit`, `line_total`, `model`, `note`, `order_id`, `product_id`, 
    `quantity`, `s_discount`, `s_price`, `s_shipping`, `s_tax`, `s_total`, `p_discount`, `p_price`, `p_shipping`, `p_tax`, `p_total`) 
VALUES (NOW(), $l.profit, $l.total, $l.model, $l.note, $order.oid, $l.pid, $l.q, 
$l.sell.d, $l.sell.p, $l.sell.s, $l.sell.t, $l.sell.total,
$l.purchase.d, $l.purchase.p, $l.purchase.s, $l.purchase.t, $l.purchase.total)
""", ['id'])
                    }.then {
                        l.lid = it[0][0] as long
                        l
                    }
                }
            }.map {
                order
            }
        }

    }

    Promise<OrderLine> create(Order order, OrderLine line) {

    }

    Promise<Order> save(Order order) {

    }

    Promise<OrderLine> save(OrderLine line) {

    }

    void deleteOrderLine(Order order, OrderLine line) {

    }
}
