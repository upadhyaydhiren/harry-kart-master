package se.atg.service.harrykart.java.dto.request;

import lombok.Data;

/**
 * @author dhiren
 */

@Data
public class ParticipantType {
    private Integer lane;
    private String name;
    private Integer baseSpeed;
}
