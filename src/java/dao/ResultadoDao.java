/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Utilidades.NewHibernateUtil;
import entidad.Resultados;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author macbookpro
 */
public class ResultadoDao {
    private static Transaction tx;
    private static Session session = NewHibernateUtil.getSessionFactory().openSession();
    
    public void guardar(Resultados r) {
        tx = session.beginTransaction();
        session.save(r);
        tx.commit();
    }
}
