package com.devproject.booking.movie.config;


import com.devproject.booking.movie.dto.BookingDto;
import com.devproject.booking.movie.dto.SeatDto;
import com.devproject.booking.movie.entity.Booking;
import com.devproject.booking.movie.service.SeatService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper(SeatService seatService) {

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(Booking.class, BookingDto.class).addMappings(mapper ->
                mapper.map(Booking::getUser, BookingDto::setUserDto)
        );

        modelMapper.typeMap(Booking.class, BookingDto.class).addMappings(mapper ->
                mapper.map(Booking::getShow, BookingDto::setShowDto)
        );

        Converter<String, List<SeatDto>> seatsConverter = context -> {
            String seats = context.getSource();
            if (seats == null || seats.isEmpty()) {
                return new ArrayList<>();
            }

            List<String> seatNumbers = Arrays.asList(seats.split(","));

            Booking booking = (Booking) context.getParent().getSource();
            Long showId = booking.getShow().getId();


            return seatService.getSeatDetails(showId, seatNumbers);
        };


        modelMapper.typeMap(Booking.class, BookingDto.class)
                .addMappings(mapper -> mapper.using(seatsConverter).map(Booking::getSeats, BookingDto::setSeats));
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }
}
