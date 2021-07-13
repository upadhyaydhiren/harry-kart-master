package se.atg.service.harrykart.java.dto.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;


/**
 * @author dhiren
 */
@JacksonXmlRootElement
@Data
public class HarryKartType {
    private Integer numberOfLoops;
    @JacksonXmlElementWrapper(localName = "startList")
    private List<ParticipantType> participant;
    @JacksonXmlElementWrapper(localName = "powerUps")
    private List<LoopType> loop;
}
