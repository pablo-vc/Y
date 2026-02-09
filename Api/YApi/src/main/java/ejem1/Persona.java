package ejem1;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement // Utilizado para generar un XML que representa esta clase
public class Persona implements Serializable {
    public int id;
    public String nombre;
    public boolean casado;
    public String sexo;

    public Persona(int id, String nombre, boolean casado, String sexo) {
        this.id = id;
        this.nombre = nombre;
        this.casado = casado;
        this.sexo = sexo;
    }

    public Persona() {
        this(0, "vacio", false, "mucho");
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getSexo() {
        return sexo;
    }

    public void setCasado(boolean casado) {
        this.casado = casado;
    }

    public Boolean getCasado() {
        return this.casado;
    }

}
