package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.enumerations.AccessRoles;
import com.show.showticketingservice.model.reservation.ReservationInfoToCancelRequest;
import com.show.showticketingservice.model.reservation.ReservationRequest;
import com.show.showticketingservice.model.user.UserSession;
import com.show.showticketingservice.service.ReservationService;
import com.show.showticketingservice.tool.annotation.CurrentUser;
import com.show.showticketingservice.tool.annotation.UserAuthenticationNecessary;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    @UserAuthenticationNecessary(role = AccessRoles.GENERAL)
    public void reserveSeats(@CurrentUser UserSession userSession, @RequestBody ReservationRequest reservationRequest) {
        reservationService.reserveSeats(userSession.getUserId(), reservationRequest);
    }

    @DeleteMapping
    @UserAuthenticationNecessary(role = AccessRoles.GENERAL)
    public void cancelSeats(@CurrentUser UserSession userSession, @RequestBody @Valid ReservationInfoToCancelRequest reservationInfoToCancelRequest) {
        reservationService.cancelSeats(userSession.getUserId(), reservationInfoToCancelRequest);
    }

}
