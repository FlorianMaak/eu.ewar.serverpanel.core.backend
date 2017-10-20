package eu.ewar.serverpanel.core;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.ewar.serverpanel.core.config.ServerConfig;


public class ServerCore {

    private static final Logger logger = LoggerFactory.getLogger(ServerCore.class);

    private ServerConfig config = new ServerConfig();


    private Server confiureServer()
            throws Exception {
        logger.debug("configureServer() entered");
        Server server = new Server();

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(9999);

        HttpConfiguration https = new HttpConfiguration();
        https.addCustomizer(new SecureRequestCustomizer());

        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages("eu.ewar.serverpanel.core.service");

        ServletHolder holder = new ServletHolder(new ServletContainer(resourceConfig));

        ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/rest/*");
        servletContextHandler.addServlet(holder, "/*");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[] {"test.html"});
        resourceHandler.setResourceBase("./html");

        HandlerList handlerList = new HandlerList();
        handlerList.addHandler(servletContextHandler);
        handlerList.addHandler(resourceHandler);

        server.setHandler(handlerList);

        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath(ServerCore.class.getResource(config.getKeystoreURL()).toExternalForm());
        sslContextFactory.setKeyStorePassword(config.getKeystorPass());
        sslContextFactory.setKeyManagerPassword(config.getKeystorPass());

        ServerConnector sslConnector = new ServerConnector(server,
                                                           new SslConnectionFactory(sslContextFactory, "http/1.1"),
                                                           new HttpConnectionFactory(https));
        sslConnector.setPort(9998);

        server.setConnectors(new Connector[] {connector, sslConnector});

        return server;
    }


    public static void main(String[] args)
            throws Exception {

        ServerCore serverCore = new ServerCore();
        Server server = serverCore.confiureServer();
        server.start();

        logger.debug("Server started");

    }

}
