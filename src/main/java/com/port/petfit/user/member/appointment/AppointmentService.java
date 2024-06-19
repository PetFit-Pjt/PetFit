package com.port.petfit.user.member.appointment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
    // 기본 생성자 추가
    public AppointmentService() {    	
    }
	
    public void createAppointment(Appointment appointment) {
    	appointmentRepository.save(appointment);
    }
    
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public void updateAppointmentStatus(Integer appointmentId, boolean isConfirmed) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid appointment Id:" + appointmentId));

        if (isConfirmed) {
            appointment.setApproved(true);
        } else {
            appointmentRepository.delete(appointment);
        }
        appointmentRepository.save(appointment);
    }
}

