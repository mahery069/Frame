package mg.itu.frame;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import mg.itu.annotation.Controller.Controller;
import mg.itu.annotation.RequestMapping;
import mg.itu.utils.Utils;

@WebListener
public class FrontControllerListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext context = sce.getServletContext();

        List<String> controllers = new ArrayList<>();
        List<FrontControllerServlet.RouteInfo> routes = new ArrayList<>();

        String basePackages = context.getInitParameter("base-package");

        if (basePackages != null) {

            for (String pkg : basePackages.split(";")) {

                List<String> found =
                        Utils.findClassesByAnnotation(pkg.trim(), Controller.class);

                controllers.addAll(found);

                for (String className : found) {

                    try {

                        Class<?> clazz = Class.forName(className);

                        for (Method method : clazz.getDeclaredMethods()) {

                            if (method.isAnnotationPresent(RequestMapping.class)) {

                                RequestMapping mapping =
                                        method.getAnnotation(RequestMapping.class);

                                routes.add(
                                        new FrontControllerServlet.RouteInfo(
                                                mapping.url(),
                                                mapping.method(),
                                                className,
                                                method.getName()
                                        )
                                );
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        context.setAttribute("routes", routes);
        context.setAttribute("mappingUrls", routes);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}