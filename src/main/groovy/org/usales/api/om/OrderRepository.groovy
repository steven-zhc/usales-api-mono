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

    Promise<OrderLine> create(Order order, OrderLine l) {
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

    Promise<Order> save(Order order) {
        order.lines.each { l ->
            Blocking.get {
                sql.executeUpdate("""
UPDATE `order_line_t` SET `line_profit`=$l.profit, `line_total`=$l.total, `model`=$l.model, `note`=$l.note, `product_id`=$l.pid, 
`quantity`=$l.q, `s_discount`=$l.sell.d, `s_price`=$l.sell.p, `s_shipping`=$l.sell.s, `s_tax`=$l.sell.t, `s_total`=$l.sell.total, 
`p_discount`=$l.purchase.d, `p_price`=$l.purchase.p, `p_shipping`=$l.purchase.s, `p_tax`=$l.purchase.t, `p_total`=$l.purchase.total 
WHERE id=$l.lid
""")
            }
        }

        Blocking.get {
            sql.executeUpdate("UPDATE `order` SET `note`=$order.note, `status`=$order.status, `profit`=$order.profit, `total`=$order.total")
        }

        Operation.of().then(Order)
    }

    Promise<Integer> save(OrderLine l) {
        Blocking.get {
            sql.executeUpdate("""
UPDATE `order_line_t` SET `line_profit`=$l.profit, `line_total`=$l.total, `model`=$l.model, `note`=$l.note, `product_id`=$l.pid, 
`quantity`=$l.q, `s_discount`=$l.sell.d, `s_price`=$l.sell.p, `s_shipping`=$l.sell.s, `s_tax`=$l.sell.t, `s_total`=$l.sell.total, 
`p_discount`=$l.purchase.d, `p_price`=$l.purchase.p, `p_shipping`=$l.purchase.s, `p_tax`=$l.purchase.t, `p_total`=$l.purchase.total 
WHERE id=$l.lid
""")
        }
    }

    Promise<Boolean> deleteOrderLine(Order order, OrderLine line) {
        Blocking.get {
            sql.execute("DELETE FROM `order_line_t` WHERE `id`=$line.lid AND `order_id`=$order.oid")
        }
    }
}
