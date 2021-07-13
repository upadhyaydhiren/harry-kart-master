package se.atg.service.harrykart.java.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.atg.service.harrykart.java.dto.request.HarryKartType;
import se.atg.service.harrykart.java.dto.request.LaneType;
import se.atg.service.harrykart.java.dto.request.LoopType;
import se.atg.service.harrykart.java.dto.response.HorseRanking;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This is service helper class for compute top 3 Horse based on speed
 * @author dhiren
 */
@Service
@Slf4j
public class HarryKartHelper {

    /**
     * This method is used for find top 3 horse with position
     * @param harryKartType {@link HarryKartType}
     * @return {@link List&lt;{@link HorseRanking}&gt;}
     */
    public List<HorseRanking> findHorseRanking(HarryKartType harryKartType) {
        try {

            Map<Integer, HorseMetadata> horseLaneMap = harryKartType
                    .getParticipant()
                    .stream()
                    .map(participantType -> HorseMetadata.builder()
                            .name(participantType.getName())
                            .laneNo(participantType.getLane())
                            .currentSpeed(participantType.getBaseSpeed())
                            .build()
                    )
                    .peek(HorseMetadata::calculateTotalTime)
                    .collect(Collectors.toMap(HorseMetadata::getLaneNo, Function.identity()));


            Map<Integer, List<LaneType>> horsePowerLoopWithLane = harryKartType.getLoop()
                    .stream()
                    .collect(Collectors.toMap(LoopType::getNumber, LoopType::getLane));

            // No Of Loop iteration
            IntStream
                    .range(1, harryKartType.getNumberOfLoops())
                    .forEach(loopNo -> {
                        List<LaneType> lanePowerDetails = horsePowerLoopWithLane.get(loopNo);
                        if (Objects.nonNull(lanePowerDetails)) {
                            lanePowerDetails
                                    .forEach(laneType -> Optional.ofNullable(horseLaneMap.get(laneType.getNumber()))
                                            .ifPresent(horseMetadata -> horseMetadata.handleSpeed(laneType.getValue())));
                        }
                        horseLaneMap.values()
                                .forEach(HorseMetadata::calculateTotalTime);
                    });

            AtomicInteger positions = new AtomicInteger(1);

            return horseLaneMap.values()
                    .stream()
                    .sorted()
                    .limit(3)
                    .map(HorseMetadata::getName)
                    .map(horseName -> new HorseRanking(positions.getAndIncrement(), horseName))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error in internal processing", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }
}
