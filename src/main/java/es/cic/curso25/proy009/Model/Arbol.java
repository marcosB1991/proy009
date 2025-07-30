package es.cic.curso25.proy009.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Arbol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String especie;

    @OneToMany(mappedBy = "arbol", cascade = CascadeType.ALL, orphanRemoval = true) //relacion una a muchos rama va a tener clave foranea de arbol CascadetType.ALl define que cualquier operacion en arbol va a afectar a ramas. orphanRemoval = true indica que si elimina un rama de la lista también quiero que se elimina de la base de datos


    private List<Rama> ramas = new ArrayList<>();

    public Arbol() {
    }

    public Arbol(String especie) {
        this.especie = especie;
    }

    public Long getId() {
        return id;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    
    public List<Rama> getRamas() {
        return ramas;
    }

    public void setRamas(List<Rama> ramas) {
        this.ramas = ramas;
    }

    
}
