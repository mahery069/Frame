package mg.itu.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Lie un argument de méthode du contrôleur à un paramètre de la requête
 * (champ de formulaire ou paramètre d'URL).
 *
 * Exemple : save(@RequestParam("nom") String nom, @RequestParam("age") int age)
 *
 * Si value() est vide, le nom de l'argument Java est utilisé
 * (nécessite la compilation avec l'option javac -parameters).
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RequestParam {
    String value() default "";
} 
    

