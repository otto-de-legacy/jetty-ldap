package de.otto.jetty.ldap

import org.eclipse.jetty.jaas.JAASLoginService
import org.eclipse.jetty.security.authentication.BasicAuthenticator
import org.eclipse.jetty.security.{ConstraintMapping, ConstraintSecurityHandler}
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.util.security.Constraint

object LdapContext {

	def createSecureContext(allowGroups: Array[String], realm: String, path: String): ServletContextHandler = {
		val servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS)
		val constraintSecurityHandler = ldapSecurityHandler(allowGroups, realm, path)
		servletContextHandler.setSecurityHandler(constraintSecurityHandler)
		servletContextHandler
	}

	private def ldapSecurityHandler(allowGroups: Array[String], realm: String, path: String): ConstraintSecurityHandler = {
		val jAASLoginService = new JAASLoginService("ldaploginmodule")

		val constraint = new Constraint()
		constraint.setName(Constraint.__BASIC_AUTH)
		constraint.setRoles(allowGroups)
		constraint.setAuthenticate(true)

		val constraintMapping = new ConstraintMapping()
		constraintMapping.setConstraint(constraint)
		constraintMapping.setPathSpec(path)

		val constraintSecurityHandler = new ConstraintSecurityHandler()
		constraintSecurityHandler.setAuthenticator(new BasicAuthenticator())
		constraintSecurityHandler.setRealmName(realm)
		constraintSecurityHandler.addConstraintMapping(constraintMapping)
		constraintSecurityHandler.setLoginService(jAASLoginService)

		constraintSecurityHandler
	}
}
