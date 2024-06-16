package uv.fei.langroup.Utilidades;

import android.content.Context;
import android.widget.Toast;

public class Mensajes {

    public void showMessage(String msj, Context context){
        Toast.makeText(context, msj, Toast.LENGTH_SHORT).show();
    }
}
