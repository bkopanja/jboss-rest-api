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

# APP Explanation

The app has 3 Controllers:

1. ExternalApiUserRestController.java
2. UserRestController.java
3. AddressRestController.java

## ExternalApiUserRestController.java

This controller is in charge of communicating with the external API that I've found by googling for a "dummy api". This external API is
created for quick testing and was a perfect candidate for this integration as it provides user data that looks like a real-world data.

The external api is located here: https://jsonplaceholder.typicode.com/

## UserRestController.java

Once we import users into our DB using the external API this controller does the standard set of actions on our users:

- get a list of all users
- get a single user
- update a user
- delete a user

## AddressRestController.java

When we import users into our DB alongside them we're creating Address entities as well so this controller again
does the standard set of actions on our addresses:

- get a list of all addresses
- get a single address
- update an address
- delete an address

# Swagger

When you land on a first page of this app you're freeted with Swagger-UI so there you can test all API calls and see how endpoints actually work