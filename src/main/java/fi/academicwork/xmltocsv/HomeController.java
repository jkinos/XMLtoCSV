package fi.academicwork.xmltocsv;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController {
    @RequestMapping(value = "/")
	public String index() {
		return "index";
	}

}
