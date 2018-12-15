package com.company.vacation.service;

import org.camunda.bpm.engine.identity.User;

public interface EmailService {

    void sendTaskAssignment(User user, String taskId, String taskName);

    void sendConfirmation();
}
