package org.aldogioia.templatesecurity.service.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import org.aldogioia.templatesecurity.data.dto.AuthResponseDto;
import org.aldogioia.templatesecurity.data.dto.CustomerCreateDto;

public interface AuthService {
    AuthResponseDto signIn(String phoneNumber, String password);
    void signUp(CustomerCreateDto createCustomerDto);
    String refresh(HttpServletRequest request);
    void signOut(HttpServletRequest request);
}
