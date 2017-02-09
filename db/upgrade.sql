CREATE TABLE `order_line_t` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_created` datetime DEFAULT NULL,
  `line_profit` float NOT NULL,
  `line_total` float NOT NULL,
  `model` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL,

  `s_discount` float DEFAULT NULL,
  `s_price` float DEFAULT NULL,
  `s_shipping` float DEFAULT NULL,
  `s_tax` float DEFAULT NULL,
  `s_total` float DEFAULT NULL,

  `p_discount` float DEFAULT NULL,
  `p_price` float DEFAULT NULL,
  `p_shipping` float DEFAULT NULL,
  `p_tax` float DEFAULT NULL,
  `p_total` float DEFAULT NULL,

  PRIMARY KEY (`id`),

  FOREIGN KEY (`order_id`) REFERENCES `order` (`id`),
  FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)

) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

INSERT INTO `order_line_t` (`id`, `date_created`, `line_profit`, `line_total`, `model`, `note`, `order_id`, `product_id`, 
    `quantity`, `s_discount`, `s_price`, `s_shipping`, `s_tax`, `s_total`, `p_discount`, `p_price`, `p_shipping`, `p_tax`, `p_total`) 
SELECT l.`id`, l.`date_created`, l.`line_profit`, l.`line_total`, l.`model`, l.`note`, l.`order_id`, l.`product_id`, 
       l.`quantity`, s.`discount`, s.`price`, s.`shipping`, s.`tax`, s.`total`, p.`discount`, p.`price`, p.`shipping`, p.`tax`, p.`total` 
FROM   order_line l, 
       (SELECT b.* FROM order_line a, line_body b WHERE  a.`sell_id` = b.`id`) s, 
       (SELECT b.* FROM order_line a, line_body b WHERE  a.`purchase_id` = b.`id`) p 
WHERE  l.`id` = s.`header_id` AND l.`id` = p.`header_id` 

DELIMITER $$
CREATE TRIGGER after_order_line_insert
    AFTER INSERT ON order_line
    FOR EACH ROW 
BEGIN

INSERT INTO `order_line_t` (`id`, `date_created`, `line_profit`, `line_total`, `model`, `note`, `order_id`, `product_id`, 
    `quantity`, `s_discount`, `s_price`, `s_shipping`, `s_tax`, `s_total`, `p_discount`, `p_price`, `p_shipping`, `p_tax`, `p_total`) 
SELECT l.`id`, l.`date_created`, l.`line_profit`, l.`line_total`, l.`model`, l.`note`, l.`order_id`, l.`product_id`, 
       l.`quantity`, s.`discount`, s.`price`, s.`shipping`, s.`tax`, s.`total`, p.`discount`, p.`price`, p.`shipping`, p.`tax`, p.`total` 
FROM   order_line l, 
       (SELECT b.* FROM order_line a, line_body b WHERE  a.`sell_id` = b.`id`) s, 
       (SELECT b.* FROM order_line a, line_body b WHERE  a.`purchase_id` = b.`id`) p 
WHERE  l.`id` = s.`header_id` AND l.`id` = p.`header_id` and l.`id` = NEW.`id`

END$$
DELIMITER ;



