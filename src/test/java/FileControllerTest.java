import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.enviro.assessment.grad001.sphelelefakude.Eenviro365Application;
import com.enviro.assessment.grad001.sphelelefakude.model.UploadedFile;
import com.enviro.assessment.grad001.sphelelefakude.service.FileService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { Eenviro365Application.class })
@AutoConfigureMockMvc
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;

    @Test
    public void testUploadFile() throws Exception {
        String fileContent = "Hello, World!";
        MockMultipartFile file = new MockMultipartFile("file", "/resources/test.txt", MediaType.TEXT_PLAIN_VALUE,
                fileContent.getBytes());

        UploadedFile uploadedFile = new UploadedFile();
        uploadedFile.setId(1L);
        uploadedFile.setFileData(fileContent); // Set the file content

        when(fileService.processFile(any(UploadedFile.class))).thenReturn(uploadedFile);

        try {
            // Test successful file upload
            mockMvc.perform(multipart("/api/upload").file(file))
                    .andExpect(status().isOk())
                    .andExpect(content()
                            .string(containsString("File uploaded successfully with ID: " + uploadedFile.getId())));
        } catch (AssertionError e) {
            // Handle assertion errors
            fail("Assertion error occurred: ");
        } catch (Exception e) {
            // Handle other exceptions
            fail("Unexpected exception occurred: " + e.getMessage());
        }

    }

    @Test
    public void testGetProcessedResults_Success() throws Exception {
        // Mock the behavior of fileService.getFileById to return a valid UploadedFile
        UploadedFile uploadedFile = new UploadedFile();
        uploadedFile.setId(1L);
        uploadedFile.setFileData("Test file content");

        when(fileService.getFileById(1L)).thenReturn(uploadedFile);

        // Perform GET request to /api/results/1
        mockMvc.perform(get("/api/results/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("\"id\":1")))
                .andExpect(content().string(containsString("\"fileData\":\"Test file content\"")));
    }

}
