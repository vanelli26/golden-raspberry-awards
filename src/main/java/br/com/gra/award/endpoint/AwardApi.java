package br.com.gra.award.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.gra.award.dto.IntervalDto;

import io.swagger.v3.oas.annotations.Operation;

@RequestMapping("/api/interval/award")
public interface AwardApi {

    @Operation(
            summary = "Interval of awards",
            description = "Obter o produtor com maior intervalo entre dois prêmios consecutivos, e o que obteve dois prêmios mais rápido"
    )
    @GetMapping
    ResponseEntity<IntervalDto> findIntervalAward();
}
