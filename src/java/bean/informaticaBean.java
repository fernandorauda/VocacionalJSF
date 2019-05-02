/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;
import Rule.*;
import Utilidades.Variables;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author macbookpro
 */

@Named(value = "inforB")
@SessionScoped
public class informaticaBean implements Serializable {
    String resp1 = "no";
    String resp2 = "no";
    String resp3 = "no";
    String resp4 = "no";

    public String getResp1() {
        return resp1;
    }

    public void setResp1(String resp1) {
        this.resp1 = resp1;
    }

    public String getResp2() {
        return resp2;
    }

    public void setResp2(String resp2) {
        this.resp2 = resp2;
    }

    public String getResp3() {
        return resp3;
    }

    public void setResp3(String resp3) {
        this.resp3 = resp3;
    }

    public String getResp4() {
        return resp4;
    }

    public void setResp4(String resp4) {
        this.resp4 = resp4;
    }
   
    
    BooleanRuleBase rules = new BooleanRuleBase("rules");
    
    //Varibles de Entrada
    RuleVariable 
            hardware,
            redes,
            numeros, 
            programacion;
    
    //Variables de Salida
    RuleVariable resultHard, resultRed, resultNum, resultProg, resultCareer;
    
    String Resultado = "";
    
    //----------------
    public void obtenerHard() {
        BaseConocimiento();
        hardware.setValue(resp1);
        redes.setValue(resp2);
        numeros.setValue(resp3);
        programacion.setValue(resp4);
        rules.forwardChain();
        Resultado = resultCareer.getValue();

        
        System.out.println(Resultado);
    }
    
    
 
    public void BaseConocimiento(){
        
        //Inicializar las variables
        hardware = new RuleVariable(rules, "");
        redes = new RuleVariable(rules, "");
        numeros = new RuleVariable(rules, "");
        programacion = new RuleVariable(rules, "");

        
        resultHard = new RuleVariable(rules, "");
        resultRed = new RuleVariable(rules, "");
        resultNum = new RuleVariable(rules, "");
        resultProg = new RuleVariable(rules, "");
        resultCareer = new RuleVariable(rules,"");
        
        Condition igual = new Condition("=");
        
        Rule ruleHardSi = new Rule(
                rules,
                "ruleHardSi",
                new Clause[]{
                    new Clause(hardware, igual, Variables.SI),
                },
                new Clause(resultHard, igual, "hardSi")
        );
        Rule ruleHardNo = new Rule(
                rules,
                "ruleHardNo",
                new Clause[]{
                    new Clause(hardware, igual, Variables.NO),
                },
                new Clause(resultHard, igual, "hardNo")
        );
        Rule ruleRedSi = new Rule(
                rules,
                "ruleRedSi",
                new Clause[]{
                    new Clause(redes, igual, Variables.SI),
                },
                new Clause(resultRed, igual, "redSi")
        );
        Rule ruleRedNo = new Rule(
                rules,
                "ruleRedNo",
                new Clause[]{
                    new Clause(redes, igual, Variables.NO),
                },
                new Clause(resultRed, igual, "redNo")
        );
        Rule ruleNumSi = new Rule(
                rules,
                "ruleNumSi",
                new Clause[]{
                    new Clause(numeros, igual, Variables.SI),
                },
                new Clause(resultNum, igual, "numSi")
        );
        Rule ruleNumNo = new Rule(
                rules,
                "ruleNumNo",
                new Clause[]{
                    new Clause(numeros, igual, Variables.NO),
                },
                new Clause(resultNum, igual, "numNo")
        );
        Rule ruleProgSi = new Rule(
                rules,
                "ruleProgSi",
                new Clause[]{
                    new Clause(programacion, igual, Variables.SI),
                },
                new Clause(resultProg, igual, "progSi")
        );
        Rule ruleProgNo = new Rule(
                rules,
                "ruleProgNo",
                new Clause[]{
                    new Clause(programacion, igual, Variables.NO),
                },
                new Clause(resultProg, igual, "progNo")
        );
        Rule ruleIngSoft = new Rule(
                rules,
                "ruleIngSoft",
                new Clause[]{
                    new Clause(resultRed, igual, "redSi"),
                    new Clause(resultHard, igual, "hardSi"),
                    new Clause(resultProg, igual, "progSi"),
                    new Clause(resultNum, igual, "numSi"),
                },
                new Clause(resultCareer, igual, "Ing. en Informatica")
        );
        Rule ruleNo = new Rule(
                rules,
                "ruleIngSoft",
                new Clause[]{
                    new Clause(resultRed, igual, "redNo"),
                    new Clause(resultHard, igual, "hardNo"),
                    new Clause(resultProg, igual, "progNo"),
                    new Clause(resultNum, igual, "numNo"),
                },
                new Clause(resultCareer, igual, "Busca en otra area")
        );
        Rule rule1 = new Rule(
                rules,
                "rule1",
                new Clause[]{
                    new Clause(resultRed, igual, "redNo"),
                    new Clause(resultHard, igual, "hardNo"),
                    new Clause(resultProg, igual, "progNo"),
                    new Clause(resultNum, igual, "numSi"),
                },
                new Clause(resultCareer, igual, "Lic. en Matematica")
        );
        Rule rule2 = new Rule(
                rules,
                "rule2",
                new Clause[]{
                    new Clause(resultRed, igual, "redNo"),
                    new Clause(resultHard, igual, "hardSi"),
                    new Clause(resultProg, igual, "progSi"),
                    new Clause(resultNum, igual, "numSi"),
                },
                new Clause(resultCareer, igual, "Ing. en Sistemas")
        );
        Rule rule3 = new Rule(
                rules,
                "rule2",
                new Clause[]{
                    new Clause(resultRed, igual, "redNo"),
                    new Clause(resultHard, igual, "hardNo"),
                    new Clause(resultProg, igual, "progSi"),
                    new Clause(resultNum, igual, "numSi"),
                },
                new Clause(resultCareer, igual, "Ing. en Software")
        );
        
    }
    
}
