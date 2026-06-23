package mg.framework;

import mg.itu.annotation.Controller.Controller;
import mg.itu.annotation.Controller.Url;

@Controller
public class A{
    
    @Url("/test")
    public String test() {
        return "Hello depuis la méthode test()";
    }
    
    @Url("/bonjour")
    public String bonjour() {
        return "Bonjour le monde!";
    }
}