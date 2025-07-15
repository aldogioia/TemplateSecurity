package org.aldogioia.templatesecurity.service.implementations;

import jakarta.servlet.http.HttpServletRequest;
import org.aldogioia.templatesecurity.data.dto.AuthResponseDto;
import org.aldogioia.templatesecurity.data.dto.CreateCustomerDto;
import org.aldogioia.templatesecurity.service.interfaces.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public AuthResponseDto signIn(String phoneNumber, String password) {
        return null;
    }

    @Override
    public void signUp(CreateCustomerDto createCustomerDto) {

    }

    @Override
    public String refresh(HttpServletRequest request) {
        return "";
    }

    @Override
    public void signOut(HttpServletRequest request) {

    }
}
