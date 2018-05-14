FROM payara/server-full:4.181
MAINTAINER AEPAD "martdominguez"

# copiar el jar
COPY mysql-connector-java-5.1.42.jar ${PAYARA_PATH}/glassfish/domains/domain1/lib
RUN echo ${ADMIN_USER}
ENV ADMIN_PASSWORD admin

RUN echo 'AS_ADMIN_PASSWORD=\n\
AS_ADMIN_NEWPASSWORD='${ADMIN_PASSWORD}'\n\
EOF\n'\
>> /opt/tmpfile

RUN echo 'AS_ADMIN_PASSWORD='${ADMIN_PASSWORD}'\n\
EOF\n'\
>> /opt/pwdfile

RUN cat /opt/pwdfile
# crear JDBC
RUN  ${PAYARA_PATH}/bin/asadmin start-domain domain1 && \
     ${PAYARA_PATH}/bin/asadmin --user admin --passwordfile=/opt/pwdfile enable-secure-admin && \
     ${PAYARA_PATH}/bin/asadmin stop-domain domain1

RUN  ${PAYARA_PATH}/bin/asadmin start-domain domain1 && \
    ${PAYARA_PATH}/bin/asadmin --user admin --passwordfile=/opt/pwdfile create-jdbc-connection-pool --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource --restype javax.sql.DataSource --property user=root:password=root:DatabaseName=aepad-tp:ServerName=172.18.0.2:port=3306 tp-pool && \
    ${PAYARA_PATH}/bin/asadmin --user admin --passwordfile=/opt/pwdfile create-jdbc-connection-pool --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource --restype javax.sql.DataSource --property user=root:password=root:DatabaseName=aepad-bk:ServerName=172.18.0.2:port=3306 tp-pool-bk  && \
    ${PAYARA_PATH}/bin/asadmin --user admin --passwordfile=/opt/pwdfile create-jdbc-resource --connectionpoolid tp-pool jdbc/aepad  && \
    ${PAYARA_PATH}/bin/asadmin --user admin --passwordfile=/opt/pwdfile create-jdbc-resource --connectionpoolid tp-pool-bk jdbc/aepadbk && \
    ${PAYARA_PATH}/bin/asadmin stop-domain domain1

