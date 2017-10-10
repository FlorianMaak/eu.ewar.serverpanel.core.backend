package eu.ewar.serverpanel.core;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.ewar.serverpanel.core.config.ServerConfig;
import eu.ewar.serverpanel.core.servlet.APIServlet;


public class ServerCore {

    private static final Logger logger = LoggerFactory.getLogger(ServerCore.class);
    
    private ServerConfig config = new ServerConfig();

    private Server confiureServer()
            throws Exception {
        logger.debug("configureServer() entered");
        Server server = new Server();

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(9999);

        ServletHandler servletHandler = new ServletHandler();

        HttpConfiguration https = new HttpConfiguration();
        https.addCustomizer(new SecureRequestCustomizer());

        servletHandler.addServletWithMapping(APIServlet.class, "/*");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[] {"test.html"});
        resourceHandler.setResourceBase("./html");
        server.setHandler(resourceHandler);

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
