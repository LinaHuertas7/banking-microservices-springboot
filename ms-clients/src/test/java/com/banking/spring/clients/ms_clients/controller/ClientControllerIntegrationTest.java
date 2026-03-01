package com.banking.spring.clients.ms_clients.controller;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.banking.spring.clients.ms_clients.DTO.request.ClientRequestDTO;
import com.banking.spring.clients.ms_clients.DTO.response.ClientResponseDTO;
import com.banking.spring.clients.ms_clients.enums.Gender;
import com.banking.spring.clients.ms_clients.exception.ClientNotFoundException;
import com.banking.spring.clients.ms_clients.service.ClientServiceInterface;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ClientController.class)
@DisplayName("Integration tests for ClientController")
public class ClientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private ClientServiceInterface clientService;

    private ClientRequestDTO requestDTO;
    private ClientResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new ClientRequestDTO();
        requestDTO.setName("Jose Lema");
        requestDTO.setGender(Gender.MALE);
        requestDTO.setAge(35);
        requestDTO.setIdentification("1234567890");
        requestDTO.setAddress("Otavalo sn y principal");
        requestDTO.setPhone("098254785");
        requestDTO.setPassword("1234");
        requestDTO.setStatus(true);

        responseDTO = ClientResponseDTO.builder()
                .slug("slug-jose-lema")
                .name("Jose Lema")
                .gender(Gender.MALE)
                .age(35)
                .identification("1234567890")
                .address("Otavalo sn y principal")
                .phone("098254785")
                .status(true)
                .build();
    }

    @Test
    @DisplayName("POST /clientes - Should create a new client and return 201 with created client")
    void createClient_shouldReturn201_withCreatedClient() throws Exception {
        when(clientService.create(any(ClientRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.slug", is("slug-jose-lema")))
                .andExpect(jsonPath("$.name", is("Jose Lema")))
                .andExpect(jsonPath("$.identification", is("1234567890")))
                .andExpect(jsonPath("$.status", is(true)));
    }

    @Test
    @DisplayName("POST /clientes - Should return 400 when required fields are missing")
    void createClient_shouldReturn400_whenMissingRequiredFields() throws Exception {
        ClientRequestDTO invalidRequest = new ClientRequestDTO();

        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /clientes - Should return 200 with a list of clients when clients exist")
    void findAll_shouldReturn200_withClientList() throws Exception {
        ClientResponseDTO second = ClientResponseDTO.builder()
                .slug("slug-marianela")
                .name("Marianela Montalvo")
                .gender(Gender.FEMALE)
                .age(28)
                .identification("0987654321")
                .status(true)
                .build();

        when(clientService.findAll()).thenReturn(List.of(responseDTO, second));

        mockMvc.perform(get("/clientes")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Jose Lema")))
                .andExpect(jsonPath("$[1].name", is("Marianela Montalvo")));
    }

    @Test
    @DisplayName("GET /clientes - Should return 200 with an empty list when no clients exist")
    void findAll_shouldReturn200_withEmptyList() throws Exception {
        when(clientService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/clientes")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("GET /clientes/{slug} - Should return 200 with client details when client exists")
    void findBySlug_shouldReturn200_whenClientExists() throws Exception {
        when(clientService.findBySlug("slug-jose-lema")).thenReturn(responseDTO);

        mockMvc.perform(get("/clientes/slug-jose-lema")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slug", is("slug-jose-lema")))
                .andExpect(jsonPath("$.name", is("Jose Lema")));
    }

    @Test
    @DisplayName("GET /clientes/{slug} - Should return 404 when client does not exist")
    void findBySlug_shouldReturn404_whenClientNotFound() throws Exception {
        when(clientService.findBySlug("slug-inexistente"))
                .thenThrow(new ClientNotFoundException("No se encontro el cliente con el slug slug-inexistente"));

        mockMvc.perform(get("/clientes/slug-inexistente")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("slug-inexistente")));
    }

    @Test
    @DisplayName("PUT /clientes/{slug} - Should return 200 with updated client when client exists")
    void replace_shouldReturn200_withUpdatedClient() throws Exception {
        when(clientService.replace(eq("slug-jose-lema"), any(ClientRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/clientes/slug-jose-lema")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slug", is("slug-jose-lema")));
    }

    @Test
    @DisplayName("DELETE /clientes/{slug} - Should return 204 when client is successfully deleted")
    void delete_shouldReturn204_whenClientDeleted() throws Exception {
        doNothing().when(clientService).delete("slug-jose-lema");

        mockMvc.perform(delete("/clientes/slug-jose-lema"))
                .andExpect(status().isNoContent());

        verify(clientService).delete("slug-jose-lema");
    }

    @Test
    @DisplayName("DELETE /clientes/{slug} - Should return 404 when client to delete is not found")
    void delete_shouldReturn404_whenClientNotFound() throws Exception {
        doThrow(new ClientNotFoundException("No se encontro el cliente"))
                .when(clientService).delete("slug-inexistente");

        mockMvc.perform(delete("/clientes/slug-inexistente"))
                .andExpect(status().isNotFound());
    }
}
