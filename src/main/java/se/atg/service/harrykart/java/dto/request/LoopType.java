package se.atg.service.harrykart.java.dto.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author dhiren
 */

@Data
@NoArgsConstructor
public class LoopType {

    @JacksonXmlElementWrapper(useWrapping = false)
    private List<LaneType> lane;
    @JacksonXmlProperty(isAttribute = true)
    private Integer number;
}
