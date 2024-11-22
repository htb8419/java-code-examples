package org.examples.filestorage;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FileStorageTest {
    @Autowired
    private MockMvc mockMvc; // MockMvc to simulate HTTP requests

    @Test
   public void testUploadFil() throws Exception {
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);

        // Stubbing the behavior of the mocked MultipartFile
        Mockito.when(mockFile.getOriginalFilename()).thenReturn("test-file.txt");
        Mockito.when(mockFile.getBytes()).thenReturn("test content".getBytes());

        // Use MockMvc to perform a multipart upload
        mockMvc.perform(MockMvcRequestBuilders.multipart("/file")
                        .file("file", mockFile.getBytes()) // Attach the file
                        .param("file", "test-file.txt")) // Optional: add other form parameters
                .andExpect(status().isOk()) // Check if the status is 200 OK
               ;// .andExpect(content().string("File uploaded successfully: test-file.txt"));
    }
}
