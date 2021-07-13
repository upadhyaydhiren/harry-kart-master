package se.atg.service.harrykart.java.dto.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.Data;

/**
 * @author dhiren
 */
@Data
public class LaneType {
    @JacksonXmlText
    private Integer value;
    @JacksonXmlProperty(isAttribute = true)
    private Integer number;
}
