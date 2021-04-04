package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.venue.VenueAlreadyExistsException;
import com.show.showticketingservice.mapper.VenueMapper;
import com.show.showticketingservice.model.venue.Venue;
import com.show.showticketingservice.model.venue.VenueUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VenueServiceTest {

    private VenueUpdateRequest venueUpdateRequest;

    private Venue venue;

    @InjectMocks
    VenueService venueService;

    @Mock
    VenueHallService venueHallService;

    @Mock
    VenueMapper venueMapper;

    @BeforeEach
    public void init() {
        venue = new Venue(1,"공연장1", "서울시", "02-1212-3434", "www.공연장1.co.kr");
    }

    @Test
    @DisplayName("공연장 정보가 성공적으로 업데이트 됩니다.")
    public void venueInfoUpdateSuccess() {
        Venue updateVenue = new Venue(0,"공연장2", "서울시", "02-1212-3434", "www.공연장1.co.kr");
        venueUpdateRequest = new VenueUpdateRequest(updateVenue, null, null, null);

        when(venueMapper.isVenueExists(updateVenue.getName())).thenReturn(false);

        doNothing().when(venueMapper).updateVenueInfo(venue.getId(), updateVenue);
        doNothing().when(venueHallService).updateVenueHalls(null, null, venue.getId());
        doNothing().when(venueHallService).deleteVenueHalls(venue.getId(), null);

        venueService.updateVenueInfo(venue.getId(), venueUpdateRequest);

        verify(venueMapper, times(1)).isVenueExists(updateVenue.getName());
        verify(venueMapper, times(1)).updateVenueInfo(venue.getId(), updateVenue);
        verify(venueHallService, times(1)).updateVenueHalls(null, null, venue.getId());
        verify(venueHallService, times(1)).deleteVenueHalls(venue.getId(), null);
    }

    @Test
    @DisplayName("기존에 있는 공연장 이름으로 업데이트 할 시 VenueAlreadyExistsException이 발생합니다.")
    public void venueHallAlreadyExistsUpdateFail() {
        Venue updateVenue = new Venue(0,"공연장1", "서울시", "02-1212-3434", "www.공연장1.co.kr");
        venueUpdateRequest = new VenueUpdateRequest(updateVenue, null, null, null);

        when(venueMapper.isVenueExists(updateVenue.getName())).thenReturn(true);

        assertThrows(VenueAlreadyExistsException.class, () -> {
            venueService.updateVenueInfo(venue.getId(), venueUpdateRequest);
        });
    }

    @Test
    @DisplayName("공연장 정보를 삭제합니다.")
    public void venueInfoDeleteSuccess() {

        doNothing().when(venueMapper).deleteVenue(venue.getId());

        venueService.deleteVenue(venue.getId());

        verify(venueMapper, times(1)).deleteVenue(venue.getId());
    }
}
