package mg.framework;

import mg.itu.annotation.Controller.Controller;
import mg.itu.annotation.Url;

@Controller
public class A{
    
    @Url("/bonjour")
    public String direBonjour() {
        return "Bonjour";
    }
    
    @Url("/salut")
    public String direSalut() {
        return "Salut";
    }
}