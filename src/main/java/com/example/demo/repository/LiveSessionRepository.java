package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.LiveSession;


@Repository
public interface LiveSessionRepository extends JpaRepository<LiveSession, Integer>{

	@Query("from LiveSession where date >= curdate()")
	List<LiveSession> getAllUpcoming();
}
