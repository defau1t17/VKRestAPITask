package org.vktask.vkrestapitask.aspect;

import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.vktask.vkrestapitask.entity.Audit;
import org.vktask.vkrestapitask.service.UserService;

import java.time.LocalDate;
import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private Audit audit = new Audit();

    private final UserService userService;

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *) " +
            "&& !within(org.vktask.vkrestapitask.controller.RegistrationRestApiController) " +
            "&& !within(org.vktask.vkrestapitask.controller.AuthenticationRestApiController)")
    public void declare() {
    }

    @Around(value = "declare()")
    public Object restApiMethodsExecution(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        System.out.println(response.getStatus());
        System.out.println(audit);
        Object[] args = proceedingJoinPoint.getArgs();
        String requestParams = args != null ? Arrays.toString(args) : "No parameters";
        return proceedingJoinPoint.proceed();
    }


}
