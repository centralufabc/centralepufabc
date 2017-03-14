package central.centralepufabc;

/**
 * Created by Kleverson Nascimento on 14/03/2017.
 */

public class Estudo {
    private String nome;
    private String desc;

    public Estudo(String nome, String desc) {
        this.nome = nome;
        this.desc = desc;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
