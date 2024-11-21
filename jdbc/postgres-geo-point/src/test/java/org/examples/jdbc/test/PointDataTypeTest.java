package org.examples.jdbc.test;

import org.examples.jdbc.repo.AddressRepo;
import org.examples.jdbc.model.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class PointDataTypeTest {
    @Autowired
    AddressRepo addressRepo;
    @Test
    @Transactional
    public void test_bind_point_data_type(){
        Address address = addressRepo.findById(1L).get();
        System.out.println(address.getLocation());
    }
}
