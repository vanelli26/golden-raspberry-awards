package br.com.gra.award.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntervalDto {

    private List<AwardDto> min;
    private List<AwardDto> max;
}
