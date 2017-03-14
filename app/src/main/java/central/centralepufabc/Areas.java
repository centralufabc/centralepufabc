package central.centralepufabc;

/**
 * Created by Kleverson Nascimento on 12/03/2017.
 */

public class Areas {
    private int imagem;
    private String nome;

    public Areas(int imagem, String nome) {
        this.imagem = imagem;
        this.nome = nome;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
