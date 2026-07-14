#!/bin/sh
set -eu

mysql_cmd() {
  mysql --protocol=socket -uroot -p"${MYSQL_ROOT_PASSWORD}" "$@"
}

for database in pager_activity pager_auth pager_cart pager_goods pager_job pager_order pager_platform pager_risk pager_shop; do
  mysql_cmd -e "CREATE DATABASE IF NOT EXISTS \`${database}\` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
done

mysql_cmd pager_activity < /opt/shop/sql/pager_activity.sql
mysql_cmd pager_auth < /opt/shop/sql/pager_auth.sql
mysql_cmd pager_cart < /opt/shop/sql/pager_cart.sql
mysql_cmd pager_goods < /opt/shop/sql/pager_goods.sql
mysql_cmd pager_job < /opt/shop/sql/pager_job.sql
mysql_cmd pager_order < /opt/shop/sql/pager_order.sql
mysql_cmd pager_platform < /opt/shop/sql/pager_platform.sql
mysql_cmd pager_risk < /opt/shop/sql/pager_risk.sql
mysql_cmd pager_shop < /opt/shop/sql/pager_shop.sql
