FROM payara/server-full:latest
MAINTAINER AEPAD "martdominguez"

# copiar el jar
COPY mysql-connector-java-5.1.42.jar ${PAYARA_PATH}/glassfish/domains/domain1/lib

# desplegar el war
#COPY src/main/resources/aepad-logica.war $DEPLOY_DIR
# crear JDBC
# CMD ${PAYARA_PATH}/bin/asadmin create-jdbc-connection-pool --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource --restype javax.sql.DataSource --property user=root:password=root:DatabaseName=tp-aepad:ServerName=172.18.0.2:port=3306 tp-pool
# crear JDBC resource
# CMD ${PAYARA_PATH}/bin/asadmin/create-jdbc-resource --connectionpoolid tp-pool jdbc/TP
