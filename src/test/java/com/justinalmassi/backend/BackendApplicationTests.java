package com.justinalmassi.backend;

import com.justinalmassi.backend.controller.PostController;
import com.justinalmassi.backend.error_handling.SortByException;
import com.justinalmassi.backend.model.PostResponse;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.justinalmassi.backend.service.PostService;
import org.springframework.web.util.NestedServletException;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BackendApplication.class)
@WebAppConfiguration
class BackendApplicationTests {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @Autowired
    private PostController postController;
    @MockBean
    private PostService postService;

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    void contextLoads() {
        assertThat(postController).isNotNull();
    }

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldReturn200WhenSendingRequestToController() throws Exception {
        String uri = "/api/ping";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void shouldReturn400WhenSendingBadRequest() throws Exception {
        String uri = "/api/posts?";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        Assert.assertEquals(400, mvcResult.getResponse().getStatus());
    }


    @Test
    public void invalidTagsSendingRequestToController() throws Exception {
        String[] invalidUrlArray = new String[]{"/api/posts?tags",
                "/api/posts?tags=      "};
        for (String url : invalidUrlArray) {

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url)
                            .accept(MediaType.APPLICATION_JSON_VALUE))
                    .andDo(print()).andReturn();

            PostResponse expectedPost = new PostResponse();
            expectedPost.setPosts(null);
            expectedPost.setStatus(HttpStatus.BAD_REQUEST);
            expectedPost.setStatusMessage("tag is empty: add tag");

            String actual = mvcResult.getResponse().getContentAsString();
            JSONObject actualJson = new JSONObject(actual);
            System.out.println("url=" + url + " actual=" + actual);

            Assert.assertEquals(expectedPost.getStatusMessage(), actualJson.get("message"));
            Assert.assertEquals(expectedPost.getStatus().value(), actualJson.get("status code"));
        }
    }

    @Test
    public void invalidSortByHash() throws Exception {
        try {
            String invalidUrlArray = "/api/posts?tag=health,tech&sortBy=####";
            mockMvc.perform(MockMvcRequestBuilders.get(invalidUrlArray)
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        } catch (NestedServletException e) {
            Assert.assertEquals(e.getRootCause().getMessage(), "Invalid SortBy tag");
            System.out.println(e.getRootCause().getMessage());
        }
    }

    @Test
    public void invalidSortByQuotation() throws Exception {
        try {
            String invalidUrlArray = "/api/posts?tag=health,tech&sortBy= \"";
            mockMvc.perform(MockMvcRequestBuilders.get(invalidUrlArray)
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        } catch (NestedServletException e) {
            Assert.assertEquals(e.getRootCause().getMessage(), "Invalid SortBy tag");
            System.out.println(e.getRootCause().getMessage());
        }
    }

    @Test
    public void invalidSortByTags() throws Exception {
        try {
            String invalidUrlArray = "/api/posts?tag=health,tech&sortBy=tags\"";
            mockMvc.perform(MockMvcRequestBuilders.get(invalidUrlArray)
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        } catch (NestedServletException e) {
            Assert.assertEquals(e.getRootCause().getMessage(), "Invalid SortBy tag");
            System.out.println(e.getRootCause().getMessage());
        }
    }

    @Test
    public void invalidSortByIdQuotation() throws Exception {
        try {
            String invalidUrlArray = "/api/posts?tag=health,tech&sortBy=id\"";
            mockMvc.perform(MockMvcRequestBuilders.get(invalidUrlArray)
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        } catch (NestedServletException e) {
            Assert.assertEquals(e.getRootCause().getMessage(), "Invalid SortBy tag");
            System.out.println(e.getRootCause().getMessage());
        }
    }

    @Test
    public void invalidDirectionHash() throws Exception {
        try {
            String invalidUrlArray = "/api/posts?tag=health,tech&direction=####";
            mockMvc.perform(MockMvcRequestBuilders.get(invalidUrlArray)
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        } catch (NestedServletException e) {
            System.out.println(e.getRootCause().getMessage());
            Assert.assertEquals(e.getRootCause().getMessage(), "Invalid Direction tag");
        }
    }
    @Test
    public void invalidDirectionQuotation() throws Exception {
        try {
            String invalidUrlArray = "/api/posts?tag=health,tech&sortBy=likes&direction= \"";
            mockMvc.perform(MockMvcRequestBuilders.get(invalidUrlArray)
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        } catch (NestedServletException e) {
            System.out.println(e.getRootCause().getMessage());
            Assert.assertEquals(e.getRootCause().getMessage(), "Invalid Direction tag");
        }
    }
    @Test
    public void invalidDirectionUp() throws Exception {
        try {
            String invalidUrlArray = "/api/posts?tag=health,tech&direction=up\"";
            mockMvc.perform(MockMvcRequestBuilders.get(invalidUrlArray)
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        } catch (NestedServletException e) {
            System.out.println(e.getRootCause().getMessage());
            Assert.assertEquals(e.getRootCause().getMessage(), "Invalid Direction tag");
        }
    }

}
