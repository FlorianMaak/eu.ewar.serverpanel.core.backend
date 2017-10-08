package eu.ewar.serverpanel.core.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DeliveryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException,
            IOException {

        File file = new File("test.html");
        resp.setContentType("text/html");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        StringBuilder buffer = new StringBuilder();

        while (bufferedReader.ready()) {
            buffer.append(bufferedReader.readLine()).append("\n");

        }

        bufferedReader.close();

        resp.getOutputStream().println(buffer.toString());
    }

}
