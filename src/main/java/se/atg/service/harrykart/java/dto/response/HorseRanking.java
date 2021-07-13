package se.atg.service.harrykart.java.dto.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * @author dhiren
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record HorseRanking(Integer position, String horse) {
}
