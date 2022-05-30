package denis.controllers;

import denis.model.Case;
import denis.repository.CaseRepository;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;
import java.util.List;

@CrossOrigin(origins = {"https://bot-vue.vercel.app","https://305b-93-170-55-154.eu.ngrok.io"})
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
