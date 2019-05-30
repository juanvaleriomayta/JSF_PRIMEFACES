package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dao {

    private Connection cn = null;

    public void Conectar() throws Exception {
        try {
            if (cn == null) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                cn = DriverManager.getConnection("jdbc:sqlserver://35.211.250.160:1433;database=REPORTES", "sa", "sqlserverjvaleriom_2019");
//                cn = DriverManager.getConnection("jdbc:sqlserver://192.168.13.6:1433;database=REPORTESQLSERVER", "root", "root");
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    //Metodo de cerrar la conexión
    public void Cerrar() throws Exception {
        if (cn != null) {
            if (cn.isClosed() == false) {
                cn.close();
                cn = null;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Dao dao = new Dao();
        dao.Conectar();
        if (dao.getCn() != null) {
            System.out.println("Conectado con éxito");
        } else {
            System.err.println("Error en la Conexión");
        }
    }

    public Connection getCn() {
        return cn;
    }

    public void setCn(Connection cn) {
        this.cn = cn;
    }
    
    
}
