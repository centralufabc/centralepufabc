package central.centralepufabc;

/**
 * Created by Kleverson Nascimento on 15/03/2017.
 */

public class Dia_importante {
    private String dia;
    private String mes;
    private String data;
    private String notificar;

    public Dia_importante(String dia, String mes, String data, String notificar) {
        this.dia = dia;
        this.mes = mes;
        this.data = data;
        this.notificar = notificar;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNotificar() {
        return notificar;
    }

    public void setNotificar(String notificar) {
        this.notificar = notificar;
    }
}
