package mg.itu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation pour indiquer qu'une méthode de contrôleur nécessite une authentification.
 * L'utilisateur doit être connecté avant l'exécution de la méthode.
 * Le nom de la variable de session à vérifier est configuré via l'init-param "auth-session" dans web.xml.
 * 
 * SPRINT 10-BIS : peut spécifier des rôles requis pour accéder à la méthode.
 * Si des rôles sont spécifiés, l'utilisateur doit avoir au moins l'un de ces rôles.
 * Le nom de la variable de session pour les rôles est configuré via l'init-param "auth-role" dans web.xml.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Auth {
    /**
     * Rôles requis pour accéder à la méthode.
     * Si vide, seule l'authentification est requise.
     * Si non vide, l'utilisateur doit avoir au moins l'un de ces rôles.
     */
    String[] value() default {};
}
