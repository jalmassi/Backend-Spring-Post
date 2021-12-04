package com.justinalmassi.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.PostController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

    @ExtendWith(SpringExtension.class)
    @SpringBootTest
    @AutoConfigureMockMvc
public class PostApiTest {

        @Autowired
        private PostController postController;

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private WebApplicationContext context;


        @Test
        public void contextLoads(){
            assertThat(postController).isNotNull();
        }

        @BeforeEach
        public void setup() {
            mockMvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .build();
        }
}
