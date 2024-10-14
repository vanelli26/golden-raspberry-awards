package br.com.gra.award;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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
