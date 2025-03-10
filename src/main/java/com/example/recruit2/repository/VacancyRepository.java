package com.example.recruit2.repository;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.recruit2.models.Vacancy;

public interface VacancyRepository extends JpaRepository<Vacancy,Integer> {
        Page<Vacancy> findAll(Pageable pageable);
        List<Vacancy> findAll(Sort sort);
}
