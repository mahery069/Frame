package mg.itu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation pour indiquer qu'une méthode de contrôleur nécessite une authentification.
 * L'utilisateur doit être connecté avant l'exécution de la méthode.
 * Le nom de la variable de session à vérifier est configuré via l'init-param "auth-session" dans web.xml.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Auth {
}
