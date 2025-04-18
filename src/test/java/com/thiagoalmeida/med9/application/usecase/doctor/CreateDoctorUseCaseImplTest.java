package com.thiagoalmeida.med9.application.usecase.doctor;

import com.thiagoalmeida.med9.application.dto.doctor.DoctorRequest;
import com.thiagoalmeida.med9.domain.entity.Doctor;
import com.thiagoalmeida.med9.domain.entity.User;
import com.thiagoalmeida.med9.domain.enums.Role;
import com.thiagoalmeida.med9.domain.repository.DoctorRepository;
import com.thiagoalmeida.med9.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateDoctorUseCaseImplTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateDoctorUseCaseImpl createDoctorUseCase;

    private DoctorRequest request;
    private Doctor doctor;
    private User user;

    @BeforeEach
    void setUp() {
        request = new DoctorRequest(
            "Dr. House",
            "123456",
            "Diagnostics",
            "11999999999",
            "house@princeton.com",
            "house",
            "password123",
            "Princeton Hospital"
        );

        doctor = new Doctor(
            1L,
            request.name(),
            request.crm(),
            request.specialty(),
            request.phone(),
            request.email()
        );

        user = new User(
            1L,
            request.username(),
            "encoded_password",
            request.name(),
            request.email(),
            Role.DOCTOR,
            request.address()
        );
    }

    @Test
    void shouldCreateDoctorSuccessfully() {
        // Arrange
        when(doctorRepository.findByCrm(request.crm())).thenReturn(Optional.empty());
        when(doctorRepository.findByEmail(request.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.password())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

        // Act
        Doctor result = createDoctorUseCase.execute(request);

        // Assert
        assertNotNull(result);
        assertEquals(request.name(), result.name());
        assertEquals(request.crm(), result.crm());
        assertEquals(request.specialty(), result.specialty());
        assertEquals(request.email(), result.email());
        
        verify(doctorRepository).findByCrm(request.crm());
        verify(doctorRepository).findByEmail(request.email());
        verify(passwordEncoder).encode(request.password());
        verify(userRepository).save(any(User.class));
        verify(doctorRepository).save(any(Doctor.class));
    }

    @Test
    void shouldThrowExceptionWhenCrmAlreadyExists() {
        // Arrange
        when(doctorRepository.findByCrm(request.crm())).thenReturn(Optional.of(doctor));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> createDoctorUseCase.execute(request)
        );
        assertEquals("CRM já cadastrado", exception.getMessage());
        
        verify(doctorRepository).findByCrm(request.crm());
        verify(doctorRepository, never()).save(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // Arrange
        when(doctorRepository.findByCrm(request.crm())).thenReturn(Optional.empty());
        when(doctorRepository.findByEmail(request.email())).thenReturn(Optional.of(doctor));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> createDoctorUseCase.execute(request)
        );
        assertEquals("Email já cadastrado", exception.getMessage());
        
        verify(doctorRepository).findByCrm(request.crm());
        verify(doctorRepository).findByEmail(request.email());
        verify(doctorRepository, never()).save(any());
        verify(userRepository, never()).save(any());
    }
}
