package com.company.vacation.diagram;

import com.company.vacation.service.EmailService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailConfirmationService implements JavaDelegate {

    private final EmailService emailService;

    @Autowired
    public EmailConfirmationService(EmailService emailService) {
        this.emailService = emailService;
    }
    @Override
    public void execute(DelegateExecution execution) {

        emailService.sendConfirmation();
    }
}
