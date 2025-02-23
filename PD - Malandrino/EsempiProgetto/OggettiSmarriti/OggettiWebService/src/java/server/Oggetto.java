/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import static server.Oggetto.*;

/**
 *
 * @author CORSO_PD
 */
@Entity
@Table(name = "Oggetto")
@NamedQueries({
    @NamedQuery(name = FIND_ALL, query = "SELECT o FROM Oggetto o"),
    @NamedQuery(name = FIND_BY_CATEGORIA, query = "SELECT o FROM Oggetto o WHERE o.categoria = :categoria"),
    @NamedQuery(name = FIND_BY_LUOGO, query = "SELECT o FROM Oggetto o WHERE o.luogo = :luogo"),
    @NamedQuery(name = FIND_BY_ID, query = "SELECT o FROM Oggetto o WHERE o.id = :id")
})
public class Oggetto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public static final String FIND_ALL = "Oggetto.findAll";
    public static final String FIND_BY_CATEGORIA = "Oggetto.findByCategoria";
    public static final String FIND_BY_LUOGO = "Oggetto.findByLuogo";
    public static final String FIND_BY_ID = "Oggetto.findById";
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String descrizione;
    private String categoria;
    private String luogo;
    private Date ritrovamento;
    private boolean restituito;

    public Oggetto() {
        
    }
    
    public Oggetto(String descrizione, String categoria, String luogo, Date ritrovamento, boolean restituito) {
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.luogo = luogo;
        this.ritrovamento = ritrovamento;
        this.restituito = restituito;
    }
    
    public Oggetto(int id, String descrizione, String categoria, String luogo, Date ritrovamento, boolean restituito) {
        this.id = id;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.luogo = luogo;
        this.ritrovamento = ritrovamento;
        this.restituito = restituito;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public Date getData() {
        return ritrovamento;
    }

    public void setData(Date ritrovamento) {
        this.ritrovamento = ritrovamento;
    }

    public boolean getRestituito() {
        return restituito;
    }

    public void setRestituito(boolean restituito) {
        this.restituito = restituito;
    }

    @Override
    public String toString() {
        return "Oggetto{" + "id=" + id + ", descrizione=" + descrizione + ", categoria=" + categoria + ", luogo=" + luogo + ", ritrovamento=" + ritrovamento + ", restituito=" + restituito + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.descrizione);
        hash = 67 * hash + Objects.hashCode(this.categoria);
        hash = 67 * hash + Objects.hashCode(this.luogo);
        hash = 67 * hash + Objects.hashCode(this.ritrovamento);
        hash = 67 * hash + Objects.hashCode(this.restituito);
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
        final Oggetto other = (Oggetto) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.descrizione, other.descrizione)) {
            return false;
        }
        if (!Objects.equals(this.categoria, other.categoria)) {
            return false;
        }
        if (!Objects.equals(this.luogo, other.luogo)) {
            return false;
        }
        if (!Objects.equals(this.ritrovamento, other.ritrovamento)) {
            return false;
        }
        if (!Objects.equals(this.restituito, other.restituito)) {
            return false;
        }
        return true;
    }
    
}
