package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.venueHall.SameVenueHallAdditionException;
import com.show.showticketingservice.exception.venueHall.VenueHallAlreadyExistsException;
import com.show.showticketingservice.mapper.VenueHallMapper;
import com.show.showticketingservice.model.venueHall.VenueHallRequest;
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

    private List<VenueHallRequest> venueHallRequests;

    private VenueHallRequest venueHallRequestOne;

    private VenueHallRequest venueHallRequestTwo;

    private List<Integer> updateHallIds;

    private int venueId;

    @Mock
    VenueHallMapper venueHallMapper;

    @InjectMocks
    VenueHallService venueHallService;

    @BeforeEach
    public void init() {
        venueHallRequests = new ArrayList<>();
        venueHallRequestOne = new VenueHallRequest("공연홀A", 50, 60);
        venueHallRequestTwo = new VenueHallRequest("공연홀B", 30, 50);
        venueHallRequests.add(venueHallRequestOne);
        venueHallRequests.add(venueHallRequestTwo);

        updateHallIds = new ArrayList<>();
        updateHallIds.add(1);
        updateHallIds.add(2);

        venueId = 1;
    }

    @Test
    @DisplayName("공연 홀 정보가 성공적으로 업데이트 됩니다.")
    public void venueHallInfoUpdateSuccess() {
        List<VenueHallRequest> venueHallUpdateRequests = new ArrayList<>();
        VenueHallRequest venueHallUpdateRequestOne = new VenueHallRequest("공연홀C", 50, 60);
        VenueHallRequest venueHallUpdateRequestTwo = new VenueHallRequest("공연홀D", 30, 50);
        venueHallUpdateRequests.add(venueHallUpdateRequestOne);
        venueHallUpdateRequests.add(venueHallUpdateRequestTwo);

        when(venueHallMapper.isVenueHallsExists(venueHallUpdateRequests, venueId)).thenReturn(false);

        doNothing().when(venueHallMapper).updateVenueHalls(updateHallIds, venueHallUpdateRequests, venueId);

        venueHallService.updateVenueHalls(updateHallIds, venueHallUpdateRequests, venueId);

        verify(venueHallMapper, times(1)).isVenueHallsExists(venueHallUpdateRequests, venueId);
        verify(venueHallMapper, times(1)).updateVenueHalls(updateHallIds, venueHallUpdateRequests, venueId);

    }

    @Test
    @DisplayName("동일한 공연 홀 이름을 업데이트 할 시 SameVenueHallAdditionException이 발생합니다.")
    public void venueHallDuplicationNameUpdateFail() {
        List<VenueHallRequest> venueHallUpdateRequests = new ArrayList<>();
        VenueHallRequest venueHallUpdateRequestOne = new VenueHallRequest("공연홀C", 50, 60);
        VenueHallRequest venueHallUpdateRequestTwo = new VenueHallRequest("공연홀C", 30, 50);
        venueHallUpdateRequests.add(venueHallUpdateRequestOne);
        venueHallUpdateRequests.add(venueHallUpdateRequestTwo);

        assertThrows(SameVenueHallAdditionException.class, () -> {
            venueHallService.updateVenueHalls(updateHallIds, venueHallUpdateRequests, venueId);
        });
    }

    @Test
    @DisplayName("기존에 있는 공연 홀 이름으로 업데이트 할 시 VenueHallAlreadyExistsException이 발생합니다.")
    public void venueHallAlreadyExistsUpdateFail() {
        List<VenueHallRequest> venueHallUpdateRequests = new ArrayList<>();
        VenueHallRequest venueHallUpdateRequestOne = new VenueHallRequest("공연홀C", 50, 60);
        VenueHallRequest venueHallUpdateRequestTwo = new VenueHallRequest("공연홀D", 30, 50);
        venueHallUpdateRequests.add(venueHallUpdateRequestOne);
        venueHallUpdateRequests.add(venueHallUpdateRequestTwo);

        when(venueHallMapper.isVenueHallsExists(venueHallUpdateRequests, venueId)).thenReturn(true);

        assertThrows(VenueHallAlreadyExistsException.class, () -> {
            venueHallService.updateVenueHalls(updateHallIds, venueHallUpdateRequests, venueId);
        });

        verify(venueHallMapper, times(1)).isVenueHallsExists(venueHallUpdateRequests, venueId);
    }

    @Test
    @DisplayName("공연 홀 정보를 삭제합니다.")
    public void venueHallInfoDeleteSuccess() {

        List<Integer> deleteHallIds = new ArrayList<>();
        deleteHallIds.add(1);
        deleteHallIds.add(2);

        doNothing().when(venueHallMapper).deleteVenueHalls(venueId, deleteHallIds);

        venueHallService.deleteVenueHalls(venueId, deleteHallIds);

        verify(venueHallMapper, times(1)).deleteVenueHalls(venueId, deleteHallIds);
    }

}
