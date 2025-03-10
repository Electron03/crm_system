package com.example.recruit2.controllers;

import org.springframework.web.bind.annotation.RestController;
import com.example.recruit2.DTO.*;
import com.example.recruit2.models.Vacancy;
import com.example.recruit2.repository.VacancyRepository;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@RestController
public class VacancyController {


        private final VacancyRepository vacancyRepository;
        public VacancyController(VacancyRepository vacancyRepository){
            this.vacancyRepository= vacancyRepository;
        }
        @PostMapping("/addvacancy")
       public ResponseEntity<String> addVacancy(@RequestBody VacancyDTO entity) {
            Vacancy vacancy = new Vacancy();
            vacancy.setVacancyTitle(entity.getVacancyTitle());
            vacancy.setAge(entity.getAge());
            vacancy.setRequirements(entity.getRequirements());
            vacancy.setConditions(entity.getConditions());  // Используем новое поле "conditions"
            vacancy.setSkills(entity.getSkills());
            vacancy.setSalary(entity.getSalary());
            vacancy.setEducation(entity.getEducation());  // Маппим новое поле "education"
            vacancy.setContactEmail(entity.getContactEmail());
            vacancy.setContactPhone(entity.getContactPhone());
            vacancy.setDatePublish(entity.getDatePublish());
            vacancy.setDetails(entity.getDetails());
            vacancyRepository.save(vacancy);
            return ResponseEntity.ok("ok");
        }
        @PostMapping("/updatevacacy")
        public ResponseEntity<String> updateVacancy(@RequestBody Vacancy entity) {
            vacancyRepository.save(entity);    
            return ResponseEntity.ok("ok");
        }
        @GetMapping("/getvacancy")
        public Page<Vacancy>getVacancy(Pageable pageable) {
            return vacancyRepository.findAll(pageable);
        }
        
        @PostMapping("/dellvacancy")
        public ResponseEntity<String> dellVacancy(@RequestBody Vacancy entity) {
            vacancyRepository.delete(entity);
            return ResponseEntity.ok("ok");
        }
        @GetMapping("/vacancies")
        public List<VacancyLimitedDTO> getVacancyTitles() {
        List<Vacancy> vacancies = vacancyRepository.findAll(); // Получаем все вакансии
            return vacancies.stream()
            .map(this::convertToLimitedDTO) // Преобразуем в DTO
            .collect(Collectors.toList());
            }
        @GetMapping("/sortvacancies")
        public List<Vacancy> getVacancies(@RequestParam(required = false, defaultValue = "new") String sort) {
            return getSortedVacancies(sort);
        }
        public VacancyLimitedDTO convertToLimitedDTO(Vacancy vacancy) {
            VacancyLimitedDTO dto = new VacancyLimitedDTO();
            dto.setVacancyTitle(vacancy.getVacancyTitle());
            return dto;
        }
        public List<Vacancy> getSortedVacancies(String sort) {
            Sort.Direction direction = sort.equalsIgnoreCase("new") ? Sort.Direction.DESC : Sort.Direction.ASC;
            return vacancyRepository.findAll(Sort.by(direction, "datePublish"));
        }

}
