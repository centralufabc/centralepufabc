package central.centralepufabc;

/**
 * Created by Kleverson Nascimento on 14/03/2017.
 */

public class Funcionário {
    private String nome_func;
    private String cargo_func;
    private int imagem_func;

    public Funcionário(String nome_func, String cargo_func, int imagem_func) {
        this.nome_func = nome_func;
        this.cargo_func = cargo_func;
        this.imagem_func = imagem_func;
    }

    public String getNome_func() {
        return nome_func;
    }

    public void setNome_func(String nome_func) {
        this.nome_func = nome_func;
    }

    public String getCargo_func() {
        return cargo_func;
    }

    public void setCargo_func(String cargo_func) {
        this.cargo_func = cargo_func;
    }

    public int getImagem_func() {
        return imagem_func;
    }

    public void setImagem_func(int imagem_func) {
        this.imagem_func = imagem_func;
    }
}
