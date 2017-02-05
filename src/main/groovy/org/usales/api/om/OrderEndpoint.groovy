package org.usales.api.om

import org.usales.api.common.Message
import ratpack.groovy.handling.GroovyChainAction
import ratpack.jackson.Jackson

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
            byMethod {
                get {
                    def name = request.queryParams.name
                    def status = request.queryParams.status

                    repository.find(name, status)
                            .then { render Jackson.json(it) }

                }
                post {
                    parse(Jackson.fromJson(CreateOrderCommand)).flatMap { ocmd ->
                        List<OrderLine> lines = ocmd.lines.collect { lcmd ->
                            LineBody p = new LineBody(p: lcmd.purchase.p, t: lcmd.purchase.t, s: lcmd.purchase.s, d: lcmd.purchase.d)
                            LineBody s = new LineBody(p: lcmd.sell.p, t: lcmd.sell.t, s: lcmd.sell.s, d: lcmd.sell.d)

                            new OrderLine(pid: lcmd.pid, q: lcmd.q, note: lcmd.note, model: lcmd.model, purchase: p, sell: s)

                        }.asList()

                        Order order = new Order(note: ocmd.note, status: Order.ORDER_STATUS_INQUIRE, lines: lines)

                        repository.create(order)
                    }.then {
                        render it
                    }
                }
            }
        }

        prefix(":oid") {
            all {
                Long oid = allPathTokens.asLong("oid")

                repository.get(oid).onNull {
                    render new Message(status: 404, message: "Cannot find Order $oid.")
                }.then { Order order ->
                    next(single(order))
                }

            }

            path { ctx ->
                byMethod {
                    def order = ctx.get(Order)

                    get {
                        render order
                    }
                    patch {

                        parse(Jackson.fromJson(UpdateOrderCommand)).flatMap { cmd ->

                            order.status = cmd.status ?: order.status
                            order.deliverFee = cmd.deliverFee ?: order.deliverFee
                            order.deliverDate = cmd.deliverDate ?: order.deliverDate
                            order.trackingNo = cmd.trackingNo ?: order.trackingNo
                            order.payment = cmd.payment ?: order.payment
                            order.note = cmd.note ?: order.note

                            repository.save(order)
                        }.then {
                            render it
                        }
                    }
                }
            }

            prefix("line") {
                path { ctx ->
                    byMethod {
                        Order order = ctx.get(Order)

                        get {
                            render Jackson.json(order.lines)
                        }
                        post {
                            parse(Jackson.fromJson(CreateOrderLineCommand)).flatMap { cmd ->

                                LineBody p = new LineBody(p: cmd.purchase.p, t: cmd.purchase.t, s: cmd.purchase.s, d: cmd.purchase.d)
                                LineBody s = new LineBody(p: cmd.sell.p, t: cmd.sell.t, s: cmd.sell.s, d: cmd.sell.d)

                                OrderLine l = new OrderLine(pid: cmd.pid, q: cmd.q, note: cmd.note, model: cmd.model, purchase: p, sell: s)

                                repository.addOrderLine(order, l)
                            }.then {
                                render it
                            }

                        }
                    }
                }

                prefix(":lid") {
                    all { ctx ->

                        Order order = ctx.get(Order)
                        Long lid = allPathTokens.asLong("lid")
                        OrderLine line = order.lines.find { it.lid == lid }

                        if (line) {
                            next(single(line))
                        } else {
                            render new Message(status: 404, message: "Cannot find order line $lid.")
                        }
                    }

                    path { ctx ->
                        byMethod {
                            def order = ctx.get(Order)
                            def line  = ctx.get(OrderLine)

                            get {
                                render line
                            }
                            patch {
                                parse(Jackson.fromJson(UpdateOrderLineCommand)).flatMap { cmd ->

                                    line.q = cmd.q ?: line.q
                                    line.note = cmd.note ?: line.note
                                    line.model = cmd.model ?: line.model

                                    if (cmd.purchase) {
                                        def p = line.purchase

                                        p.p = cmd.purchase.p ?: p.p
                                        p.t = cmd.purchase.t ?: p.t
                                        p.s = cmd.purchase.s ?: p.s
                                        p.d = cmd.purchase.d ?: p.d
                                    }

                                    if (cmd.sell) {
                                        def s = line.sell

                                        s.p = cmd.sell.p ?: s.p
                                        s.t = cmd.sell.t ?: s.t
                                        s.s = cmd.sell.s ?: s.s
                                        s.d = cmd.sell.d ?: s.d
                                    }

                                    repository.save(line)

                                }.then {
                                    render it
                                }


                            }
                            delete {
                                repository.deleteOrderLine(order, line)
                            }
                        }
                    }
                }
            }
        }
    }
}
