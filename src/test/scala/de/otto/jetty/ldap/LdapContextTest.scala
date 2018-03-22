package de.otto.jetty.ldap

import java.net.ServerSocket

import org.eclipse.jetty.client.HttpClient
import org.eclipse.jetty.client.api.ContentResponse
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.scalatest.{Matchers, WordSpec}

class LdapContextTest extends WordSpec with Matchers {

	private def jettyTestServer(port: Int, servletContextHandler: ServletContextHandler): Server = {
		val testServer = new org.eclipse.jetty.server.Server(port)
		testServer.setHandler(servletContextHandler)
		testServer
	}

	private def getLocalhost(port: Int, path: String): ContentResponse = {
		val httpClient = new HttpClient()
		httpClient.start()
		httpClient.GET(s"http://localhost:$port$path")
	}

	private def freePort(): Int = {
		val serverSocket = new ServerSocket(0)
		val port = serverSocket.getLocalPort
		serverSocket.close()
		port
	}

	"Creation of context with ldap" should {
		"return status-code 401 when not authorized" in {
			val port = freePort()
			val path = "/"

			val servletContextHandler = LdapContext.createSecureContext(Array("some-group"), "some-realm", "/*")

			val server = jettyTestServer(port, servletContextHandler)
			server.start()

			getLocalhost(port, path).getStatus shouldBe 401

			server.stop()
		}
	}
}
