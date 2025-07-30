package es.cic.curso25.proy009.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Rama {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numHOjas;

    @ManyToOne //Muchas ramas a un arbol
    @JoinColumn(name="arbol_id") //Clave foranea de arbol
    @JsonBackReference// Define que este lado NO se serializa para evitar ciclos
    private Arbol arbol; //Para establecer relacion con arbol

    public Rama() {
    }

    public Rama(int numHOjas) {
        this.numHOjas = numHOjas;
    }

    public int getNumHOjas() {
        return numHOjas;
    }

    public void setNumHOjas(int numHOjas) {
        this.numHOjas = numHOjas;
    }

    public Arbol getArbol() {
        return arbol;
    }

    public void setArbol(Arbol arbol) {
        this.arbol = arbol;
    }

    
}
