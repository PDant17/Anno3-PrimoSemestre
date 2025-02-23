package server;

import java.sql.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2025-02-16T11:02:48")
@StaticMetamodel(Oggetto.class)
public class Oggetto_ { 

    public static volatile SingularAttribute<Oggetto, String> descrizione;
    public static volatile SingularAttribute<Oggetto, String> luogo;
    public static volatile SingularAttribute<Oggetto, String> categoria;
    public static volatile SingularAttribute<Oggetto, Boolean> restituito;
    public static volatile SingularAttribute<Oggetto, Integer> id;
    public static volatile SingularAttribute<Oggetto, Date> ritrovamento;

}