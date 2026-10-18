package mg.framework;

import mg.itu.annotation.Controller.Controller;
import mg.itu.annotation.RequestMapping;
import mg.itu.annotation.WebAPI.WebAPI;
import mg.itu.frame.ModelAndView;

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
