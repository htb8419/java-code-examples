package org.examples.jdbc.test;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
public class JdbcBatchTest {
    private static final int BATCH_SIZE = 500;
    private static final String UPDATE_QUERY = """
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
            Iterator<String[]> csvIterator = csvReader.iterator();
            while (csvIterator.hasNext()) {
                List<String[]> lines = readLines(csvIterator);
                updateDamage(lines);
                count.addAndGet(lines.size());
                System.out.println(LocalDateTime.now() + "   " + count.get());
            }
        }
    }

    private void updateDamage(List<String[]> lines) {
        jdbcTemplate.batchUpdate(UPDATE_QUERY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                String[] item = lines.get(i);
                ps.setLong(1, Long.parseLong(item[0]));
                ps.setString(2, item[1]);
                ps.setString(3, item[2]);
                ps.setString(4, item[3]);
                ps.setDouble(5, Double.parseDouble(item[4]));
            }

            @Override
            public int getBatchSize() {
                return lines.size();
            }
        });
    }

    private List<String[]> readLines(Iterator<String[]> csvIterator) {
        List<String[]> lines = new ArrayList<>(BATCH_SIZE);
        int i = 0;
        do {
            lines.add(csvIterator.next());
        } while (i++ < BATCH_SIZE && csvIterator.hasNext());
        return lines;
    }
}
