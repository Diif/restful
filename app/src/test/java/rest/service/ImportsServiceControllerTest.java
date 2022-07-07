package rest.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import rest.AbstractTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class ImportsServiceControllerTest extends AbstractTest {
    @Test
    public void giveItemsWithSameUUID_thenReturnError() throws IOException {
        System.out.println(new File(".").getAbsolutePath());
        File requestFile = new File("src/test/resources/ImportsApiController/SameUUIDRequest.txt");
        File responseFile =  new File("src/test/resources/ImportsApiController/ErrorResponse.txt");
        Assertions.assertTrue(requestFile.exists());
        Assertions.assertTrue(responseFile.exists());

        String requestString = Files.readString(responseFile.toPath());
        String responseExpected = Files.readString(responseFile.toPath());

        String uri = "/imports";
        MvcResult result = null;
        try {
            result = mockMvc.perform(post(uri)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(requestString).
                    accept(MediaType.APPLICATION_JSON_VALUE)).
                    andReturn();
        } catch (Exception e){
            fail("Exception during /imports");
        }

        int status = result.getResponse().getStatus();
        assertEquals(status, 400);

        String responseJSON = result.getResponse().getContentAsString();
        System.out.println(responseJSON);
        assertEquals(responseJSON,responseExpected);
    }

    @Test
    public void giveIncorrectItems_thenReturnBadRequest(){

    }
}
