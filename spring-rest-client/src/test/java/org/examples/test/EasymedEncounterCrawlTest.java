package org.examples.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.examples.model.PersonInfo;
import org.examples.service.EncounterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.springframework.util.StringUtils.hasText;

@SpringBootTest
public class EasymedEncounterCrawlTest {

    private static final String GET_SECTION_URL = "https://api.easymed.ir:30110/ehr/clinicalAssessmentCase/getSection";
    final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    EncounterService encounterService;
    int batchSize = 100;
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void fetchPersonInfoFromEasymed() {
        int start = 4468730;
        int end = start + 1_000_000;

        for (int i = start; i < end; i += 2) {
            try {
                List<Map<String, Object>> sections = fetchSectionInfoByEncounterId(i);
                getDemographic(sections).ifPresent(personInfo -> encounterService.saveAll(Collections.singletonList(personInfo)));
                if (i % 10_000 == 0) {
                    Thread.sleep(10000);
                }
            } catch (HttpServerErrorException.InternalServerError ignored) {
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                System.out.println("error fetch encounter =" + i);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> fetchSectionInfoByEncounterId(long encounterId) throws Exception {
        SectionRequest sectionRequest = new SectionRequest(73057, new EncounterDto(encounterId));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.ACCEPT, "application/json");
        HttpEntity<Object> request = new HttpEntity<>(sectionRequest, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(GET_SECTION_URL, request, Map.class);
        Object result = Objects.requireNonNull(response.getBody()).get("result");
        return (List<Map<String, Object>>) result;
    }

    private Optional<PersonInfo> getDemographic(List<Map<String, Object>> sections) throws JsonProcessingException {
        return sections.stream()
                .filter(data -> "demographic_occ_medicine".equals(data.get("code")) && hasText((CharSequence) data.get("jsonValue")))
                .findFirst()
                .map(item -> item.get("jsonValue"))
                .map(jsonValue -> {
                    try {
                        return objectMapper.readValue((String) jsonValue, PersonInfo.class);
                    } catch (JsonProcessingException e) {
                        return null;
                    }
                }).filter(personInfo ->
                        hasText(personInfo.father_name()) && hasText(personInfo.last_name()) && hasText(personInfo.mobile()));

    }


    record EncounterDto(long id) {
    }

    record SectionRequest(long clinicalAssessmentId, EncounterDto encounterDto) {
    }
}
