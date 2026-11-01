package test;

import mg.itu.annotation.Controller.Controller;
import mg.itu.annotation.RequestMapping;
import mg.itu.frame.ModelAndView;
import mg.itu.frame.MySession;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Contrôleur de test pour Sprint 11 : singleton pour tous les contrôleurs.
 * 
 * SPRINT 11 : tous les contrôleurs (@Controller) sont des singletons.
 * Une seule instance est partagée entre toutes les requêtes et tous les navigateurs.
 * 
 * Ce contrôleur démontre :
 * - compteurGlobal : partagé entre tous les utilisateurs (attribut du singleton)
 * - compteurSession : propre à chaque utilisateur (via MapSession)
 */
@Controller
public class CompteurController {

    // Compteur global : partagé entre tous les utilisateurs (attribut du singleton)
    private AtomicInteger compteurGlobal = new AtomicInteger(0);

    @RequestMapping(url = "/compteur")
    public ModelAndView compteur(MySession session) {
        // Incrémenter le compteur global (partagé)
        int global = compteurGlobal.incrementAndGet();
        
        // Incrémenter le compteur de session (propre à l'utilisateur)
        Integer sessionCount = (Integer) session.get("visites");
        if (sessionCount == null) {
            sessionCount = 0;
        }
        sessionCount++;
        session.set("visites", sessionCount);
        
        ModelAndView mv = new ModelAndView("compteur");
        mv.addObject("instanceId", System.identityHashCode(this));
        mv.addObject("compteurGlobal", global);
        mv.addObject("compteurSession", sessionCount);
        return mv;
    }
}
