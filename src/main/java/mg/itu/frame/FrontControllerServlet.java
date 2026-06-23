package mg.itu.frame;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.itu.annotation.Controller.Controller;
import mg.itu.annotation.Controller.Url;
import mg.itu.utils.UrlMapping;
import mg.itu.utils.Utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FrontControllerServlet extends HttpServlet {
    
    private List<String> controllers = new ArrayList<>();
    private Map<String, UrlMapping> urlMappings = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("=== FrontControllerServlet init ===");
        String basePackages = config.getInitParameter("base-package");
        System.out.println("base-package: " + basePackages);
        if (basePackages != null) {
            for (String pkg : basePackages.split(";")) {
                System.out.println("Scanning package: " + pkg.trim());
                List<String> found = Utils.findClassesByAnnotation(pkg.trim(), Controller.class);
                System.out.println("Found controllers: " + found);
                controllers.addAll(found);
                
                // Pour chaque controller, scanner les méthodes @Url
                for (String controllerClass : found) {
                    try {
                        Class<?> clazz = Class.forName(controllerClass);
                        Object controllerInstance = clazz.getDeclaredConstructor().newInstance();
                        
                        for (Method method : clazz.getDeclaredMethods()) {
                            if (method.isAnnotationPresent(Url.class)) {
                                Url urlAnnotation = method.getAnnotation(Url.class);
                                String path = urlAnnotation.value();
                                UrlMapping mapping = new UrlMapping(path, controllerInstance, method);
                                urlMappings.put(path, mapping);
                                System.out.println("Mapped URL: " + path + " -> " + method.getName());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.println("Total controllers: " + controllers.size());
        System.out.println("Total URL mappings: " + urlMappings.size());
        System.out.println("=== End init ===");
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
        String appName = req.getContextPath();
        
        // Extraire le chemin sans le contexte
        String path = uri.substring(appName.length());
        
        System.out.println("Request path: " + path);

        // Chercher le mapping correspondant
        UrlMapping mapping = urlMappings.get(path);
        
        if (mapping != null) {
            try {
                // Invoquer la méthode du controller
                Method targetMethod = mapping.getMethod();
                Object result = targetMethod.invoke(mapping.getControllerInstance());
                
                resp.setContentType("text/html;charset=UTF-8");
                PrintWriter out = resp.getWriter();
                out.print("<h1>Résultat: " + result + "</h1>");
            } catch (Exception e) {
                e.printStackTrace();
                resp.sendError(500, "Erreur lors de l'invocation de la méthode");
            }
        } else {
            // URL non trouvée, afficher les URLs disponibles
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.print("<h1>Manaona tompoko</h1>");
            out.print("<h2>URL non trouvée: " + path + "</h2>");
            out.print("<h2>URLs disponibles:</h2>");
            out.print("<ul>");
            for (String url : urlMappings.keySet()) {
                out.print("<li><a href=\"" + appName + url + "\">" + url + "</a></li>");
            }
            out.print("</ul>");
            if (urlMappings.isEmpty()) {
                out.print("<p>Aucune URL mappée</p>");
            }
        }
    }
}