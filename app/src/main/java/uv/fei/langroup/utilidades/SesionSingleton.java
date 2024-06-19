package uv.fei.langroup.utilidades;

import uv.fei.langroup.modelo.POJO.Colaborador;

public class SesionSingleton {
    private static SesionSingleton instance;

    public Colaborador colaborador;

    public String token;

    public static SesionSingleton getInstance() {
        if (instance == null) {
            instance = new SesionSingleton();
        }

        return instance;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
