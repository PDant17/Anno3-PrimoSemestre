/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import static server.Circolo.*;

/**
 *
 * @author CORSO_PD
 */
@Entity
@Table(name = "Circolo")
@NamedQueries({
    @NamedQuery(name = FIND_ALL, query = "SELECT c FROM Circolo c"),
    @NamedQuery(name = FIND_BY_ID, query = "SELECT c FROM Circolo c WHERE c.id = :id"),
    @NamedQuery(name = FIND_BY_REGIONE, query = "SELECT c FROM Circolo c WHERE c.regione = :regione")
})
public class Circolo implements Serializable {
    
    public static final String FIND_ALL = "Circolo.findAll";
    public static final String FIND_BY_ID = "Circolo.findById";
    public static final String FIND_BY_REGIONE = "Circolo.findByRegione";
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    private String nome;
    private String responsabile;
    private String email;
    private String citta;
    private String provincia;
    private String regione;

    public Circolo() {
    }

    public Circolo(String nome, String responsabile, String email, String citta, String provincia, String regione) {
        this.nome = nome;
        this.responsabile = responsabile;
        this.email = email;
        this.citta = citta;
        this.provincia = provincia;
        this.regione = regione;
    }

    public Circolo(int id, String nome, String responsabile, String email, String citta, String provincia, String regione) {
        this.id = id;
        this.nome = nome;
        this.responsabile = responsabile;
        this.email = email;
        this.citta = citta;
        this.provincia = provincia;
        this.regione = regione;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getResponsabile() {
        return responsabile;
    }

    public void setResponsabile(String responsabile) {
        this.responsabile = responsabile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getRegione() {
        return regione;
    }

    public void setRegione(String regione) {
        this.regione = regione;
    }

    @Override
    public String toString() {
        return "Circolo{" + "id=" + id + ", nome=" + nome + ", responsabile=" + responsabile + ", email=" + email + ", citta=" + citta + ", provincia=" + provincia + ", regione=" + regione + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.id;
        hash = 53 * hash + Objects.hashCode(this.nome);
        hash = 53 * hash + Objects.hashCode(this.responsabile);
        hash = 53 * hash + Objects.hashCode(this.email);
        hash = 53 * hash + Objects.hashCode(this.citta);
        hash = 53 * hash + Objects.hashCode(this.provincia);
        hash = 53 * hash + Objects.hashCode(this.regione);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Circolo other = (Circolo) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.responsabile, other.responsabile)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.citta, other.citta)) {
            return false;
        }
        if (!Objects.equals(this.provincia, other.provincia)) {
            return false;
        }
        if (!Objects.equals(this.regione, other.regione)) {
            return false;
        }
        return true;
    }
    
}
