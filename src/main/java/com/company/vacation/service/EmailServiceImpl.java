package com.company.vacation.service;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private static final String HOST = "mail.example.org";
    private static final String USER = "admin@example.org";
    private static final String PWD = "toomanysecrets";

    @Override
    public void sendTaskAssignment(User user, String taskId, String taskName) {
        if (user != null) {
            String recipient = user.getEmail();

            if (recipient != null && !recipient.isEmpty()) {

                Email email = new SimpleEmail();
                email.setHostName(HOST);
                email.setAuthentication(USER, PWD);

                try {
                    email.setFrom("noreply@camunda.org");
                    email.setSubject("Task assigned: " + taskName);
                    email.setMsg("Please complete: http://localhost:8080/camunda/app/tasklist/default/#/task/" + taskId);
                    email.addTo(recipient);
                    email.send();
                } catch (EmailException e) {

                }
            }
        }
    }

    @Override
    public void sendConfirmation() {
        //TODO
    }
}
