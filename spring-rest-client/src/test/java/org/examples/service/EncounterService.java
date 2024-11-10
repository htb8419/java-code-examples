package org.examples.service;

import org.examples.model.PersonInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
public class EncounterService {
    private static final String UPDATE_QUERY = """
               insert into KEYCLOAK.PERSON_DEMOGRAPHIC
                   (RECORD_ID, ADMISSION_DATE, ORGANIZATION, MEDICAL_CENTER, FIRST_NAME,LAST_NAME,
                    FATHER_NAME, GENDER, PERSON_ID_TYPE, PERSON_ID_NO, BIRTH_DATE, MARITAL_STATUS,
                    EDUCATION_LEVEL, MOBILE, CHILDREN)
               values (?, ?, ?, ?, ?, ?,
                       ?, ?, ?, ?, ?, ?,
                       ?,?,?)
            """;
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void saveAll(List<PersonInfo> personInfoList) {
        jdbcTemplate.batchUpdate(UPDATE_QUERY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                PersonInfo personInfo = personInfoList.get(i);
                ps.setString(1, personInfo.record_id());
                ps.setString(2, personInfo.admission_date1());
                ps.setString(3, personInfo.organization());
                ps.setString(4, personInfo.medical_center());
                ps.setString(5, personInfo.first_name());
                ps.setString(6, personInfo.last_name());

                ps.setString(7, personInfo.father_name());
                ps.setString(8, personInfo.gender());
                ps.setString(9, personInfo.person_id_type());
                ps.setString(10, personInfo.person_id_no());
                ps.setString(11, personInfo.birth_date1());
                ps.setString(12, personInfo.marital_status());
                ps.setString(13, personInfo.education_level());
                ps.setString(14, personInfo.mobile());
                String children = personInfo.children();
                try {
                    ps.setInt(15, StringUtils.hasText(children) ? Integer.parseInt(children) : 0);
                } catch (NumberFormatException e) {
                    ps.setInt(15, 0);
                }

            }

            @Override
            public int getBatchSize() {
                return personInfoList.size();
            }
        });
    }

}
