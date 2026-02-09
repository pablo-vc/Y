import java.io.Serializable;

public class Persona implements Serializable {
    public int id;
    public String nombre;
    public boolean casado;
    public String sexo;

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

    public void getCasado() {
        return casado;
    }

}
