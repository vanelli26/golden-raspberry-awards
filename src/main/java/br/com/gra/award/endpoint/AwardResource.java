package br.com.gra.award.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.gra.award.dto.IntervalDto;
import br.com.gra.award.service.AwardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AwardResource implements AwardApi {

    private final AwardService awardService;

    @Override
    public ResponseEntity<IntervalDto> findIntervalAward() {

        return ResponseEntity.ok(awardService.findIntervalAward());
    }
}
