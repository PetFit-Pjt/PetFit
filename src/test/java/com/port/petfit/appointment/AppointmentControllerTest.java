package com.port.petfit.appointment;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddAppointment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/appointments/add")
                .contentType("application/json")
                .content("{\"appointmentId\":\"1\", \"userId\":\"user123\", \"hospitalId\":\"hospital456\", \"petNum\":1, \"appointmentDateTime\":\"2024-04-17T10:00:00\", \"status\":\"0\", \"medicalNotes\":\"Checkup\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAppointmentsByUserId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/appointments/user/{userId}", "user123"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAppointmentById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/appointments/{appointmentId}", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testConfirmAppointment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/appointments/{appointmentId}/confirm", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCancelAppointment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/appointments/{appointmentId}/cancel", "1"))
                .andExpect(status().isOk());
    }
}
