package central.centralepufabc;

/**
 * Created by Kleverson Nascimento on 02/04/2017.
 */

public class Monitoria_obj {
    private String nome_materia;
    private String detalhes_monitoria;

    public Monitoria_obj(String nome_materia, String detalhes_monitoria) {
        this.nome_materia = nome_materia;
        this.detalhes_monitoria = detalhes_monitoria;
    }

    public String getNome_materia() {
        return nome_materia;
    }

    public void setNome_materia(String nome_materia) {
        this.nome_materia = nome_materia;
    }

    public String getDetalhes_monitoria() {
        return detalhes_monitoria;
    }

    public void setDetalhes_monitoria(String detalhes_monitoria) {
        this.detalhes_monitoria = detalhes_monitoria;
    }




}
