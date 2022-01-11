package nl.bioinf.group5.servlets;

import nl.bioinf.group5.config.WebConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ErrorHandler", urlPatterns = {"/ErrorHandler"}, loadOnStartup = 1)
public class ErrorHandler extends HttpServlet {

    private static final long serialVersionUID = 1L;
    @Override
    public void init() throws ServletException {
        final ServletContext servletContext = this.getServletContext();
        WebConfig.createTemplateEngine(servletContext);
    }

    public void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();

        Exception exception = (Exception)
                request.getAttribute("javax.servlet.error.exception");
        writer.printf("exception: %s%n", exception);

        Class exceptionClass = (Class)
                request.getAttribute("javax.servlet.error.exception_type");
        writer.printf("exception_type: %s%n", exceptionClass);

        Throwable throwable = (Throwable)
                request.getAttribute("javax.servlet.error.exception_type");
        writer.printf("throwable: %s%n", throwable);

        String errorMessage = (String) request.getAttribute(
                "javax.servlet.error.message");
        writer.printf("message: %s%n", errorMessage);

        String requestUri = (String) request.getAttribute(
                "javax.servlet.error.request_uri");
        response.getWriter().printf("request_uri: %s%n",
                requestUri);

        Integer statusCode = (Integer)
                request.getAttribute("javax.servlet.error.status_code");
        writer.printf("status_code: %s%n", statusCode);

        String servletName = (String)
                request.getAttribute("javax.servlet.error.servlet_name");
        writer.printf("servlet_name: %s%n", servletName);
    }

    public void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
