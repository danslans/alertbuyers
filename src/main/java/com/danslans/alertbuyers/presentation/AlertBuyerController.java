package com.danslans.alertbuyers.presentation;

import com.danslans.alertbuyers.presentation.delegate.IAlertBuyerDelegate;
import com.danslans.alertbuyers.presentation.dto.RequestAlertBuyerDto;
import com.danslans.alertbuyers.presentation.dto.ResponseAlertBuyerDto;
import com.danslans.alertbuyers.presentation.dto.ResponseNotificationDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@CrossOrigin(origins = "*")
@Validated
public class AlertBuyerController {
    IAlertBuyerDelegate alertBuyerDelegate;

    @Autowired
    public AlertBuyerController(IAlertBuyerDelegate alertBuyerDelegate) {
        this.alertBuyerDelegate = alertBuyerDelegate;
    }

    @PostMapping("/alertBuyer")
    public Mono<ResponseEntity<ResponseAlertBuyerDto>> sendAlertBuyer(@Valid @RequestBody RequestAlertBuyerDto requestAlertBuyer) {
        return alertBuyerDelegate.sendAlertBuyer(requestAlertBuyer);
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<ResponseNotificationDto>> getNotificationsByEmail(@RequestParam("email") @NotEmpty String email) {
        return alertBuyerDelegate.getNotificationsByEmail(email);
    }
}
