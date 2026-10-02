package mg.itu.frame;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.itu.annotation.Controller.Controller;
import mg.itu.annotation.RequestMapping;
import mg.itu.annotation.WebAPI.WebAPI;
import mg.itu.utils.Utils;

import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;


public class FrontControllerServlet extends HttpServlet {

    public static class RouteInfo {
        String url;
        String httpMethod;
        String className;
        String methodName;
        boolean webApi;

        RouteInfo(String url, String httpMethod, String className, String methodName, boolean webApi) {
            this.url = url;
            this.httpMethod = httpMethod;
            this.className = className;
            this.methodName = methodName;
            this.webApi = webApi;
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

    @SuppressWarnings("unchecked")
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // SPRINT 7 : doit être fait AVANT tout appel à req.getParameter(...),
        // sinon les accents des champs de formulaire (POST) sont mal décodés.
        req.setCharacterEncoding("UTF-8");

        List<RouteInfo> routes =
                (List<RouteInfo>) getServletContext().getAttribute("routes");
        if (routes == null) {
            routes = (List<RouteInfo>) getServletContext().getAttribute("mappingUrls");
        }
        if (routes == null) {
            routes = List.of();
        }
        if (routes.isEmpty()) {
            routes = chargerRoutes();
            getServletContext().setAttribute("routes", routes);
            getServletContext().setAttribute("mappingUrls", routes);
        }
        String uri = req.getRequestURI();
        String method = req.getMethod();

        // Permettre de surcharger la méthode via un paramètre URL
        String methodParam = req.getParameter("method");
        if (methodParam != null && !methodParam.isEmpty()) {
            method = methodParam.toUpperCase();
        }

        String appName = req.getContextPath();
        String path = uri.substring(appName.length());

        // Rechercher la route correspondante
        RouteInfo foundRoute = null;
        for (RouteInfo route : routes) {
            if (route.url.equals(path) && route.httpMethod.equalsIgnoreCase(method)) {
                foundRoute = route;
                break;
            }
        }

        if (foundRoute == null) {
            // URL inconnue - afficher message et liste des URLs disponibles
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.print("<h1>URL inconnue</h1>");
            out.print("<p>L'URL <strong>" + path + "</strong> avec la méthode <strong>" + method + "</strong> n'existe pas.</p>");
            out.print("<h2>URLs disponibles:</h2>");
            out.print("<ul>");
            if (routes.isEmpty()) {
                out.print("<p>Aucune URL disponible</p>");
            } else {
                for (RouteInfo route : routes) {
                    out.print("<li>" + route.httpMethod + " " + route.url + " -> " + route.className + "." + route.methodName + "()</li>");
                }
            }
            out.print("</ul>");
            return;
        }

        try {
            // Instancier le contrôleur
            Class<?> clazz = Class.forName(foundRoute.className);
            Object instance = clazz.getDeclaredConstructor().newInstance();

            // SPRINT 7 : retrouver la méthode (elle peut maintenant avoir des arguments)
            Method laMethode = trouverMethode(clazz, foundRoute);

            // SPRINT 7 : remplir les arguments à partir des paramètres de la requête
            Object[] arguments;
            try {
                arguments = construireArguments(laMethode, req, resp);
            } catch (IllegalArgumentException e) {
                // valeur envoyée par le formulaire non convertible (ex: age = "abc")
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
                return;
            }

            Object resultat = laMethode.invoke(instance, arguments);

            if (foundRoute.webApi) {
                resp.setContentType("application/json;charset=UTF-8");
                PrintWriter out = resp.getWriter();
                if (resultat instanceof String) {
                    out.print((String) resultat);
                } else {
                    out.print(Utils.toJson(resultat));
                }
                return;
            }

            if (resultat instanceof ModelAndView) {
                ModelAndView mv = (ModelAndView) resultat;

                // Étape 1 - Construire le chemin JSP à partir du préfixe/suffixe de web.xml
                String prefixe = getServletConfig().getInitParameter("prefixe");
                String suffixe = getServletConfig().getInitParameter("suffixe");
                if (prefixe == null) prefixe = "";
                if (suffixe == null) suffixe = "";
                String cheminJsp = prefixe + mv.getUrl() + suffixe;

                // Étape 2 - Injecter les données dans la requête HTTP
                for (Map.Entry<String, Object> entry : mv.getData().entrySet()) {
                    req.setAttribute(entry.getKey(), entry.getValue());
                }

                // Étape 3 - Forward vers la JSP
                RequestDispatcher dispatcher = req.getRequestDispatcher(cheminJsp);
                dispatcher.forward(req, resp);

            } else {
                // La méthode a retourné autre chose (ex: String) -> on l'affiche telle quelle
                resp.setContentType("text/html;charset=UTF-8");
                PrintWriter out = resp.getWriter();
                out.print(resultat != null ? resultat.toString() : "");
            }

        } catch (Exception e) {
            throw new ServletException("Erreur lors de l'exécution de " + foundRoute.className + "." + foundRoute.methodName + "()", e);
        }
    }

    private List<RouteInfo> chargerRoutes() {
        List<RouteInfo> routes = new ArrayList<>();
        String basePackages = getServletContext().getInitParameter("base-package");

        if (basePackages == null || basePackages.isBlank()) {
            return routes;
        }

        for (String pkg : basePackages.split(";")) {
            List<String> found = Utils.findClassesByAnnotation(pkg.trim(), Controller.class);
            for (String className : found) {
                try {
                    Class<?> clazz = Class.forName(className);
                    for (Method method : clazz.getDeclaredMethods()) {
                        if (method.isAnnotationPresent(RequestMapping.class)) {
                            RequestMapping mapping = method.getAnnotation(RequestMapping.class);
                            boolean webApi = method.isAnnotationPresent(WebAPI.class);
                            routes.add(new RouteInfo(
                                    mapping.url(),
                                    mapping.method(),
                                    className,
                                    method.getName(),
                                    webApi));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return routes;
    }

    /**
     * Retrouve la méthode du contrôleur correspondant à la route.
     * On compare nom + url + méthode HTTP (getDeclaredMethod(nom) ne marche plus
     * dès que la méthode a des arguments).
     */
    private Method trouverMethode(Class<?> clazz, RouteInfo route) throws NoSuchMethodException {
        for (Method m : clazz.getDeclaredMethods()) {
            if (!m.getName().equals(route.methodName)) {
                continue;
            }
            RequestMapping mapping = m.getAnnotation(RequestMapping.class);
            if (mapping != null
                    && mapping.url().equals(route.url)
                    && mapping.method().equalsIgnoreCase(route.httpMethod)) {
                return m;
            }
        }
        throw new NoSuchMethodException(route.className + "." + route.methodName);
    }

    /**
     * Construit le tableau d'arguments pour l'invocation :
     *  - HttpServletRequest / HttpServletResponse : injectés tels quels
     *  - autres types : valeur du paramètre de requête, convertie vers le type de l'argument
     */
    private Object[] construireArguments(Method m, HttpServletRequest req, HttpServletResponse resp) {
        Parameter[] params = m.getParameters();
        Object[] args = new Object[params.length];

        for (int i = 0; i < params.length; i++) {
            Parameter p = params[i];
            Class<?> type = p.getType();

            if (HttpServletRequest.class.isAssignableFrom(type)) {
                args[i] = req;
                continue;
            }
            if (HttpServletResponse.class.isAssignableFrom(type)) {
                args[i] = resp;
                continue;
            }

            String nom = nomParametre(p);
            String brut = req.getParameter(nom);
            try {
                args[i] = convertir(brut, type);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        "Valeur invalide pour le paramètre '" + nom + "' : " + brut);
            }
        }
        return args;
    }

    /** Nom du champ de formulaire : @RequestParam("x") sinon nom de l'argument Java. */
    private String nomParametre(Parameter p) {
        for (Annotation ann : p.getAnnotations()) {
            if (ann.annotationType().getName().equals("mg.itu.annotation.RequestParam")) {
                try {
                    Method valueMethod = ann.annotationType().getMethod("value");
                    Object value = valueMethod.invoke(ann);
                    if (value instanceof String && !((String) value).isEmpty()) {
                        return (String) value;
                    }
                } catch (ReflectiveOperationException e) {
                    throw new IllegalStateException("Impossible de lire @RequestParam", e);
                }
                break;
            }
        }
        if (!p.isNamePresent()) {
            throw new IllegalStateException(
                    "Argument '" + p.getName() + "' sans nom exploitable : ajouter @RequestParam(\"...\") "
                            + "ou compiler la mini-app avec javac -parameters");
        }
        return p.getName();
    }

    /** Conversion String (champ de formulaire) -> type de l'argument. */
    private Object convertir(String v, Class<?> type) {
        boolean vide = (v == null || v.trim().isEmpty());

        if (type == String.class) return v;

        if (type == int.class)      return vide ? 0 : Integer.parseInt(v.trim());
        if (type == Integer.class)  return vide ? null : Integer.valueOf(v.trim());
        if (type == long.class)     return vide ? 0L : Long.parseLong(v.trim());
        if (type == Long.class)     return vide ? null : Long.valueOf(v.trim());
        if (type == double.class)   return vide ? 0.0 : Double.parseDouble(v.trim());
        if (type == Double.class)   return vide ? null : Double.valueOf(v.trim());

        if (type == boolean.class || type == Boolean.class) {
            // une case à cocher envoie "on" si cochée, rien sinon
            return v != null && (v.equalsIgnoreCase("true") || v.equalsIgnoreCase("on") || v.equals("1"));
        }

        throw new IllegalStateException("Type d'argument non supporté : " + type.getName());
    }
}