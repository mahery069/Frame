package mg.itu.frame;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Factory pour la création et la gestion des instances de contrôleurs.
 * 
 * SPRINT 11 : TOUS les contrôleurs (@Controller) sont des singletons.
 * Une seule instance par classe de contrôleur est créée à la première requête
 * et partagée entre toutes les requêtes suivantes et tous les navigateurs.
 * 
 * La création des singletons est thread-safe (synchronized) pour éviter les créations multiples
 * en cas de requêtes simultanées.
 */
public class ControllerFactory {

    // Cache des singletons : nom de classe -> instance unique
    private static final Map<String, Object> singletons = new ConcurrentHashMap<>();

    /**
     * Retourne l'instance unique du contrôleur de la classe donnée.
     * Si l'instance n'existe pas encore, elle est créée de manière thread-safe.
     * 
     * @param clazz la classe du contrôleur
     * @return l'instance unique du contrôleur
     * @throws IllegalStateException si l'instanciation échoue
     */
    public static Object get(Class<?> clazz) {
        String className = clazz.getName();
        Object instance = singletons.get(className);

        if (instance != null) {
            // Instance déjà créée : la réutiliser
            return instance;
        }

        // Instance non encore créée : la créer de manière thread-safe
        synchronized (ControllerFactory.class) {
            // Double-check : une autre thread pourrait l'avoir créée entre-temps
            instance = singletons.get(className);
            if (instance == null) {
                instance = createInstance(clazz);
                singletons.put(className, instance);
            }
        }

        return instance;
    }

    /**
     * Crée une nouvelle instance de la classe donnée.
     * 
     * @param clazz la classe à instancier
     * @return la nouvelle instance
     * @throws IllegalStateException si l'instanciation échoue
     */
    private static Object createInstance(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(
                "Impossible d'instancier " + clazz.getName() + " : il faut un constructeur sans argument", e);
        }
    }

    /**
     * Vide le cache des singletons.
     * À appeler lors de l'arrêt de l'application (contextDestroyed).
     */
    public static void clear() {
        singletons.clear();
    }
}
