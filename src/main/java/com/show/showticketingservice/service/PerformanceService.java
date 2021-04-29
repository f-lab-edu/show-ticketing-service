package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.performance.PerformanceAlreadyExistsException;
import com.show.showticketingservice.exception.performance.PerformanceNotExistsException;
import com.show.showticketingservice.exception.performance.PerformanceTimeConflictException;
import com.show.showticketingservice.mapper.PerformanceMapper;
import com.show.showticketingservice.mapper.PerformanceTimeMapper;
import com.show.showticketingservice.mapper.SeatMapper;
import com.show.showticketingservice.model.enumerations.ShowType;
import com.show.showticketingservice.model.performance.PerformanceRequest;
import com.show.showticketingservice.model.performance.PerformanceDetailInfoResponse;
import com.show.showticketingservice.model.performance.PerformanceTimeRequest;
import com.show.showticketingservice.model.performance.SeatPriceRowNumData;
import com.show.showticketingservice.model.performance.SeatRequest;
import com.show.showticketingservice.model.venueHall.VenueHallColumnSeat;
import com.show.showticketingservice.model.performance.PerformanceUpdateRequest;
import com.show.showticketingservice.tool.constants.CacheConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PerformanceService {

    private final PerformanceMapper performanceMapper;

    private final PerformanceTimeMapper performanceTimeMapper;

    private final VenueHallService venueHallService;

    private final FileService fileService;

    private final SeatPriceService seatPriceService;

    private final SeatMapper seatMapper;

    @Transactional
    public void insertPerformance(PerformanceRequest performanceRequest) {
        checkPerformanceExists(performanceRequest.getTitle(), performanceRequest.getShowType());
        performanceMapper.insertPerformance(performanceRequest);
    }

    public void checkPerformanceExists(String title, ShowType showType) {
        if (performanceMapper.isPerformanceExists(title, showType)) {
            throw new PerformanceAlreadyExistsException();
        }
    }

    public void updatePosterImage(int performanceId, MultipartFile image) {

        fileService.checkFileContentType(image);

        String imagePath = performanceMapper.getImagePath(performanceId);

        if (imagePath != null) {
            fileService.deleteFile(imagePath);
        }

        imagePath = fileService.saveFile(image);
        performanceMapper.updatePerfImagePath(performanceId, imagePath);
    }

    @Transactional
    public void insertPerformanceTimes(List<PerformanceTimeRequest> performanceTimeRequests, int performanceId) {

        checkPerfTimeRequestConflict(performanceTimeRequests);

        checkPerfTimeWithDB(performanceTimeRequests, performanceId);

        performanceTimeMapper.insertPerformanceTimes(performanceTimeRequests, performanceId);

        insertSeatInfo(performanceId, performanceTimeRequests);
    }

    private void checkPerfTimeWithDB(List<PerformanceTimeRequest> performanceTimeRequests, int performanceId) {
        /*
        입력하려는 공연 스케줄을 기존 DB에 저장되어 있는 스케줄과 비교

        Logic:
        1. DB의 스케줄을 가져와 startTime 기준으로 정렬 (perfTimesFromDB)
        2. 정렬된 perfTimesFromDB를 endTime 기준으로 '이분탐색' 하여 입력하려는 스케줄의 시작 시간 보다 큰 스케줄 중 가장 작은 값을 검색
        3. 검색된 스케줄이 입력하려는 스케줄의 시간과 중첩된다면 예외처리
         */

        List<PerformanceTimeRequest> perfTimesFromDB = performanceTimeMapper.getPerfTimes(performanceId, performanceTimeRequests);

        if (perfTimesFromDB.size() == 0)
            return;

        Collections.sort(perfTimesFromDB, Comparator.comparing(PerformanceTimeRequest::getStartTime));

        int timeRequestsSize = performanceTimeRequests.size();

        for (int idx = 0; idx < timeRequestsSize; idx++) {

            PerformanceTimeRequest timeRequest = performanceTimeRequests.get(idx);

            long startTime = Long.parseLong(timeRequest.getStartTime());
            long endTime = Long.parseLong(timeRequest.getEndTime());

            int first = 0;
            int last = perfTimesFromDB.size() - 1;

            while (first < last) {
                int mid = (first + last) / 2;

                if (Long.parseLong(perfTimesFromDB.get(mid).getEndTime()) <= startTime)
                    first = mid + 1;
                else
                    last = mid;
            }

            PerformanceTimeRequest refTimeRequest = perfTimesFromDB.get(first);

            long refStartTime = Long.parseLong(refTimeRequest.getStartTime());
            long refEndTime = Long.parseLong(refTimeRequest.getEndTime());

            if (startTime < refEndTime && refStartTime < endTime) {
                throw new PerformanceTimeConflictException("입력한 공연 스케줄 시간이 기존의 스케줄과 중첩 되었습니다.");
            }

        }
    }

    private void checkPerfTimeRequestConflict(List<PerformanceTimeRequest> performanceTimeRequests) {
        /*
        공연 스케줄 입력 요청의 시간이 서로 중첩되거나 정상적인 공연 시간이 아닐 때 예외 처리
        (스케줄 입력 요청을 시작 시간을 기준으로 정렬 후 확인)
         */

        int size = performanceTimeRequests.size();

        Collections.sort(performanceTimeRequests, Comparator.comparing(PerformanceTimeRequest::getStartTime));

        PerformanceTimeRequest perfTime;

        long preEndTime = 0;

        for (int idx = 0; idx < size; idx++) {

            perfTime = performanceTimeRequests.get(idx);

            checkCorrectPerfTime(perfTime);

            if (idx > 0 && preEndTime >= Long.parseLong(perfTime.getStartTime())) {
                throw new PerformanceTimeConflictException("입력한 공연 스케줄 시간이 중첩 되었습니다.");
            }

            preEndTime = Long.parseLong(perfTime.getEndTime());
        }
    }

    private void checkCorrectPerfTime(PerformanceTimeRequest timeRequest) {
        /*
        정상적인 공연 시간 체크
        - 시작 시간이 끝나는 시간 보다 늦을 경우 예외처리
        - 공연 시간이 24시간 이상일 경우 예외처리
         */

        long startTime = Long.parseLong(timeRequest.getStartTime());
        long endTime = Long.parseLong(timeRequest.getEndTime());

        if (startTime >= endTime) {
            throw new PerformanceTimeConflictException("공연 스케줄 시간을 잘못 입력하였습니다.");
        }

        if(endTime - startTime > 24_00_00) {
            throw new PerformanceTimeConflictException("공연 시간은 24시간을 초과할 수 없습니다.");
        }

    }

    private List<SeatRequest> setSeatInfo(int performanceId, List<PerformanceTimeRequest> performanceTimeRequests) {
        /*
        좌석 정보 세팅
        - DB로부터 좌석 정보에 필요한 정보를 불러옴 (VenueHallColumnSeat, SeatPriceRowNumData)
         */

        VenueHallColumnSeat venueHallColumnSeat = venueHallService.getVenueHallColumnAndId(performanceId);

        List<SeatPriceRowNumData> seatPriceRowNumDataList = seatPriceService.getSeatPriceRowNum(performanceId);
        List<SeatRequest> seatRequests = new ArrayList<>();

        performanceTimeRequests.stream().forEach(performanceTimeRequest -> {

            seatPriceRowNumDataList.stream().forEach(seatPriceRowNumData -> {

                IntStream.range(seatPriceRowNumData.getStartRowNum(), seatPriceRowNumData.getEndRowNum() + 1).forEach(rowNum -> {

                    IntStream.range(1, venueHallColumnSeat.getColumnSeats() + 1).forEach(colNum -> {

                        SeatRequest seatRequest = SeatRequest.builder().
                                perfTimeId(performanceTimeRequest.getId()).
                                hallId(venueHallColumnSeat.getId()).
                                priceId(seatPriceRowNumData.getId()).
                                colNum(colNum).
                                rowNum(rowNum).
                                reserved(false).
                                build();

                        seatRequests.add(seatRequest);
                    });
                });
            });
        });

        return seatRequests;
    }

    public void insertSeatInfo(int performanceId, List<PerformanceTimeRequest> performanceTimeRequests) {
        seatPriceService.checkSeatPriceNotExistsException(performanceId);
        List<SeatRequest> seatRequests = setSeatInfo(performanceId, performanceTimeRequests);
        seatMapper.insertSeatInfo(seatRequests);
    }

    @Transactional
    public void updatePerformanceInfo(int performanceId, PerformanceUpdateRequest perfUpdateRequest) {

        checkValidPerformanceId(performanceId);

        checkPerfTitleDuplicated(performanceId, perfUpdateRequest.getTitle(), perfUpdateRequest.getShowType());

        performanceMapper.updatePerformanceInfo(performanceId, perfUpdateRequest);
    }

    private void checkValidPerformanceId(int performanceId) {
        if(!performanceMapper.isPerformanceIdExists(performanceId)) {
            throw new PerformanceNotExistsException();
        }
    }

    private void checkPerfTitleDuplicated(int performanceId, String title, ShowType showType) {
        if (performanceMapper.isPerfTitleDuplicated(performanceId, title, showType)) {
            throw new PerformanceAlreadyExistsException();
        }
    }

    @Cacheable(cacheNames = CacheConstant.PERFORMANCE, key = "#performanceId")
    public PerformanceDetailInfoResponse getPerformanceDetailInfo(int performanceId) {
        return performanceMapper.getPerformanceDetailInfo(performanceId);
    }

    public void deletePerformanceTimes(int performanceId, List<Integer> timeIds) {
        checkValidPerformanceId(performanceId);
        performanceTimeMapper.deletePerformanceTimes(performanceId, timeIds);
    }
}
