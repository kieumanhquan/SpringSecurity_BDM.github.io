package com.example.springsecurity.controller;

import com.example.springsecurity.registration.RegistrationRequest;
import com.example.springsecurity.registration.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @RequestMapping(value = "/sign_up", method = RequestMethod.GET)
    public String formAdd() {
        return "registration";
    }

    @RequestMapping(value = "/sign_up", method = RequestMethod.POST)
    public String postAdd(@Valid RegistrationRequest registrationRequest) {
        System.out.println(registrationRequest);
        registrationService.register(registrationRequest);
        String msg="Please confirm email to active";
        return "redirect:/api/v1/registration/response"+"?message="+msg;
    }

    @RequestMapping(value = "/response", method = RequestMethod.GET)
    public String showResponse(@RequestParam String message,ModelMap model) {
        model.put("message", message);
        return "response";
    }

    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

}