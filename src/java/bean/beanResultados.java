/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.ResultadoDao;
import entidad.Resultados;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author macbookpro
 */
@Named(value = "resultBean")
@SessionScoped
public class beanResultados implements Serializable {
    
    private static ResultadoDao dao;
    private ResultadoDao result = new ResultadoDao();
    private List<Resultados> resultList = result.listado();
    
    public List<Resultados> listadoResult() {
        dao = new ResultadoDao();

        return dao.listado();
    }
}
