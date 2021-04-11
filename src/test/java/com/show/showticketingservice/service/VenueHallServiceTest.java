package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.venueHall.SameVenueHallAdditionException;
import com.show.showticketingservice.exception.venueHall.VenueHallAlreadyExistsException;
import com.show.showticketingservice.exception.venueHall.VenueHallIdNotExistsException;
import com.show.showticketingservice.mapper.VenueHallMapper;
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
public class VenueHallServiceTest {

    private List<VenueHallUpdateRequest> venueHallUpdateRequests;

    private VenueHallUpdateRequest venueHallUpdateRequestOne;

    private VenueHallUpdateRequest venueHallUpdateRequestTwo;

    private int venueId;

    @Mock
    VenueHallMapper venueHallMapper;

    @InjectMocks
    VenueHallService venueHallService;

    @BeforeEach
    public void init() {
        venueHallUpdateRequests = new ArrayList<>();
        venueHallUpdateRequestOne = new VenueHallUpdateRequest(1, "공연홀A", 50, 60);
        venueHallUpdateRequestTwo = new VenueHallUpdateRequest(2, "공연홀B", 30, 50);
        venueHallUpdateRequests.add(venueHallUpdateRequestOne);
        venueHallUpdateRequests.add(venueHallUpdateRequestTwo);

        venueId = 1;
    }

    @Test
    @DisplayName("공연 홀 정보가 성공적으로 업데이트 됩니다.")
    public void venueHallInfoUpdateSuccess() {
        List<VenueHallUpdateRequest> venueHallUpdateRequests = new ArrayList<>();
        VenueHallUpdateRequest venueHallUpdateRequestOne = new VenueHallUpdateRequest(1, "공연홀C", 50, 60);
        VenueHallUpdateRequest venueHallUpdateRequestTwo = new VenueHallUpdateRequest(2, "공연홀D", 30, 50);
        venueHallUpdateRequests.add(venueHallUpdateRequestOne);
        venueHallUpdateRequests.add(venueHallUpdateRequestTwo);

        List<Integer> hallIds = venueHallService.getVenueHallUpdateRequestId(venueHallUpdateRequests);

        when(venueHallMapper.getVenueHallCount(venueId, hallIds)).thenReturn(hallIds.size());
        when(venueHallMapper.isVenueHallNamesExists(venueHallUpdateRequests, venueId)).thenReturn(false);
        doNothing().when(venueHallMapper).updateVenueHalls(venueHallUpdateRequests, venueId);

        venueHallService.updateVenueHalls(venueId, venueHallUpdateRequests);

        verify(venueHallMapper, times(1)).getVenueHallCount(venueId, hallIds);
        verify(venueHallMapper, times(1)).isVenueHallNamesExists(venueHallUpdateRequests, venueId);
        verify(venueHallMapper, times(1)).updateVenueHalls(venueHallUpdateRequests, venueId);

    }

    @Test
    @DisplayName("존재하지 않는 공연 홀 id로 업데이트할 시 VenueHallIdNotExistsException이 발생합니다.")
    public void venueHallIdNotExistsUpdateFail() {
        List<VenueHallUpdateRequest> venueHallUpdateRequests = new ArrayList<>();
        VenueHallUpdateRequest venueHallUpdateRequestOne = new VenueHallUpdateRequest(3, "공연홀C", 50, 60);
        VenueHallUpdateRequest venueHallUpdateRequestTwo = new VenueHallUpdateRequest(4, "공연홀D", 30, 50);
        venueHallUpdateRequests.add(venueHallUpdateRequestOne);
        venueHallUpdateRequests.add(venueHallUpdateRequestTwo);

        List<Integer> hallIds = venueHallService.getVenueHallUpdateRequestId(venueHallUpdateRequests);

        when(venueHallMapper.getVenueHallCount(venueId, hallIds)).thenReturn(hallIds.size() - 2);

        assertThrows(VenueHallIdNotExistsException.class, () -> {
            venueHallService.updateVenueHalls(venueId, venueHallUpdateRequests);
        });

        verify(venueHallMapper, times(1)).getVenueHallCount(venueId, hallIds);
    }

    @Test
    @DisplayName("동일한 공연 홀 이름을 업데이트할 시 SameVenueHallAdditionException이 발생합니다.")
    public void venueHallDuplicationNameUpdateFail() {
        List<VenueHallUpdateRequest> venueHallUpdateRequests = new ArrayList<>();
        VenueHallUpdateRequest venueHallUpdateRequestOne = new VenueHallUpdateRequest(1, "공연홀C", 50, 60);
        VenueHallUpdateRequest venueHallUpdateRequestTwo = new VenueHallUpdateRequest(2, "공연홀C", 30, 50);
        venueHallUpdateRequests.add(venueHallUpdateRequestOne);
        venueHallUpdateRequests.add(venueHallUpdateRequestTwo);

        List<Integer> hallIds = venueHallService.getVenueHallUpdateRequestId(venueHallUpdateRequests);

        when(venueHallMapper.getVenueHallCount(venueId, hallIds)).thenReturn(hallIds.size());

        assertThrows(SameVenueHallAdditionException.class, () -> {
            venueHallService.updateVenueHalls(venueId, venueHallUpdateRequests);
        });

        verify(venueHallMapper, times(1)).getVenueHallCount(venueId, hallIds);
    }

    @Test
    @DisplayName("기존에 있는 공연 홀 이름으로 업데이트할 시 VenueHallAlreadyExistsException이 발생합니다.")
    public void venueHallAlreadyExistsUpdateFail() {
        List<VenueHallUpdateRequest> venueHallUpdateRequests = new ArrayList<>();
        VenueHallUpdateRequest venueHallUpdateRequestOne = new VenueHallUpdateRequest(1, "공연홀A", 50, 60);
        VenueHallUpdateRequest venueHallUpdateRequestTwo = new VenueHallUpdateRequest(2, "공연홀B", 30, 50);
        venueHallUpdateRequests.add(venueHallUpdateRequestOne);
        venueHallUpdateRequests.add(venueHallUpdateRequestTwo);

        List<Integer> hallIds = venueHallService.getVenueHallUpdateRequestId(venueHallUpdateRequests);

        when(venueHallMapper.getVenueHallCount(venueId, hallIds)).thenReturn(hallIds.size());
        when(venueHallMapper.isVenueHallNamesExists(venueHallUpdateRequests, venueId)).thenReturn(true);

        assertThrows(VenueHallAlreadyExistsException.class, () -> {
            venueHallService.updateVenueHalls(venueId, venueHallUpdateRequests);
        });

        verify(venueHallMapper, times(1)).getVenueHallCount(venueId, hallIds);
        verify(venueHallMapper, times(1)).isVenueHallNamesExists(venueHallUpdateRequests, venueId);
    }

    @Test
    @DisplayName("존재하지 않는 공연 홀 id로 삭제할 시 VenueHallIdNotExistsException이 발생합니다.")
    public void venueHallIdNotExistsDeleteFail() {
        List<Integer> deleteHallIds = new ArrayList<>();
        deleteHallIds.add(1);
        deleteHallIds.add(2);

        when(venueHallMapper.getVenueHallCount(venueId, deleteHallIds)).thenReturn(deleteHallIds.size() - 2);

        assertThrows(VenueHallIdNotExistsException.class, () -> {
            venueHallService.updateVenueHalls(venueId, venueHallUpdateRequests);
        });

        verify(venueHallMapper, times(1)).getVenueHallCount(venueId, deleteHallIds);
    }

    @Test
    @DisplayName("공연 홀 정보를 성공적으로 삭제합니다.")
    public void venueHallInfoDeleteSuccess() {

        List<Integer> deleteHallIds = new ArrayList<>();
        deleteHallIds.add(1);
        deleteHallIds.add(2);

        when(venueHallMapper.getVenueHallCount(venueId, deleteHallIds)).thenReturn(deleteHallIds.size());
        doNothing().when(venueHallMapper).deleteVenueHalls(venueId, deleteHallIds);

        venueHallService.deleteVenueHalls(venueId, deleteHallIds);

        verify(venueHallMapper, times(1)).getVenueHallCount(venueId, deleteHallIds);
        verify(venueHallMapper, times(1)).deleteVenueHalls(venueId, deleteHallIds);
    }

}
