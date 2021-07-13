package se.atg.service.harrykart.java.helper;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

/**
 * This is helper class for handle horse computation
 * @author dhiren
 */
@Data
@Builder
public class HorseMetadata implements Comparable<HorseMetadata> {
    private final String name;
    private final Integer laneNo;
    private Integer currentSpeed;
    private double totalSpentTime;


    public void handleSpeed(Integer speed) {
        currentSpeed += speed;
    }

    public void calculateTotalTime() {
        double spentTime = (1000.0) / currentSpeed;
        totalSpentTime += spentTime;
    }

    @Override
    public int compareTo(HorseMetadata o) {
        if (Objects.isNull(o)) {
            return -1;
        }
        return Double.compare(totalSpentTime, o.totalSpentTime);
    }
}
