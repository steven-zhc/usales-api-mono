import org.usales.api.common.CommonModule
import org.usales.api.om.OrderEndpoint
import org.usales.api.om.OrderModule
import org.usales.api.pm.CategoryEndpoint
import org.usales.api.pm.ProductEndpoint
import org.usales.api.pm.ProductModule
import ratpack.groovy.sql.SqlModule
import ratpack.hikari.HikariModule

import static ratpack.groovy.Groovy.ratpack

ratpack {
    bindings {

        module SqlModule
        module(HikariModule) { c ->
            c.dataSourceClassName = "com.mysql.jdbc.jdbc2.optional.MysqlDataSource"
            c.addDataSourceProperty "URL", "jdbc:mysql://localhost:3306/usales_dev"
            c.username = 'usales_user'
            c.password = 'Welcome1'
        }

        module CommonModule
        module ProductModule
        module OrderModule
    }

    handlers { CategoryEndpoint categoryEndpoint,
               ProductEndpoint productEndpoint,
               OrderEndpoint orderEndpoint ->

        prefix("category", categoryEndpoint)

        prefix("product", productEndpoint)

        prefix("order", orderEndpoint)
    }
}