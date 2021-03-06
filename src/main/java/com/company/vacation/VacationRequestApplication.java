package com.company.vacation;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableProcessApplication
public class VacationRequestApplication {

    public static void main(String... args) {
        SpringApplication.run(VacationRequestApplication.class, args);
    }

    @EventListener
    private void processPostDeploy(PostDeployEvent event) {
        // createUser
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = processEngine.getIdentityService();
        User user = identityService.newUser("john");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@company.com");
        user.setPassword("superSecret");
        identityService.saveUser(user);
        identityService.createMembership("john", "camunda-admin");

        Map<String, Object> params = new HashMap<>();
        params.put("employeeId", user.getId());
        params.put("firstName", user.getFirstName());
        params.put("lastName", user.getLastName());
        params.put("numberOfDays", 14);
        params.put("reason", "I need a break!");

        // start process
        RuntimeService runtimeService = processEngine.getRuntimeService();
        runtimeService.startProcessInstanceByKey("vacationRequest", params);
    }
}