package com.example.recruit2.controllers;

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.recruit2.DTO.CandidateDTO;
import com.example.recruit2.DTO.CandidateLimitedDTO;
import com.example.recruit2.models.Candidate;
import com.example.recruit2.repository.CandidateRepository;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class userControllers {
  
    @Autowired
    private CandidateRepository candidateRepository;

    public userControllers(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }


    @PostMapping("/addcandidate")
   public ResponseEntity<String> addCandidate(@Valid @RequestBody CandidateDTO candidateDTO ) {
    Candidate candidate = new Candidate();
    candidate.setFullname(candidateDTO.getFullname());
    candidate.setSkills(candidateDTO.getSkills());
    candidate.setEducation(candidateDTO.getEducation());
    candidate.setPhone(candidateDTO.getPhone());
    candidate.setPhone2(candidateDTO.getPhone2());
    candidate.setStatus(candidateDTO.getStatus());
    candidate.setMeetDate(candidateDTO.getMeetDate());
    candidate.setDateNote(candidateDTO.getDateNote());
    candidate.setDateRec(candidateDTO.getDateRec());
    candidate.setVacancy(candidateDTO.getVacancy());
    candidate.setEmail(candidateDTO.getEmail());
    candidate.setDetail(candidateDTO.getDetail());
    candidate.setExperience(candidateDTO.getExperience());
        
        candidateRepository.save(candidate);
        return ResponseEntity.ok("");
    }
    @PostMapping("/chancandidate")
    public ResponseEntity<String> chanCandidate(@RequestBody Candidate candidate ) {
         candidateRepository.save(candidate);
         return ResponseEntity.ok("");
     }
     @PostMapping("/dellcandidate")
     public ResponseEntity<String> dellCandidate(@RequestBody Candidate candidate ) {
          candidateRepository.delete(candidate);
          return ResponseEntity.ok("");
      }

      @CrossOrigin(origins = "*")
    @GetMapping("/getcandidats")
    public Page<Candidate> getcandidats(
            @RequestParam(defaultValue = "new") String sort,
            @PageableDefault(sort = "dateRec", direction = Sort.Direction.DESC)  Pageable pageable) {

        Sort.Direction direction = sort.equalsIgnoreCase("new") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable sortedPageable = PageRequest.of(
            pageable.getPageNumber(), 
            pageable.getPageSize(), 
            Sort.by(direction, "dateRec")
        );
        return candidateRepository.findAll(sortedPageable);
    }
    @GetMapping("/searchcandidate")
    public List<Candidate> searchCandidates(@RequestParam String query) {
        if (query.startsWith("@")) {
            String nameQuery = query.substring(1); // Убираем '@'
            return candidateRepository.findByFullnameContainingIgnoreCase(nameQuery);
        } else if (query.startsWith("$")) {
            String vacancyQuery = query.substring(1); // Убираем '$'
            return candidateRepository.findByVacancyContainingIgnoreCase(vacancyQuery);
        } else {
            return List.of(); // Если нет '@' или '$', возвращаем пустой список
        }
    }
        @GetMapping("/candidateslimited")
        public List<CandidateLimitedDTO> getLimitedCandidates() {
            List<Candidate> candidates = candidateRepository.findAll(); 
            return candidates.stream()
                .map(this::convertToLimitedDTO) 
                .collect(Collectors.toList());
                }
     

@PutMapping("/updateMeetDate")
    public String updateMeetDate1(@RequestParam String fullname, @RequestParam String meetDate) {
        boolean success = updateMeetDate(fullname, meetDate);
        return success ? "Meet date updated successfully" : "Candidate not found";
    }

    public CandidateLimitedDTO convertToLimitedDTO(Candidate candidate) {
    CandidateLimitedDTO dto = new CandidateLimitedDTO();
    dto.setFullname(candidate.getFullname());
    dto.setMeetdate(candidate.getMeetDate());
    dto.setDateNote(candidate.getDateNote());
    return dto;
    }
  public boolean updateMeetDate(String fullname, String meetDate) {
        int updatedRows = candidateRepository.updateMeetDateByFullname(meetDate, fullname);
        return updatedRows > 0; // Возвращает true, если что-то обновилось
    }

    //  @GetMapping("getcandidat")
    //  public ResponseEntity<Candidate> getcandidat(@RequestParam int param) {
    //      return ResponseEntity.ok(candidateRepository.getById(param));
    //  }

     
}
