package denis.controllers;

import denis.model.Case;
import denis.repository.CaseRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin (origins = {"https://testbotforresidents.herokuapp.com","https://bot-vue.vercel.app","http://localhost:3000"})
@RestController
public class CaseController {

    private final CaseRepository caseRepository;

    public CaseController(CaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    @GetMapping("/allcases")
    public List<Case> findUserCases(@RequestParam Long userId){
        return caseRepository.findByUserId(userId);
    }
}