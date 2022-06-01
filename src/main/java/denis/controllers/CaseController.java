package denis.controllers;

import denis.model.Case;
import denis.repository.CaseRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = {"https://bot-vue.vercel.app","https://83f0-104-28-224-95.eu.ngrok.io"})
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
