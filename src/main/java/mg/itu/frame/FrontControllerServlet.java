package mg.itu.frame;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.itu.annotation.Controller.Controller;
import mg.itu.utils.Utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class FrontControllerServlet extends HttpServlet {
    
    private List<String> controllers = new ArrayList<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        String basePackages = config.getInitParameter("base-package");
        if (basePackages != null) {
            for (String pkg : basePackages.split(";")) {
                List<String> found = Utils.findClassesByAnnotation(pkg.trim(), Controller.class);
                controllers.addAll(found);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String uri = req.getRequestURI();
        String method = req.getMethod();
        
        // Permettre de surcharger la méthode via un paramètre URL
        String methodParam = req.getParameter("method");
        if (methodParam != null && !methodParam.isEmpty()) {
            method = methodParam.toUpperCase();
        }
        
        String appName = req.getContextPath();

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print("<h1>Manaona tompoko</h1>");
        out.print("<h2>Controllers détectés:</h2>");
        out.print("<ul>");
        for (String controller : controllers) {
            out.print("<li>" + controller + "</li>");
        }
        out.print("</ul>");
        if (controllers.isEmpty()) {
            out.print("<p>Aucun controller détecté</p>");
        }
    }
}