package mg.framework;

import mg.itu.annotation.Controller.Controller;
import mg.itu.annotation.RequestMapping;
import mg.itu.annotation.WebAPI.WebAPI;
import mg.itu.frame.ModelAndView;

@Controller
public class A{
    
    @RequestMapping(url = "/bonjour")
public ModelAndView bonjour(){

    ModelAndView mv=new ModelAndView();

    mv.setUrl("bonjour");

    mv.addObject("nom","Mahery");

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