package central.centralepufabc;

/**
 * Created by Kleverson Nascimento on 08/05/2017.
 */

public class Monitoria_codigo {
    private String nome_materia;
    private String codigo_materia;

    public Monitoria_codigo(String nome_materia, String codigo_materia) {
        this.nome_materia = nome_materia;
        this.codigo_materia = codigo_materia;
    }

    public String getNome_materia() {
        return nome_materia;
    }

    public void setNome_materia(String nome_materia) {
        this.nome_materia = nome_materia;
    }

    public String getCodigo_materia() {
        return codigo_materia;
    }

    public void setCodigo_materia(String codigo_materia) {
        this.codigo_materia = codigo_materia;
    }
}
