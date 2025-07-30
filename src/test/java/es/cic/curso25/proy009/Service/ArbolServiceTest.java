package es.cic.curso25.proy009.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.*;

import es.cic.curso25.proy009.Model.Arbol;
import es.cic.curso25.proy009.Model.Rama;
import es.cic.curso25.proy009.Repository.ArbolRepository;
import es.cic.curso25.proy009.Repository.RamaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class ArbolServiceTest {

    @Mock
    private ArbolRepository arbolRepository;

    @Mock
    private RamaRepository ramaRepository;

    @InjectMocks
    private ArbolService arbolService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearArbolyRamas() {
        String especie = "Olivo";
        int numeroRamas = 3;

        // Devolver el mismo objeto que se pasa a save PREGUNTADO A GPT ESTE TEST NO
        // ENTIENDO ESTA LINEA CONSULTAR!!!!
        when(arbolRepository.save(any(Arbol.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Arbol resultado = arbolService.crearArbolyRamas(especie, numeroRamas);

        assertNotNull(resultado);
        assertEquals(especie, resultado.getEspecie());
        assertEquals(numeroRamas, resultado.getRamas().size());

        verify(arbolRepository).save(any(Arbol.class));
    }

    @Test
    void listarArboles() {
        List<Arbol> arboles = List.of(new Arbol("Pino"), new Arbol("Olivo"));
        when(arbolRepository.findAll()).thenReturn(arboles);

        List<Arbol> resultado = arbolService.listarArboles();

        assertEquals(2, resultado.size());
        verify(arbolRepository).findAll();
    }

    @Test
    void obtenerArbol() {
        Long id = 1L;
        Arbol arbol = new Arbol("Olivo");
        when(arbolRepository.findById(id)).thenReturn(Optional.of(arbol));

        Arbol resultado = arbolService.obtenerArbol(id);

        assertNotNull(resultado);
        assertEquals("Olivo", resultado.getEspecie());
    }

    @Test
    void obtenerArbol_lanzaExcepcion() {
        Long id = 1L;
        when(arbolRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ArbolNoEncontradoException.class, () -> arbolService.obtenerArbol(id));
    }

    @Test
    void agregarRama() {
        Long arbolId = 1L;
        Arbol arbol = new Arbol("Olivo");
        arbol.setRamas(new ArrayList<>());

        Rama rama = new Rama(5);

        // Simulamos el comportamiento del repositorio:
        // Cuando se llama a findById con el ID del árbol, se devuelve un Optional con
        // el árbol simulado.
        // Cuando se llama a save con el árbol (ya con la lista de ramas), se devuelve el
        // mismo árbol.
        // Esto permite probar la lógica del servicio sin necesidad de acceder a una
        // base de datos real.
        when(arbolRepository.findById(arbolId)).thenReturn(Optional.of(arbol));
        when(arbolRepository.save(arbol)).thenReturn(arbol);

        Arbol resultado = arbolService.agregarRama(arbolId, rama);

        //Asegura que no damos null
        assertNotNull(resultado);
        //COmprueba que se ha añadido la rama con 5 hojas
        assertTrue(resultado.getRamas().contains(rama));
        
        //verifica que nuestro arbol y el arbol que recibe la rama a través de su get es el mismo
        assertEquals(arbol, rama.getArbol());

        //Verifica uso de save
        verify(arbolRepository).save(arbol);
    }

    @Test
    void eliminarRama() {
        Long ramaId = 1L;
        when(ramaRepository.existsById(ramaId)).thenReturn(true);

        arbolService.eliminarRama(ramaId);

        verify(ramaRepository).deleteById(ramaId);
    }

    @Test
    void eliminarRama_lanzaExcepcion() {
        Long ramaId = 1L;
        when(ramaRepository.existsById(ramaId)).thenReturn(false);

        //Verifica que se está lanzando está excepcion desde eliminarRama
        assertThrows(RamaNoEncontradaException.class, () -> arbolService.eliminarRama(ramaId));
    }

    @Test
    void eliminarArbol() {
        Long arbolId = 1L;
        when(arbolRepository.existsById(arbolId)).thenReturn(true);

        arbolService.eliminarArbol(arbolId);

        verify(arbolRepository).deleteById(arbolId);
    }

    @Test
    void eliminarArbol_lanzaExcepcion() {
        Long arbolId = 1L;
        when(arbolRepository.existsById(arbolId)).thenReturn(false);

        assertThrows(ArbolNoEncontradoException.class, () -> arbolService.eliminarArbol(arbolId));
    }
}
