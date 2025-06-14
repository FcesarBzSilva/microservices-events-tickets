package org.example.mseventmanager.clients;

import org.example.mseventmanager.dto.TicketDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(url = "http://localhost:8081", path = "/tickets", name = "ms-ticket-manager")
public interface TicketServiceClient {
    @GetMapping("/check-tickets-by-event/{eventId}")
    List<TicketDTO> getTicketsByEventId(@PathVariable("eventId") String eventId);
}
