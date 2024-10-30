package org.examples.jdbc.test;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
public class JdbcInsertTest {

    private final String UPDATE_QUERY = """
                    insert into employee (id,name,department,email,salary)
                                values(?,?,?,?,?)
            """;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void readCSVFile() throws Exception {
        AtomicInteger count = new AtomicInteger(0);
        File file = ResourceUtils.getFile("classpath:large_sample_employee_data.csv");
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(file)).build()) {
            csvReader.forEach(item -> {
                jdbcTemplate.update(UPDATE_QUERY, ps -> {
                    ps.setLong(1, Long.parseLong(item[0]));
                    ps.setString(2, item[1]);
                    ps.setString(3, item[2]);
                    ps.setString(4, item[3]);
                    ps.setDouble(5, Double.parseDouble(item[4]));
                });
                if (count.incrementAndGet() % 1000 == 0) {
                    System.out.println(count.get());
                }
            });
        }
    }
}
