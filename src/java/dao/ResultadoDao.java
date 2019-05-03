/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Utilidades.NewHibernateUtil;
import entidad.Resultados;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author macbookpro
 */
public class ResultadoDao {
    private static Transaction tx;
    private static Session session = NewHibernateUtil.getSessionFactory().openSession();
    
    public List<Resultados> listado(){
        List<Resultados> lista = new ArrayList();
        Query sql = session.createQuery("from Resultados");
        lista = (List<Resultados>) sql.list();
        return lista;
    }
    
    public void guardar(Resultados r) {
        tx = session.beginTransaction();
        session.save(r);
        tx.commit();
    }
}
