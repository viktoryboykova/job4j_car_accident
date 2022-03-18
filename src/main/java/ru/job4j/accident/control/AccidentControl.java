package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentRepository;
import ru.job4j.accident.repository.AccidentTypeRepository;
import ru.job4j.accident.repository.RuleRepository;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Controller
public class AccidentControl {
    private final AccidentRepository accidentRepository;
    private final AccidentTypeRepository accidentTypeRepository;
    private final RuleRepository ruleRepository;

    public AccidentControl(AccidentRepository accidentRepository, AccidentTypeRepository accidentTypeRepository, RuleRepository ruleRepository) {
        this.accidentRepository = accidentRepository;
        this.accidentTypeRepository = accidentTypeRepository;
        this.ruleRepository = ruleRepository;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("types", accidentTypeRepository.findAll());
        model.addAttribute("rules", ruleRepository.findAll());
        return "accident/create";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") int id, Model model) {
        model.addAttribute("accident", accidentRepository.findById(id).orElse(null));
        model.addAttribute("types", accidentTypeRepository.findAll());
        model.addAttribute("rules", ruleRepository.findAll());
        return "accident/edit";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        Accident updatedAccident = accidentRepository.findAccidentById(accident.getId());
        String[] rIds = req.getParameterValues("rIds");
        updatedAccident.setType(accidentTypeRepository.findById(accident.getType().getId()).orElse(null));
        updatedAccident.getRules().clear();
        Set<Rule> rules = new HashSet<>();
        Arrays.stream(rIds).forEach(rId -> rules.add(ruleRepository.findById(Integer.parseInt(rId)).orElse(null)));
        for (Rule rule : rules) {
            updatedAccident.getRules().add(rule);
        }
        updatedAccident.setName(accident.getName());
        updatedAccident.setText(accident.getText());
        updatedAccident.setAddress(accident.getAddress());
        accidentRepository.save(updatedAccident);
        return "redirect:/";
    }

}
