Jetty LDAP
==========
[![Build Status](https://travis-ci.org/otto-de/jetty-ldap.svg?branch=master)](https://travis-ci.org/otto-de/jetty-ldap)
> Scala-library to enable LDAP-Authorization for Jetty

This library provides a way to create a jetty-context which is secured via ldap-login.

## Installation

```scala
//build.sbt
libraryDependencies += "de.otto" % "jetty-ldap" % "0.1.0"
```

## Configuration
Change parameters surrounded by __ in ldap-loginModule.conf.
* LDAP_SERVER -> e.g.: "ldap.your-company.com" 
* LDAP_SERVER_PORT -> e.g.: "636"
* BIND_DN -> e.g.: "uid=some-uid,ou=some-organization-unit,dc=your-company,dc=com"
* BIND_USER_PW -> The password for your bind user.
* USER_BASE_DN -> This is where your users are stored.
* ROLE_BASE_DN -> This is where the roles are stored.

You have to run your jar with an extra parameter, which points to your ldap-loginModule.conf:
```
java -Djava.security.auth.login.config=<path-to-ldap-loginModule.conf> -jar your-application.jar
```
## Example
```scala
import de.otto.jetty.ldap.LdapContext

val allowedGroups = Array("allowd-group-1", "allowd-group-2")
val realm = "your-realm"
val pathSpec = "/*"

val ldapContext = LdapContext.createSecureContext(allowedGroups, realm, pathSpec)

val jetty = new org.eclipse.jetty.server.Server(8080)
jetty.setHandler(ldapContext)
jetty.start()
```

## Release History

* 0.1.0
    * First release

## Contributing

1. Fork it
2. Create your feature branch (`git checkout -b feature/fooBar`)
3. Commit your changes (`git commit -am 'Add some fooBar'`)
4. Push to the branch (`git push origin feature/fooBar`)
5. Create a new Pull Request
6. Get a round of applause

## Meta

Have a look at our developer-blog: [dev.otto.de](https://dev.otto.de/)

## Copyright

Copyright [Otto (GmbH & Co KG)](http://www.otto.de)

## License

[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt)