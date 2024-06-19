package com.port.petfit.user.member.appointment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

	Appointment findByAppointmentId(Integer appointmentId);
	
    List<Appointment> findByUser_userId(String userId);
	
}