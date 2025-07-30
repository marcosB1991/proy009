package es.cic.curso25.proy009.Controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.curso25.proy009.Model.Arbol;
import es.cic.curso25.proy009.Model.Rama;
import es.cic.curso25.proy009.Service.ArbolService;

@SpringBootTest
@AutoConfigureMockMvc
public class ArbolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    
    @MockBean
    private ArbolService arbolService;

    @Autowired
    private ObjectMapper objectMapper;

    private String especie = "Olivo";

    @Test
    void crearArbol() throws Exception {
        int numRamas = 3;

        // Crear un árbol con ramas via POST /arboles?especie=Olivo&numRamas=3
        mockMvc.perform(post("/arboles")
                .param("especie", especie)
                .param("numRamas", String.valueOf(numRamas)))
                .andExpect(status().isOk()) // Verifica un 200
                .andExpect(jsonPath("$.especie").value(especie))// verica el body del json tenga un campo llamado
                                                                    // especie con el valor que le he pasado
                .andExpect(jsonPath("$.ramas.length()").value(numRamas)); // verifica el numero
    }

    @Test
    void listarArboles() throws Exception {
        List<Arbol> lista = List.of(
                new Arbol("Olivo"),
                new Arbol("Pino"));

        when(arbolService.listarArboles()).thenReturn(lista);

        mockMvc.perform(get("/arboles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].especie").value("Olivo"))
                .andExpect(jsonPath("$[1].especie").value("Pino"));
    }

    @Test
    void eliminarArbol() throws Exception {
        int numRamas = 1;

        // Crear árbol para eliminar
        String response = mockMvc.perform(post("/arboles")
                .param("especie", especie)
                .param("numRamas", String.valueOf(numRamas)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Long arbolId = objectMapper.readTree(response).get("id").asLong();

        // Eliminar árbol
        mockMvc.perform(delete("/arboles/" + arbolId))
                .andExpect(status().isNoContent());

        // Intentar obtener árbol eliminado debe dar error 404
        mockMvc.perform(get("/arboles/" + arbolId))
                .andExpect(status().isNotFound());
    }
}
