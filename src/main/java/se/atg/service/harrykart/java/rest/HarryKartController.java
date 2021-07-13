package se.atg.service.harrykart.java.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.atg.service.harrykart.java.dto.request.HarryKartType;
import se.atg.service.harrykart.java.dto.response.HorseRanking;
import se.atg.service.harrykart.java.helper.HarryKartHelper;

import java.util.List;

@RestController
@RequestMapping("/java/api")
@AllArgsConstructor
public class HarryKartController {

    private final HarryKartHelper harryKartHelper;

    @PostMapping(path = "/play", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HorseRanking>> playHarryKart(@RequestBody HarryKartType harryKartType) {
        return ResponseEntity.ok(harryKartHelper.findHorseRanking(harryKartType));
    }

}
