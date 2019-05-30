package Interface;

import Modelo.AlumnoM;
import java.util.List;

public interface AlumnoI {

    void registrarAlumno(AlumnoM alu) throws Exception;

    void ModificarAlumno(AlumnoM alu) throws Exception;

    void EliminarAlumno(AlumnoM alu) throws Exception;

    List<AlumnoM> listarAlumno() throws Exception;
}
