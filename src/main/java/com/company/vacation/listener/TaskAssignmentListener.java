package com.company.vacation.listener;

import com.company.vacation.service.EmailService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskAssignmentListener implements TaskListener {

    private final EmailService emailService;

    @Autowired
    public TaskAssignmentListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void notify(DelegateTask delegateTask) {

        String assignee = delegateTask.getAssignee();

        if (assignee != null) {
            IdentityService identityService = Context.getProcessEngineConfiguration().getIdentityService();
            User user = identityService.createUserQuery()
                    .userId(assignee)
                    .singleResult();
            String taskId = delegateTask.getId();
            String taskName = delegateTask.getName();

            emailService.sendTaskAssignment(user, taskId, taskName);
        }
    }
}
