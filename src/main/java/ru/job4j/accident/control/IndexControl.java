package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.repository.AccidentMem;

import java.util.Collection;
import java.util.List;

@Controller
public class IndexControl {
    @GetMapping("/")
    public String index(Model model) {
        Collection<Accident> accidents = new AccidentMem().getAllAccidents();
        model.addAttribute("accidents", accidents);
        return "index";
    }
}
