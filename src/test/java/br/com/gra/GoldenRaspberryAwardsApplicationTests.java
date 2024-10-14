package br.com.gra;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gra.award.IntervalDto;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class GoldenRaspberryAwardsApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testImportCsvAndGetIntervalAward() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                        get("/api/interval/award")
                                .with(httpBasic("gra", "vanelli")))
                .andExpect(status().isOk())
                .andReturn();

        IntervalDto interValDto = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), IntervalDto.class);

        Assertions.assertNotNull(interValDto);
        Assertions.assertEquals(1, interValDto.getMin().size());
        Assertions.assertEquals("Joel Silver", interValDto.getMin().get(0).getProducer());
        Assertions.assertEquals(1, interValDto.getMin().get(0).getInterval());
        Assertions.assertEquals(1990, interValDto.getMin().get(0).getPreviousWin());
        Assertions.assertEquals(1991, interValDto.getMin().get(0).getFollowingWin());

        Assertions.assertEquals(1, interValDto.getMax().size());
        Assertions.assertEquals(13, interValDto.getMax().get(0).getInterval());
        Assertions.assertEquals("Matthew Vaughn", interValDto.getMax().get(0).getProducer());
        Assertions.assertEquals(2002, interValDto.getMax().get(0).getPreviousWin());
        Assertions.assertEquals(2015, interValDto.getMax().get(0).getFollowingWin());

        System.out.println(interValDto);
    }
}
