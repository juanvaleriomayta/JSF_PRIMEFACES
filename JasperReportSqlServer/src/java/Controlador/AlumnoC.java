package Controlador;

import Dao.ImplAlumnoD;
import Modelo.AlumnoM;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@SessionScoped
@Named(value = "alumnoC")
public class AlumnoC implements Serializable {

    private AlumnoM alu = new AlumnoM();
    private List<AlumnoM> listAlu;
    private String accionAlu;

    public List<String> completeTextUbigeo(String query) throws SQLException, Exception {
        ImplAlumnoD Dao = new ImplAlumnoD();
        return Dao.autocompleteUbigeo(query);
    }

    @PostConstruct
    public void inicio() {
        try {
            listarAlumno();
        } catch (Exception e) {
        }
    }

    public void operarAlumno() throws Exception {
        switch (accionAlu) {
            case "Registrar":
                registrarAlumno();
                break;
            case "Modificar":
                modificarAlumno();
                break;
        }
    }

    public void limpiarAlumno() {
        alu = new AlumnoM();
    }

    public void listarAlumno() throws Exception {
        ImplAlumnoD Dao;
        try {
            Dao = new ImplAlumnoD();
            listAlu = Dao.listarAlumno();
        } catch (Exception e) {
            throw e;
        }
    }

    public void registrarAlumno() throws Exception {
        ImplAlumnoD Dao;
        try {
            Dao = new ImplAlumnoD();
            alu.setCODUBI(Dao.obtenerCodigoUbigeo(alu.getNOMUBI()));
            Dao.registrarAlumno(alu);
            listarAlumno();
            limpiarAlumno();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Alumno", "Registrado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "YA ESTA REGISTRADO EL DNI"));
            throw e;
        }
    }

    public void leerAlumno(String codigoAlumno) throws Exception {
        ImplAlumnoD Dao;
        try {
            Dao = new ImplAlumnoD();
            alu = Dao.leerAlumno(codigoAlumno);
            accionAlu = "Modificar";
        } catch (Exception e) {
            throw e;
        }
    }

    public void modificarAlumno() throws Exception {
        ImplAlumnoD Dao;
        try {
            Dao = new ImplAlumnoD();
            Dao.ModificarAlumno(alu);
            alu.setCODUBI(Dao.obtenerCodigoUbigeo(alu.getNOMUBI()));
            listarAlumno();
            limpiarAlumno();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Alumno", "Modificado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", null));
            throw e;
        }
    }

    public void eliminarAlumno(AlumnoM alu) throws Exception {
        ImplAlumnoD Dao;
        try {
            Dao = new ImplAlumnoD();
            Dao.EliminarAlumno(alu);
            listarAlumno();
            limpiarAlumno();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Alumno", "Eliminado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", null));
            throw e;
        }
    }
    //DESCARGAR REPORTE DE ALUMNOS
    public void REPORTE_PDF_ALUMNO(String CodigoAlumno) throws Exception {
        ImplAlumnoD reportAlu = new ImplAlumnoD();
        try {
            Map<String, Object> parameters = new HashMap(); // Libro de parametros
            parameters.put(null, CodigoAlumno); //Insertamos un parametro
            reportAlu.REPORTE_PDF_ALUMNO(parameters); //Pido exportar Reporte con los parametros
//            report.exportarPDF2(parameters);
        } catch (Exception e) {
            throw e;
        }
    }

    public AlumnoM getAlu() {
        return alu;
    }

    public void setAlu(AlumnoM alu) {
        this.alu = alu;
    }

    public List<AlumnoM> getListAlu() {
        return listAlu;
    }

    public void setListAlu(List<AlumnoM> listAlu) {
        this.listAlu = listAlu;
    }

    public String getAccionAlu() {
        return accionAlu;
    }

    public void setAccionAlu(String accionAlu) {
        this.accionAlu = accionAlu;
    }

}
