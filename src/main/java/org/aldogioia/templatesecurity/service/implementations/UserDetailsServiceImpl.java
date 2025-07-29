package org.aldogioia.templatesecurity.service.implementations;

import lombok.RequiredArgsConstructor;
import org.aldogioia.templatesecurity.data.dao.UserDao;
import org.aldogioia.templatesecurity.data.entities.User;
import org.aldogioia.templatesecurity.security.CustomUserDetails;
import org.aldogioia.templatesecurity.security.exception.customException.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDao userDao;
    /**
     * Carica un utente in base al numero di telefono.
     *
     * @param phoneNumber il numero di telefono dell'utente
     * @return i dettagli dell'utente
     * @throws UsernameNotFoundException se l'utente non viene trovato
     */
    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        User user = userDao.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new EntityNotFoundException("User not found with phone number: " + phoneNumber));
        return new CustomUserDetails(user);
    }

    /**
     * Ottiene il numero di telefono dell'utente corrente.
     *
     * @return il numero di telefono dell'utente corrente
     */
    public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
