package mg.itu.frame;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.itu.annotation.Controller.Controller;
import mg.itu.annotation.RequestMapping;
import mg.itu.annotation.Url;
import mg.itu.utils.Utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class FrontControllerServlet extends HttpServlet {
    
    private List<String> controllers = new ArrayList<>();
    private List<RouteInfo> routes = new ArrayList<>();
    
    private static class RouteInfo {
        String url;
        String className;
        String methodName;
        Method method;
        
        RouteInfo(String url, String className, String methodName, Method method) {
            this.url = url;
            this.className = className;
            this.methodName = methodName;
            this.method = method;
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        String basePackages = config.getInitParameter("base-package");
        if (basePackages != null) {
            for (String pkg : basePackages.split(";")) {
                List<String> found = Utils.findClassesByAnnotation(pkg.trim(), Controller.class);
                controllers.addAll(found);
                scanRoutes(found);
            }
        }
    }
    
    private void scanRoutes(List<String> controllerClasses) {
        for (String className : controllerClasses) {
            try {
                Class<?> clazz = Class.forName(className);
                Method[] methods = clazz.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(Url.class)) {
                        Url urlAnnotation = method.getAnnotation(Url.class);
                        routes.add(new RouteInfo(urlAnnotation.value(), className, method.getName(), method));
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
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
        String appName = req.getContextPath();
        String path = uri.substring(appName.length());

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        
        // Rechercher la route correspondante
        RouteInfo foundRoute = null;
        for (RouteInfo route : routes) {
            if (route.url.equals(path)) {
                foundRoute = route;
                break;
            }
        }
        
        if (foundRoute != null) {
            // URL trouvée - exécuter la méthode
            try {
                Class<?> clazz = Class.forName(foundRoute.className);
                Object controllerInstance = clazz.getDeclaredConstructor().newInstance();
                Object result = foundRoute.method.invoke(controllerInstance);
                
                // Afficher le résultat
                if (result != null) {
                    out.print(result.toString());
                } else {
                    out.print("<p>Méthode exécutée mais retourne null</p>");
                }
            } catch (Exception e) {
                out.print("<p>Erreur lors de l'exécution: " + e.getMessage() + "</p>");
                e.printStackTrace();
            }
        } else {
            // URL inconnue - afficher message et liste des URLs disponibles
            out.print("<h1>URL inconnue</h1>");
            out.print("<p>L'URL <strong>" + path + "</strong> n'existe pas.</p>");
            out.print("<h2>URLs disponibles:</h2>");
            out.print("<ul>");
            if (routes.isEmpty()) {
                out.print("<p>Aucune URL disponible</p>");
            } else {
                for (RouteInfo route : routes) {
                    out.print("<li>" + route.url + " -> " + route.className + "." + route.methodName + "()</li>");
                }
            }
            out.print("</ul>");
        }
    }
}