package Dao;

import Interface.UbigeoI;
import Modelo.UbigeoM;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImplUbigeoD extends Dao implements UbigeoI {

    @Override
    public List<UbigeoM> ListarUbigeo() throws Exception {
        List<UbigeoM> listaUbigeo;
        ResultSet rs;
        try {
            Conectar();
            String sql = "SELECT * FROM UBIGEO";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            listaUbigeo = new ArrayList<>();
            rs = ps.executeQuery();
            while (rs.next()) {
                UbigeoM ubi = new UbigeoM();
                ubi.setCODUBI(rs.getString("CODUBI"));
                ubi.setDISUBI(rs.getString("DISUBI"));
                ubi.setPROUBI(rs.getString("PROUBI"));
                ubi.setDEPUBI(rs.getString("DEPUBI"));
                listaUbigeo.add(ubi);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            Cerrar();
        }
        return listaUbigeo;
    }

}
