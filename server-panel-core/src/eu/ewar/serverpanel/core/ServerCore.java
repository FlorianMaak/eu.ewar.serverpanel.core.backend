package eu.ewar.serverpanel.core;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;

import eu.ewar.serverpanel.core.servlet.DeliveryServlet;


public class ServerCore {

    private static Server server;


    private static void confiureServer() {
        server = new Server(8080);

        ServletHandler servletHandler = new ServletHandler();
        
        servletHandler.addServletWithMapping(DeliveryServlet.class, "/*");
       
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[] {"test.html"});
        resourceHandler.setResourceBase("./html");
        server.setHandler(resourceHandler);
        

    }


    public static void main(String[] args)
            throws Exception {
        confiureServer();
        server.start();
    }

}
