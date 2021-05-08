package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.venue.VenueAlreadyExistsException;
import com.show.showticketingservice.exception.venue.VenueIdNotExistsException;
import com.show.showticketingservice.mapper.VenueMapper;
import com.show.showticketingservice.model.venue.VenueRequest;
import com.show.showticketingservice.model.venue.VenueResponse;
import com.show.showticketingservice.model.venue.VenueUpdateRequest;
import com.show.showticketingservice.model.venueHall.VenueHallResponse;
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

    private VenueRequest venueRequest;

    @InjectMocks
    VenueService venueService;

    @Mock
    VenueHallService venueHallService;

    @Mock
    VenueMapper venueMapper;

    @BeforeEach
    public void init() {
        venueRequest = new VenueRequest(1,"공연장1", "서울시", "02-1212-3434", "www.공연장1.co.kr");
    }

    @Test
    @DisplayName("공연장 정보가 성공적으로 업데이트 됩니다.")
    public void venueInfoUpdateSuccess() {
        VenueRequest updateVenue = new VenueRequest(0,"공연장2", "서울시", "02-1212-3434", "www.공연장1.co.kr");
        List<VenueHallUpdateRequest> venueHallUpdateRequests = new ArrayList<>();
        venueHallUpdateRequests.add(new VenueHallUpdateRequest(1, "공연홀C", 50, 60));
        List<Integer> deleteHallIds = new ArrayList<>();
        deleteHallIds.add(1);
        venueUpdateRequest = new VenueUpdateRequest(updateVenue, venueHallUpdateRequests, deleteHallIds);

        when(venueMapper.isVenueIdExists(venueRequest.getId())).thenReturn(true);
        when(venueMapper.isDuplicateVenueName(updateVenue.getName(), venueRequest.getId())).thenReturn(false);
        doNothing().when(venueMapper).updateVenueInfo(venueRequest.getId(), updateVenue);
        doNothing().when(venueHallService).updateVenueHalls(venueRequest.getId(),  venueHallUpdateRequests);
        doNothing().when(venueHallService).deleteVenueHalls(venueRequest.getId(), deleteHallIds);

        venueService.updateVenueInfo(venueRequest.getId(), venueUpdateRequest);

        verify(venueMapper, times(1)).isVenueIdExists(venueRequest.getId());
        verify(venueMapper, times(1)).isDuplicateVenueName(updateVenue.getName(), venueRequest.getId());
        verify(venueMapper, times(1)).updateVenueInfo(venueRequest.getId(), updateVenue);
        verify(venueHallService, times(1)).updateVenueHalls(venueRequest.getId(), venueHallUpdateRequests);
        verify(venueHallService, times(1)).deleteVenueHalls(venueRequest.getId(), deleteHallIds);
    }

    @Test
    @DisplayName("존재하지 않는 공연장 id로 업데이트할 시 VenueIdNotExistsException이 발생합니다.")
    public void venueIdNotExistsUpdateFail() {
        VenueRequest updateVenue = new VenueRequest(0,"공연장2", "서울시", "02-1212-3434", "www.공연장1.co.kr");
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
        VenueRequest updateVenue = new VenueRequest(0,"공연장1", "서울시", "02-1212-3434", "www.공연장1.co.kr");
        List<VenueHallUpdateRequest> venueHallUpdateRequests = new ArrayList<>();
        venueHallUpdateRequests.add(new VenueHallUpdateRequest(1, "공연홀C", 50, 60));
        List<Integer> deleteHallIds = new ArrayList<>();
        deleteHallIds.add(1);
        venueUpdateRequest = new VenueUpdateRequest(updateVenue, venueHallUpdateRequests, deleteHallIds);

        when(venueMapper.isVenueIdExists(venueRequest.getId())).thenReturn(true);
        when(venueMapper.isDuplicateVenueName(updateVenue.getName(), venueRequest.getId())).thenReturn(true);

        assertThrows(VenueAlreadyExistsException.class, () -> {
            venueService.updateVenueInfo(venueRequest.getId(), venueUpdateRequest);
        });

        verify(venueMapper, times(1)).isVenueIdExists(venueRequest.getId());
        verify(venueMapper, times(1)).isDuplicateVenueName(venueRequest.getName(), venueRequest.getId());
    }

    @Test
    @DisplayName("공연장 정보를 삭제합니다.")
    public void venueInfoDeleteSuccess() {

        when(venueMapper.isVenueIdExists(venueRequest.getId())).thenReturn(true);

        doNothing().when(venueMapper).deleteVenue(venueRequest.getId());

        venueService.deleteVenue(venueRequest.getId());

        verify(venueMapper, times(1)).isVenueIdExists(venueRequest.getId());
        verify(venueMapper, times(1)).deleteVenue(venueRequest.getId());
    }

    @Test
    @DisplayName("삭제할 공연장 id가 없을 경우 VenueIdNotExistsException이 발생합니다.")
    public void venueIdNotExistsDeleteFail() {

        when(venueMapper.isVenueIdExists(venueRequest.getId())).thenReturn(false);

        assertThrows(VenueIdNotExistsException.class, () -> {
            venueService.deleteVenue(venueRequest.getId());
        });
    }

    @Test
    @DisplayName("공연장 조회를 성공합니다.")
    public void getVenueInfoSuccess() {

        VenueResponse venueResponse = new VenueResponse(
                venueRequest.getId(),
                venueRequest.getName(),
                venueRequest.getAddress(),
                venueRequest.getTel(),
                venueRequest.getHomepage());

        VenueHallResponse venueHallResponse = new VenueHallResponse(3, venueRequest.getId(), "공연 홀C", 3, 6, 18);
        List<VenueHallResponse> venueHallResponses = new ArrayList<>();
        venueHallResponses.add(venueHallResponse);

        when(venueMapper.getVenueInfo(venueRequest.getId())).thenReturn(venueResponse);
        when(venueHallService.getVenueHalls(venueRequest.getId())).thenReturn(venueHallResponses);

        venueService.getVenueInfo(venueRequest.getId());

        verify(venueMapper, times(1)).getVenueInfo(venueRequest.getId());
        verify(venueHallService, times(1)).getVenueHalls(venueRequest.getId());
    }
}
