package mg.framework;

import mg.itu.annotation.Controller.Controller;
import mg.itu.annotation.RequestMapping;

@Controller
public class A{
    
    @RequestMapping(url = "/bonjour", method = "GET")
    public String direBonjour() {
        return "Bonjour";
    }
    
    @RequestMapping(url = "/salut", method = "POST")
    public String direSalut() {
        return "Salut";
    }
}