package es.cic.curso25.proy009.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import es.cic.curso25.proy009.Model.Arbol;
import es.cic.curso25.proy009.Model.Rama;
import es.cic.curso25.proy009.Repository.ArbolRepository;
import es.cic.curso25.proy009.Repository.RamaRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ArbolService {

    private final ArbolRepository arbolRepository;
    private final RamaRepository ramaRepository;

    public ArbolService(ArbolRepository arbolRepository, RamaRepository ramaRepository) {
        this.arbolRepository = arbolRepository;
        this.ramaRepository = ramaRepository;
    }

    public Arbol crearArbolyRamas(String especie, int numeroRamas){
        Arbol arbol = new Arbol(especie); //Creo un nuevo arbol
        for (int i = 0; i < numeroRamas; i++) {
        Rama rama = new Rama(i); // i será el número de hojas que lleva cada rama creada
        rama.setArbol(arbol); //dentro del for establezco la relacion de cada rama con su arbol 
        arbol.getRamas().add(rama); // getRamas devuelve una lista por lo que le hago un add de la rama creada  a la lista de ramas que tiene el arbol
        }
    return arbolRepository.save(arbol); //Una vez creado el arbol se añade a la bbdd con save 
    }

    public List<Arbol> listarArboles(){
        return arbolRepository.findAll();
    }

    public Arbol obtenerArbol(Long id){
        return arbolRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("Arbol no encontrado con id " + id));
    }

}
