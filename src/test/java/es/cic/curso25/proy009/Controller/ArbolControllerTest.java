package es.cic.curso25.proy009.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.cic.curso25.proy009.Model.Rama;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ArbolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long arbolId;

    @BeforeEach
    void setup() throws Exception {
        // Crear un árbol para usar en las pruebas
        String especie = "Pino";
        int numRamas = 3;

        String response = mockMvc.perform(post("/arboles")
                .param("especie", especie)
                .param("numRamas", String.valueOf(numRamas))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Obtener el id del árbol creado
        arbolId = objectMapper.readTree(response).get("id").asLong();
    }

    @Test
    void listarArboles() throws Exception {
        mockMvc.perform(get("/arboles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void obtenerArbol() throws Exception {
        mockMvc.perform(get("/arboles/{id}", arbolId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(arbolId))
                .andExpect(jsonPath("$.especie").value("Pino"));
    }

    @Test
    void crearArbol() throws Exception {
        mockMvc.perform(post("/arboles")
                .param("especie", "Roble")
                .param("numRamas", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.especie").value("Roble"))
                .andExpect(jsonPath("$.ramas.length()").value(2));
    }

    @Test
    void agregarRama() throws Exception {
        Rama nuevaRama = new Rama();
        nuevaRama.setNumHOjas(10);

        String ramaJson = objectMapper.writeValueAsString(nuevaRama);

        mockMvc.perform(post("/arboles/{id}/rama", arbolId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ramaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ramas").isArray())
                .andExpect(jsonPath("$.ramas[?(@.numHOjas == 10)]").exists());
    }

    @Test
    void eliminarRama() throws Exception {
        // Primero, agregar una rama para eliminarla después
        Rama ramaParaEliminar = new Rama();
        ramaParaEliminar.setNumHOjas(5);

        String ramaJson = objectMapper.writeValueAsString(ramaParaEliminar);

        String response = mockMvc.perform(post("/arboles/{id}/rama", arbolId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ramaJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long ramaId = objectMapper.readTree(response)
                .get("ramas")
                .get(response.length() - 1) //última rama añadida, para asegurar obtener id
                .get("id")
                .asLong();

        // Nota: Si da problemas el método para obtener ramaId, podemos obtenerlo en otro paso.

        mockMvc.perform(delete("/arboles/rama/{id}", ramaId))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminarArbol() throws Exception {
        mockMvc.perform(delete("/arboles/{id}", arbolId))
                .andExpect(status().isNoContent());

        // Luego, validar que ya no existe
        mockMvc.perform(get("/arboles/{id}", arbolId))
                .andExpect(status().is4xxClientError());
    }
}
