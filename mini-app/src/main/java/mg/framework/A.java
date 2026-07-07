package mg.framework;

import mg.itu.annotation.Controller.Controller;
import mg.itu.annotation.RequestMapping;
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
}