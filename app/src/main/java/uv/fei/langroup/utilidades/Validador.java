package uv.fei.langroup.utilidades;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Validador {
    private static final String CORREO_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String CONTRASENIA_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\W_])[A-Za-z\\d\\W_]{8,16}$";
    private static final Pattern correoPattern = Pattern.compile(CORREO_PATTERN);
    private static final Pattern contraseniaPattern = Pattern.compile(CONTRASENIA_PATTERN);

    public static boolean esCorreoValido (String correo) {
        if (correo == null) {
            return false;
        }

        Matcher matcher = correoPattern.matcher(correo);
        return matcher.matches();
    }

    public static boolean esContraseniaValida (String contrasenia) {
        if (contrasenia == null) {
            return false;
        }

        Matcher matcher = contraseniaPattern.matcher(contrasenia);
        return matcher.matches();
    }
}
