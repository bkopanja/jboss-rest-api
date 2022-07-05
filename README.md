# Simple RESTful API

This app is created as a part of the ZCAM assignment

# Build process

Building the app is really simple, it's as simple as doing:

`mvn clean package`

This will produce a `JbossRestApi-1.0.war` file inside the `target` directory and that's what we need to deploy to Wildfly

The app is configured using the `jboss-web.xml` to run under the context root of `/` so keep that in mind if you're deploying it on a server with multiple apps

# Additional setup

The app is deployed on Wildfly 26.1.1 and we've configured a DS in Wildfly that is being referenced within the app (using JNDI)

The DS JNDI Name is `jboss/datasources/PostgresDS`

The app runs on Java 11

The DB runs on PostgreSQL 12.x, please create a DB and user that can access it and then execute
`src/main/resources/db/initial-db.sql` for the initial DB structure