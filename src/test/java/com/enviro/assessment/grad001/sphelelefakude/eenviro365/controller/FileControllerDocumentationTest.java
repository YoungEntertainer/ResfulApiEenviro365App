package com.enviro.assessment.grad001.sphelelefakude.eenviro365.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@WebMvcTest(FileController.class)
@AutoConfigureMockMvc
public class FileControllerDocumentationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestDocumentationContextProvider restDocumentation;

    @Autowired
    private WebApplicationContext context;
    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(this.restDocumentation))
                .alwaysDo(document("{method-name}",
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
    }

    @Test
    public void testGetFileById() throws Exception {
        mockMvc.perform(get("/api/files/{id}", 1))
                .andExpect(status().isOk())
                .andDo(document("files/get",
                        pathParameters(
                                parameterWithName("id").description("The ID of the file to retrieve")
                        ),
                        responseFields(
                                fieldWithPath("id").description("The ID of the file").type(JsonFieldType.NUMBER),
                                fieldWithPath("fileData").description("The data of the file").type(JsonFieldType.STRING)
                        )
                ));
    }

    @Test
    public void testUpdateFile() throws Exception {
        mockMvc.perform(put("/api/files/{id}", 1)
                .content("{\"fileData\": \"updated data\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("files/update",
                        pathParameters(
                                parameterWithName("id").description("The ID of the file to update")
                        ),
                        requestFields(
                                fieldWithPath("fileData").description("The updated data of the file").type(JsonFieldType.STRING)
                        ),
                        responseFields(
                                fieldWithPath("id").description("The ID of the updated file").type(JsonFieldType.NUMBER),
                                fieldWithPath("fileData").description("The updated data of the file").type(JsonFieldType.STRING)
                        )
                ));
    }
}
