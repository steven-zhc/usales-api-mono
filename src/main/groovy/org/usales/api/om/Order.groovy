package org.usales.api.om

/**
 * Created by steven on 01/01/2017.
 */
class Order {

    static final int ORDER_STATUS_INQUIRE = 1

    Long oid
    Integer status

    Float total
    Float profit


    Date dateCreated = new Date()
    String note

    Float deliverFee
    Date deliverDate
    String trackingNo

    Float payment

    List<OrderLine> lines

    void settle() {

        lines.each { it.settle() }

        total = lines.inject(0.0) { acc, line -> acc + line.total }
        profit = lines.inject(0.0) { acc, line -> acc + line.profit }

        if (deliverFee) {
            total += deliverFee
        }
    }

    void addOrderLine(OrderLine line) {
        lines.add(line)
        settle()
    }

    @Override
    String toString() {
        return """Order{oid=$oid, status=$status, total=$total, profit=$profit, dateCreated=$dateCreated, note='$note', 
deliverFee=$deliverFee, deliverDate=$deliverDate, trackingNo='$trackingNo', payment=$payment, lines=$lines}"""
    }
}

class OrderLine {
    Long lid

    Long pid
    Integer q
    String model
    String note
    Float total
    Float profit

    LineBody purchase
    LineBody sell

    void settle() {
        purchase.settle(q)
        sell.settle(q)

        total = sell.total
        profit = (sell.total - purchase.total).round(2)
    }

    @Override
    String toString() {
        return """OrderLine{lid=$lid, pid=$pid, q=$q, model='$model', note='$note', 
total=$total, profit=$profit, purchase=$purchase, sell=$sell}"""
    }

}

class LineBody {
    Float p
    Float t
    Float s
    Float d
    Float total

    void settle(int quantity) {
        total = (p * quantity).round(2) + t + s + d
    }

    @Override
    String toString() {
        return """LineBody{p=$p, t=$t, s=$s, d=$d, total=$total}"""
    }

}
