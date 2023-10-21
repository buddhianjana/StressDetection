package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.DoctorDetails;

public interface DoctorDetailsRepo extends JpaRepository<DoctorDetails, Long>{
	public DoctorDetails findByEmail(String email);

}
