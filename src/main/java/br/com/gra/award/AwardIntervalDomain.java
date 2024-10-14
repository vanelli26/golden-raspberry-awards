package br.com.gra.award;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AwardIntervalDomain {

    private String producer;
    private Integer interval;
    private Integer previousWin;
    private Integer followingWin;
}
