package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.screening.model.ScreeningDto;

import java.util.List;

public interface ScreeningService {

    String createScreening(String movie, String room, String startingTime);

    String deleteScreening(String movie, String room, String startingTime);

    List<ScreeningDto> getScreeningList();
}
