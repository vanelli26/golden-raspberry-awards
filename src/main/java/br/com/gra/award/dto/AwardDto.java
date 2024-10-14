package br.com.gra.award.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AwardDto {

    private String producer;
    private Integer interval;
    private Integer previousWin;
    private Integer followingWin;
}
