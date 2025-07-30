package es.cic.curso25.proy009.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.cic.curso25.proy009.Model.Rama;

public interface RamaRepository extends JpaRepository<Rama, Long>{

    List<Rama> findByArbolId(Long arbolId); //Sintaxis de jpa que equivale a arbol_id
}
