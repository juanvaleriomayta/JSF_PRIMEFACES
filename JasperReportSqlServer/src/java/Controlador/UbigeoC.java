package Controlador;

import Dao.ImplUbigeoD;
import Modelo.UbigeoM;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;

@Named(value = "ubigeoC")
@SessionScoped
public class UbigeoC implements Serializable {

    private UbigeoM ubi = new UbigeoM();
    private List<UbigeoM> listUbi;

    @PostConstruct
    public void inicio() {
        try {
            listarUbigeo();
        } catch (Exception e) {
        }
    }

    public void listarUbigeo() throws Exception {
        ImplUbigeoD Dao;
        try {
            Dao = new ImplUbigeoD();
            listUbi = Dao.ListarUbigeo();
        } catch (Exception e) {
            throw e;
        }
    }

    public UbigeoM getUbi() {
        return ubi;
    }

    public void setUbi(UbigeoM ubi) {
        this.ubi = ubi;
    }

    public List<UbigeoM> getListUbi() {
        return listUbi;
    }

    public void setListUbi(List<UbigeoM> listUbi) {
        this.listUbi = listUbi;
    }

    
}
