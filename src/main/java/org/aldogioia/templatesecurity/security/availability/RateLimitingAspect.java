package org.aldogioia.templatesecurity.security.availability;

import com.google.common.util.concurrent.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Aspetto per la limitazione del rate delle richieste ai controller REST.
 * <p>
 * Intercetta le chiamate ai metodi dei controller annotati con {@link RateLimit}
 * e applica una limitazione del numero di richieste per secondo tramite {@link RateLimiter}.
 * Se il limite viene superato, viene sollevata una {@link RuntimeException}.
 */
@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitingAspect {
    /**
     * Configurazione per il bean {@link ModelMapper}.
     * <p>
     * Definisce e configura un'istanza di {@link ModelMapper} per la mappatura automatica tra oggetti.
     * Abilita il matching dei campi e imposta il livello di accesso ai campi pubblici.
     */
    private final ConcurrentHashMap<Method, RateLimiter> rateLimiterMap = new ConcurrentHashMap<>();

    /**
     * Intercetta le chiamate ai metodi dei controller REST e applica il rate limiting
     * se il metodo o la classe sono annotati con {@link RateLimit}.
     *
     * @param joinPoint il punto di giunzione dell'aspetto
     * @return il risultato dell'invocazione del metodo target
     * @throws Throwable se si verifica un errore durante l'esecuzione del metodo target
     */
    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object limitRequestRate(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method= ((MethodSignature) joinPoint.getSignature()).getMethod();
        RateLimit rateLimited = method.getAnnotation(RateLimit.class);
        if (rateLimited == null) {
            rateLimited = joinPoint.getTarget().getClass().getAnnotation(RateLimit.class);
        }
        if (rateLimited != null) {
            final double permitsPerSecond = rateLimited.permitsPerSecond();
            RateLimiter rateLimiter = rateLimiterMap.computeIfAbsent(method, key -> RateLimiter.create(permitsPerSecond));

            if (rateLimiter.tryAcquire()) {
                return joinPoint.proceed();
            } else {
                throw new RuntimeException("Too many requests");
            }
        } else {
            return joinPoint.proceed();
        }
    }
}
