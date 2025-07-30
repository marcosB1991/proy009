package es.cic.curso25.proy009.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.cic.curso25.proy009.Model.Arbol;
import es.cic.curso25.proy009.Model.Rama;
import es.cic.curso25.proy009.Service.ArbolService;

@RestController
@RequestMapping("/arboles")
public class ArbolController {

    private final ArbolService arbolService;

    public ArbolController(ArbolService arbolService) {
        this.arbolService = arbolService;
    }

    @GetMapping
    public List<Arbol> listarArboles(){
        return arbolService.listarArboles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Arbol> obtenerArbol(@PathVariable Long id){
        Arbol arbol= arbolService.obtenerArbol(id);
        return ResponseEntity.ok(arbol);
    }
    
    @PostMapping
    public ResponseEntity<Arbol> crearArbol(@RequestParam String especie, @RequestParam int numRamas){
        Arbol arbol= arbolService.crearArbolyRamas(especie, numRamas);
        return ResponseEntity.ok(arbol);
    }

    @PostMapping("/{id}/rama")
    public ResponseEntity <Arbol> agregarRama(@PathVariable Long id, @RequestBody Rama ramaAgregada)
    //Se usa requestbody porque estoy pasando un objeto request param lo uso para int boolean string, etc..
    {
        Arbol arbol = arbolService.agregarRama(id, ramaAgregada);
        return ResponseEntity.ok(arbol);
    }

    

}
