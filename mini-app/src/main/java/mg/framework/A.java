package mg.framework;

import mg.itu.annotation.Auth;
import mg.itu.annotation.Controller.Controller;
import mg.itu.annotation.RequestMapping;
import mg.itu.annotation.RequestParam;
import mg.itu.annotation.WebAPI.WebAPI;
import mg.itu.frame.ModelAndView;
import mg.itu.frame.MySession;

@Controller
public class A {

    @RequestMapping(url = "/bonjour")
    public ModelAndView bonjour() {
        ModelAndView mv = new ModelAndView();
        mv.setUrl("bonjour");
        mv.addObject("nom", "Mahery");
        return mv;
    }

    @RequestMapping(url = "/salut", method = "POST")
    public String direSalut() {
        return "Salut";
    }

    @WebAPI
    @RequestMapping(url = "/api/employee")
    public Employee getEmployee() {
        return new Employee(1, "Mahery", "Developpeur Java");
    }

    // ---------------- SPRINT 7 : de la vue vers le contrôleur ----------------
    /**
     * GET /form : affiche le formulaire (vue form.jsp).
     */
    @RequestMapping(url = "/form")
    public ModelAndView formulaire() {
        return new ModelAndView("form");
    }

    /**
     * POST /save : le framework crée l'Employe et le Diplome et les remplit
     * à partir du formulaire (employe.nom, employe.prenom, diplome.nom...).
     */
    @RequestMapping(url = "/save", method = "POST")
    public ModelAndView save(Employe employe, Diplome diplome) {
        ModelAndView mv = new ModelAndView("resultat");
        mv.addObject("employe", employe);
        mv.addObject("diplome", diplome);
        return mv;
    }

    // ---------------- SPRINT 8 : upload de fichiers ----------------
    /**
     * GET /employe : formulaire avec les scans de la CIN (vue employe-form.jsp).
     */
    @RequestMapping(url = "/employe")
    public ModelAndView formulaireEmploye() {
        return new ModelAndView("employe-form");
    }

    /**
     * POST /employe/save : le framework remplit nom, prenom et les MapFile
     * rectoCin / versoCin à partir du formulaire multipart.
     * Le stockage des fichiers (dossier, BLOB...) reste à faire ici si besoin.
     */
    @RequestMapping(url = "/employe/save", method = "POST")
    public ModelAndView saveEmploye(Employe employe) {
        ModelAndView mv = new ModelAndView("employe-resultat");
        mv.addObject("employe", employe);
        return mv;
    }

    // ---------------- SPRINT 10 : authentification par annotation @Auth ----------------
    /**
     * GET /login : formulaire de connexion (vue login.jsp).
     */
    @RequestMapping(url = "/login")
    public ModelAndView loginForm() {
        return new ModelAndView("login");
    }

    /**
     * POST /login : authentification et stockage en session via MySession.
     * Le framework synchronisera automatiquement MySession -> HttpSession.
     */
    @RequestMapping(url = "/login", method = "POST")
    public ModelAndView login(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             MySession session) {
        // Simulation d'authentification
        if ("admin".equals(username) && "admin".equals(password)) {
            session.set("user", username);
            ModelAndView mv = new ModelAndView("dashboard");
            mv.addObject("username", username);
            return mv;
        } else {
            ModelAndView mv = new ModelAndView("login");
            mv.addObject("error", "Identifiants incorrects");
            return mv;
        }
    }

    /**
     * GET /dashboard : page protégée par @Auth.
     * Le framework vérifie automatiquement l'authentification avant l'exécution.
     */
    @Auth
    @RequestMapping(url = "/dashboard")
    public ModelAndView dashboard(MySession session) {
        String username = (String) session.get("user");
        ModelAndView mv = new ModelAndView("dashboard");
        mv.addObject("username", username);
        return mv;
    }

    /**
     * GET /logout : déconnexion en supprimant les attributs de MySession.
     */
    @RequestMapping(url = "/logout")
    public ModelAndView logout(MySession session) {
        session.getAttributes().clear();
        return new ModelAndView("login");
    }

    /**
     * GET /api/profil : API protégée par @Auth.
     * Le framework vérifie automatiquement l'authentification avant l'exécution.
     */
    @Auth
    @WebAPI
    @RequestMapping(url = "/api/profil")
    public String getProfil(MySession session) {
        String username = (String) session.get("user");
        return "Profil de " + username;
    }

    public static class Employee {

        private int id;
        private String nom;
        private String poste;

        public Employee(int id, String nom, String poste) {
            this.id = id;
            this.nom = nom;
            this.poste = poste;
        }
    }
}
