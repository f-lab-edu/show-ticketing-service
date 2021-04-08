package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.venue.VenueAlreadyExistsException;
import com.show.showticketingservice.exception.venue.VenueIdNotExistsException;
import com.show.showticketingservice.mapper.VenueMapper;
import com.show.showticketingservice.model.venue.Venue;
import com.show.showticketingservice.model.venue.VenueUpdateRequest;
import com.show.showticketingservice.model.venueHall.VenueHallUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

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
        List<VenueHallUpdateRequest> venueHallUpdateRequests = new ArrayList<>();
        venueHallUpdateRequests.add(new VenueHallUpdateRequest(1, "공연홀C", 50, 60));
        List<Integer> deleteHallIds = new ArrayList<>();
        deleteHallIds.add(1);
        venueUpdateRequest = new VenueUpdateRequest(updateVenue, venueHallUpdateRequests, deleteHallIds);

        when(venueMapper.isVenueIdExists(venue.getId())).thenReturn(true);
        when(venueMapper.isVenueExists(updateVenue.getName())).thenReturn(false);
        doNothing().when(venueMapper).updateVenueInfo(venue.getId(), updateVenue);
        doNothing().when(venueHallService).updateVenueHalls(venue.getId(),  venueHallUpdateRequests);
        doNothing().when(venueHallService).deleteVenueHalls(venue.getId(), deleteHallIds);

        venueService.updateVenueInfo(venue.getId(), venueUpdateRequest);

        verify(venueMapper, times(1)).isVenueIdExists(venue.getId());
        verify(venueMapper, times(1)).isVenueExists(updateVenue.getName());
        verify(venueMapper, times(1)).updateVenueInfo(venue.getId(), updateVenue);
        verify(venueHallService, times(1)).updateVenueHalls(venue.getId(), venueHallUpdateRequests);
        verify(venueHallService, times(1)).deleteVenueHalls(venue.getId(), deleteHallIds);
    }

    @Test
    @DisplayName("존재하지 않는 공연장 id로 업데이트할 시 VenueIdNotExistsException이 발생합니다.")
    public void venueIdNotExistsUpdateFail() {
        Venue updateVenue = new Venue(0,"공연장2", "서울시", "02-1212-3434", "www.공연장1.co.kr");
        List<VenueHallUpdateRequest> venueHallUpdateRequests = new ArrayList<>();
        venueHallUpdateRequests.add(new VenueHallUpdateRequest(1, "공연홀C", 50, 60));
        List<Integer> deleteHallIds = new ArrayList<>();
        deleteHallIds.add(1);
        venueUpdateRequest = new VenueUpdateRequest(updateVenue, venueHallUpdateRequests, deleteHallIds);

        when(venueMapper.isVenueIdExists(3)).thenReturn(false);

        assertThrows(VenueIdNotExistsException.class, () -> {
            venueService.updateVenueInfo(3, venueUpdateRequest);
        });

        verify(venueMapper, times(1)).isVenueIdExists(3);
    }

    @Test
    @DisplayName("기존에 있는 공연장 이름으로 업데이트할 시 VenueAlreadyExistsException이 발생합니다.")
    public void venueAlreadyExistsUpdateFail() {
        Venue updateVenue = new Venue(0,"공연장1", "서울시", "02-1212-3434", "www.공연장1.co.kr");
        List<VenueHallUpdateRequest> venueHallUpdateRequests = new ArrayList<>();
        venueHallUpdateRequests.add(new VenueHallUpdateRequest(1, "공연홀C", 50, 60));
        List<Integer> deleteHallIds = new ArrayList<>();
        deleteHallIds.add(1);
        venueUpdateRequest = new VenueUpdateRequest(updateVenue, venueHallUpdateRequests, deleteHallIds);

        when(venueMapper.isVenueIdExists(venue.getId())).thenReturn(true);
        when(venueMapper.isVenueExists(updateVenue.getName())).thenReturn(true);

        assertThrows(VenueAlreadyExistsException.class, () -> {
            venueService.updateVenueInfo(venue.getId(), venueUpdateRequest);
        });

        verify(venueMapper, times(1)).isVenueIdExists(venue.getId());
        verify(venueMapper, times(1)).isVenueExists(updateVenue.getName());
    }

    @Test
    @DisplayName("공연장 정보를 삭제합니다.")
    public void venueInfoDeleteSuccess() {

        when(venueMapper.isVenueIdExists(venue.getId())).thenReturn(true);

        doNothing().when(venueMapper).deleteVenue(venue.getId());

        venueService.deleteVenue(venue.getId());

        verify(venueMapper, times(1)).isVenueIdExists(venue.getId());
        verify(venueMapper, times(1)).deleteVenue(venue.getId());
    }

    @Test
    @DisplayName("삭제할 공연장 id가 없을 경우 VenueIdNotExistsException이 발생합니다.")
    public void venueIdNotExistsDeleteFail() {

        when(venueMapper.isVenueIdExists(venue.getId())).thenReturn(false);

        assertThrows(VenueIdNotExistsException.class, () -> {
            venueService.deleteVenue(venue.getId());
        });
    }
}
