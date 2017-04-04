package central.centralepufabc;

/**
 * Created by Kleverson Nascimento on 03/04/2017.
 */

public class Gif {
    String nome_gif;
    String tag_gif;

    public String getNome_gif() {
        return nome_gif;
    }

    public void setNome_gif(String nome_gif) {
        this.nome_gif = nome_gif;
    }

    public String getTag_gif() {
        return tag_gif;
    }

    public void setTag_gif(String tag_gif) {
        this.tag_gif = tag_gif;
    }



    public Gif(String nome_gif, String tag_gif) {
        this.nome_gif = nome_gif;
        this.tag_gif = tag_gif;
    }




}
