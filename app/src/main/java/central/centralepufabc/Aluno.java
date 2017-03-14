package central.centralepufabc;

/**
 * Created by Kleverson Nascimento on 14/03/2017.
 */

public class Aluno {
    private String nome;
    private String frase;
    private int imagem;

    public Aluno(String nome, String frase, int imagem) {
        this.nome = nome;
        this.frase = frase;
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFrase() {
        return frase;
    }

    public void setFrase(String frase) {
        this.frase = frase;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }
}
