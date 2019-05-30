package Dao;

import Interface.AlumnoI;
import Modelo.AlumnoM;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class ImplAlumnoD extends Dao implements AlumnoI {

    @Override
    public void registrarAlumno(AlumnoM alu) throws Exception {
        try {
            Conectar();
//            String sql = "INSERT INTO ALUMNO (NOMALU,APEALU,DNIALU,DIRALU,FECNACALU,CODUBI) "
//                    + "VALUES(?,?,?,?,CONVERT(DATE,?,105),?)";
            String sql = "EXEC SP_REGISTRAR_ALUMNO ?,?,?,?,?,?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, alu.getNOMALU());
            ps.setString(2, alu.getAPEALU());
            ps.setString(3, alu.getDNIALU());
            ps.setString(4, alu.getDIRALU());
            ps.setString(5, alu.getFECNACALU());
            ps.setString(6, alu.getCODUBI());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            Cerrar();
        }
    }

    @Override
    public void ModificarAlumno(AlumnoM alu) throws Exception {
        try {
            Conectar();
            String sql = "UPDATE ALUMNO SET NOMALU=?, APEALU=?, DNIALU=?, DIRALU=?, FECNACALU=?, CODUBI=? WHERE CODALU=?";
//            String sql = "EXEC SP_ACTUALIZAR_ALUMNO ?,?,?,?,?,?,?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, alu.getNOMALU());
            ps.setString(2, alu.getAPEALU());
            ps.setString(3, alu.getDNIALU());
            ps.setString(4, alu.getDIRALU());
            ps.setString(5, alu.getFECNACALU());
            ps.setString(6, alu.getCODUBI());
            ps.setString(7, alu.getCODALU());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            Cerrar();
        }
    }

    public String obtenerCodigoUbigeo(String Ubigeo) throws SQLException, Exception {
        ResultSet rs;
        try {
            Conectar();
            String sql = "SELECT CODUBI FROM UBIGEO WHERE CONCAT(DEPUBI,' ',PROUBI,' ', DISUBI) LIKE ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, Ubigeo);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("CODUBI");
            }
            return null;
        } catch (SQLException e) {
            throw e;
        }
    }

    public List<String> autocompleteUbigeo(String Consulta) throws SQLException, Exception {
        ResultSet rs;
        List<String> Lista;
        try {
            Conectar();

            String sql = "SELECT CONCAT(DEPUBI,' ',PROUBI,' ',DISUBI) AS DISUBI FROM UBIGEO WHERE DISUBI LIKE ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, "%" + Consulta + "%");
            Lista = new ArrayList<>();
            rs = ps.executeQuery();
            while (rs.next()) {

                Lista.add(rs.getString("DISUBI"));
            }
            return Lista;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void EliminarAlumno(AlumnoM alu) throws Exception {
        try {
            Conectar();
            String sql = "DELETE FROM ALUMNO WHERE CODALU = ?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, alu.getCODALU());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            Cerrar();
        }
    }

    public AlumnoM leerAlumno(String codigoAlumno) throws Exception {
        AlumnoM alu = null;
        ResultSet rs;
        try {
            Conectar();
            String sql = "SELECT CODALU,NOMALU,APEALU,DNIALU,DIRALU,"
                    + "FORMAT(CONVERT(DATE,FECNACALU,"
                    + "105),'dd/MM/yyyy','en-gb') AS FECNACALU,UBIGEO.CODUBI,"
                    + "CONCAT(UBIGEO.DEPUBI,' ',UBIGEO.PROUBI,' ',UBIGEO.DISUBI) AS NOMUBI FROM ALUMNO"
                    + "INNER JOIN UBIGEO ON UBIGEO.CODUBI = ALUMNO.CODUBI WHERE CODALU LIKE ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, codigoAlumno);
            rs = ps.executeQuery();
            if (rs.next()) {
                alu = new AlumnoM();
                alu.setCODALU(rs.getString("CODALU"));
                alu.setNOMALU(rs.getString("NOMALU"));
                alu.setAPEALU(rs.getString("APEALU"));
                alu.setDNIALU(rs.getString("DNIALU"));
                alu.setDIRALU(rs.getString("DIRALU"));
                alu.setFECNACALU(rs.getString("FECNACALU"));
                alu.setNOMUBI(rs.getString("NOMUBI"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            Cerrar();
        }
        return alu;
    }

    @Override
    public List<AlumnoM> listarAlumno() throws Exception {
        List<AlumnoM> listaAlumno;
        ResultSet rs;
        try {
            Conectar();
//            String sql="SELECT CODALU,NOMALU,APEALU,DNIALU,DIRALU,FORMAT(CONVERT(DATE,FECNACALU,105),'dd/MM/yyyy','en-gb') AS FECNACALU,CODUBI FROM ALUMNO";
            String sql = "SELECT * FROM VW_LISTAR_ALUMNO";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            rs = ps.executeQuery();
            listaAlumno = new ArrayList<>();
            while (rs.next()) {
                AlumnoM alu = new AlumnoM();
                alu.setCODALU(rs.getString("CODALU"));
                alu.setNOMALU(rs.getString("NOMALU"));
                alu.setAPEALU(rs.getString("APEALU"));
                alu.setDNIALU(rs.getString("DNIALU"));
                alu.setDIRALU(rs.getString("DIRALU"));
                alu.setFECNACALU(rs.getString("FECNACALU"));
                alu.setNOMUBI(rs.getString("NOMUBI"));
                listaAlumno.add(alu);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            Cerrar();
        }
        return listaAlumno;
    }

    //METODO REPORTE_PDF_ALUMNO PARA DESCARGAR EL REPORTE
    public void REPORTE_PDF_ALUMNO(Map parameters) throws JRException, IOException, Exception {
        Conectar();
        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("Vistas\\Reportes\\REPORTES.jasper"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parameters, this.getCn());
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=REPORTE DE ALUMNOS.pdf");
        try (ServletOutputStream stream = response.getOutputStream()) {
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
            stream.flush();
        }
        FacesContext.getCurrentInstance().responseComplete();
    }

}
