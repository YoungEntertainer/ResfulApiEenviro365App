import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.enviro.assessment.grad001.sphelelefakude.eenviro365.model.UploadedFile;
import com.enviro.assessment.grad001.sphelelefakude.eenviro365.service.FileService;

@SpringBootTest(classes = FileControllerTest.class)
@AutoConfigureMockMvc
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;

    @Test
    public void testUploadFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes());
        UploadedFile uploadedFile = new UploadedFile();
        uploadedFile.setId(1L);
        when(fileService.processFile(any(UploadedFile.class))).thenReturn(uploadedFile);

        mockMvc.perform(post("/api/upload").contentType(MediaType.MULTIPART_FORM_DATA_VALUE).content(file.getBytes()))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetProcessedResults() throws Exception {
        UploadedFile uploadedFile = new UploadedFile();
        uploadedFile.setId(1L);
        uploadedFile.setFileData("Test data");
        when(fileService.getFileById(1L)).thenReturn(uploadedFile);

        try {
            mockMvc.perform(get("/api/results/1")).andExpect(status().isOk());
        } catch (Exception e) {
            
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateFile() throws Exception {
        UploadedFile uploadedFile = new UploadedFile();
        uploadedFile.setId(1L);
        when(fileService.updateFile(any(UploadedFile.class))).thenReturn(uploadedFile);

        mockMvc.perform(put("/api/update/1").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"fileData\":\"Updated data\"}")).andExpect(status().isOk());
    }

    @Test
    public void testDeleteFile() throws Exception {
        Mockito.doNothing().when(fileService).deleteFile(1L);

        mockMvc.perform(delete("/api/delete/1")).andExpect(status().isOk());
    }
}
