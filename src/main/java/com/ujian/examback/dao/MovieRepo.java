package com.ujian.examback.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ujian.examback.entity.Movie;

public interface MovieRepo extends JpaRepository<Movie, Integer> {

}
