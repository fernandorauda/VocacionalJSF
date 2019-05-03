/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import Rule.*;
import Utilidades.Variables;
import dao.ResultadoDao;
import entidad.Resultados;

/**
 *
 * @author Yonatan-Segura
 */
@Named(value = "cienciaBean")
@SessionScoped
public class cienciasBean implements Serializable {

    private static ResultadoDao dao;
    private Resultados r = new Resultados();
    BooleanRuleBase rules = new BooleanRuleBase("rules");

    //Variables de entrada
    RuleVariable a2p1, a2p2, a2p3, a2p4;

    //Variables de salida
    RuleVariable a2r1, a2r2, a2r3, a2r4, resultCareer;
    String resultado = "";
public String obtenerHard() {
        BaseConocimiento();
        a2p1.setValue(r.getResult2());
        a2p2.setValue(r.getResult1());
        a2p3.setValue(r.getResult4());
        a2p4.setValue(r.getResult3());
        rules.forwardChain();
        if(resultCareer.getValue()==null){
            r.setCareer("No disponemos de una carrera para tu selección");
        }else{
            r.setCareer(resultCareer.getValue());
        }
        r.setArea("Ciencias Sociales");

        //Mostrar en un modal
        
        guardar();
        return "/resultadoCiencia.xhtml?faces-redirect=true";
    }

    //Guardar Resultado
    public void guardar() {
       
        dao = new ResultadoDao();
        dao.guardar(r);
       
    }
    public void BaseConocimiento() {
        //inicializar variables
        a2p1 = new RuleVariable(rules, "");
        a2p2 = new RuleVariable(rules, "");
        a2p3 = new RuleVariable(rules, "");
        a2p4 = new RuleVariable(rules, "");

        a2r1 = new RuleVariable(rules, "");
        a2r2 = new RuleVariable(rules, "");
        a2r3 = new RuleVariable(rules, "");
        a2r4 = new RuleVariable(rules, "");
        resultCareer = new RuleVariable(rules, "");
        Condition igual = new Condition("=");

        Rule rule1 = new Rule(
                rules,
                "rule1",
                new Clause[]{
                    new Clause(a2p1, igual, Variables.SI),},
                new Clause(a2r1, igual, "a2p1Si")
        );
        Rule rule2 = new Rule(
                rules,
                "rule2",
                new Clause[]{
                    new Clause(a2p1, igual, Variables.NO),},
                new Clause(a2r1, igual, "a2p1No")
        );
        Rule rule3 = new Rule(
                rules,
                "rule3",
                new Clause[]{
                    new Clause(a2p2, igual, Variables.SI),},
                new Clause(a2r2, igual, "a2p2Si")
        );
        Rule rule4 = new Rule(
                rules,
                "rule4",
                new Clause[]{
                    new Clause(a2p2, igual, Variables.NO),},
                new Clause(a2r2, igual, "a2p2No")
        );
        Rule rule5 = new Rule(
                rules,
                "rule5",
                new Clause[]{
                    new Clause(a2p3, igual, Variables.SI),},
                new Clause(a2r3, igual, "a2p3Si")
        );
        Rule rule6 = new Rule(
                rules,
                "rule6",
                new Clause[]{
                    new Clause(a2p3, igual, Variables.NO),},
                new Clause(a2r3, igual, "a2p3No")
        );
        Rule rule7 = new Rule(
                rules,
                "rule7",
                new Clause[]{
                    new Clause(a2p4, igual, Variables.SI),},
                new Clause(a2r4, igual, "a2p4Si")
        );
        Rule rule8 = new Rule(
                rules,
                "rule8",
                new Clause[]{
                    new Clause(a2p4, igual, Variables.NO),},
                new Clause(a2r4, igual, "a2p4No")
        );

        Rule rule9 = new Rule(
                rules,
                "rule9",
                new Clause[]{
                    new Clause(a2r1, igual, "a2p1No"),
                    new Clause(a2r2, igual, "a2p2Si"),
                    new Clause(a2r3, igual, "a2p3No"),
                    new Clause(a2r4, igual, "a2p4No"),},
                new Clause(resultCareer, igual, "Técnico en Mercadotecnia")
        );
         Rule rule10 = new Rule(
                rules,
                "rule10",
                new Clause[]{
                    new Clause(a2r1, igual, "a2p1Si"),
                    new Clause(a2r2, igual, "a2p2Si"),
                    new Clause(a2r3, igual, "a2p3Si"),
                    new Clause(a2r4, igual, "a2p4No"),},
                new Clause(resultCareer, igual, "Lic en Ciencias Económicas")
        );
          Rule rule11 = new Rule(
                rules,
                "rule11",
                new Clause[]{
                    new Clause(a2r1, igual, "a2p1Si"),
                    new Clause(a2r2, igual, "a2p2Si"),
                    new Clause(a2r3, igual, "a2p3No"),
                    new Clause(a2r4, igual, "a2p4No"),},
                new Clause(resultCareer, igual, "Lic en Contaduría Pública")
        );
          
          Rule rule12 = new Rule(
                rules,
                "rule12",
                new Clause[]{
                    new Clause(a2r1, igual, "a2p1Si"),
                    new Clause(a2r2, igual, "a2p2No"),
                    new Clause(a2r3, igual, "a2p3No"),
                    new Clause(a2r4, igual, "a2p4No"),},
                new Clause(resultCareer, igual, "Lic en Contaduría Internacional")
        );
           Rule rule13 = new Rule(
                rules,
                "rule13",
                new Clause[]{
                    new Clause(a2r1, igual, "a2p1Si"),
                    new Clause(a2r2, igual, "a2p2No"),
                    new Clause(a2r3, igual, "a2p3No"),
                    new Clause(a2r4, igual, "a2p4Si"),},
                new Clause(resultCareer, igual, "Lic en Administración financiera")
        );

       
    }
       //Getter and Setter
    public Resultados getR() {
        return r;
    }

    public void setR(Resultados r) {
        this.r = r;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
