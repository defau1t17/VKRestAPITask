package org.vktask.vkrestapitask.aspect;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.vktask.vkrestapitask.entity.Audit;
import org.vktask.vkrestapitask.entity.User;
import org.vktask.vkrestapitask.service.AuditService;
import org.vktask.vkrestapitask.service.UserService;

import java.time.LocalDate;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private Audit audit = new Audit();

    private final UserService userService;

    private final AuditService auditService;

    @Pointcut("execution(* org.vktask.vkrestapitask.service.AuthenticationService.getAuthentication(..))")
    public void declareAuth() {
    }

    @Around(value = "declareAuth()")
    public Object get(ProceedingJoinPoint joinPoint) throws Throwable {
        audit.setLocalDate(LocalDate.now());
        Object result = null;
        HttpServletRequest request = null;
        try {
            request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            result = joinPoint.proceed();
            return result;
        } catch (Exception ex) {
            audit.setPermission(false);
            throw ex;
        } finally {
            try {
                if (result != null) {
                    HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
                    if (response.getStatus() == 200) {
                        audit.setId(0);
                        Optional<User> optionalUser = userService.findUserByToken(request.getHeader("AUTH-TOKEN"));
                        audit.setRequestParams(request.getQueryString());
                        audit.setUser(optionalUser.get());
                        audit.setPermission(true);
                    } else {
                        audit.setPermission(false);
                    }
                }
            } finally {
                auditService.save(audit);
            }
        }
    }
}
