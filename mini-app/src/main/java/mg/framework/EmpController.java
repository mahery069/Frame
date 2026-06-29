package mg.framework;

import mg.itu.annotation.Controller.Controller;
import mg.itu.annotation.Url;

@Controller
public class EmpController {

    @Url("/emp/list")
    public String list(){
        return "hello world";
    }

}
