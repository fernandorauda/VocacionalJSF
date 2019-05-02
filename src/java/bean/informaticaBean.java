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
    String resp1 = "";
    String resp2 = "";
    String resp3 = "";
    String resp4 = "";

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
   
    
    
    public void analisis(){
        String hard = "", red = "", num ="", prog="", career="";
        if(resp1.equals("si")){
            hard = ObtenerHard("si", "no");
        }else if(resp1.equals("no")){
            hard = ObtenerHard("no", "si");
        }
        
        if(resp2.equals("si")){
            red = ObtenerRed("si", "no");
        }else if(resp2.equals("no")){
            red = ObtenerRed("no", "si");
        }
        
        if(resp3.equals("si")){
            num = ObtenerNum("si", "no");
        }else if(resp3.equals("no")){
            num = ObtenerNum("no", "si");
        }
        
        if(resp4.equals("si")){
            prog = ObtenerProg("si", "no");
        }else if(resp4.equals("no")){
            prog = ObtenerProg("no", "si");
        }
        
        career = Career(hard, red, num, prog);
        System.out.println(career);
    }
    
    BooleanRuleBase rules = new BooleanRuleBase("rules");
    
    //Varibles de Entrada
    RuleVariable 
            hardwareSi, 
            hardwareNo, 
            redesSi, 
            redesNo, 
            numerosSi, 
            numerosNo, 
            programacionSi, 
            programacionNo;
    
    //Variables de Salida
    RuleVariable resultHard, resultRed, resultNum, resultProg, resultCareer;
    
    String Resultado = "";
    
    //----------------
    public String ObtenerHard(String SI, String NO) {
        BaseConocimiento();
        hardwareSi.setValue(SI);
        hardwareNo.setValue(NO);
        rules.forwardChain();
        Resultado = resultHard.getValue();

        return Resultado;
    }
    
    public String ObtenerRed(String SI, String NO) {
        BaseConocimiento();
        redesSi.setValue(SI);
        redesNo.setValue(NO);
        rules.forwardChain();
        Resultado = resultRed.getValue();

        return Resultado;
    }
    
    public String ObtenerNum(String SI, String NO) {
        BaseConocimiento();
        numerosSi.setValue(SI);
        numerosNo.setValue(NO);
        rules.forwardChain();
        Resultado = resultNum.getValue();

        return Resultado;
    }
    
    public String ObtenerProg(String SI, String NO) {
        BaseConocimiento();
        programacionSi.setValue(SI);
        programacionNo.setValue(NO);
        rules.forwardChain();
        Resultado = resultProg.getValue();

        return Resultado;
    }
    
    public String Career(String hard, String red, String num, String prog) {
        BaseConocimiento();
        resultHard.setValue(hard);
        resultRed.setValue(red);
        resultNum.setValue(num);
        resultProg.setValue(prog);
        rules.forwardChain();
        Resultado = resultCareer.getValue();

        return Resultado;
    }
    
    
 
    public void BaseConocimiento(){
        
        //Inicializar las variables
        hardwareSi = new RuleVariable(rules, "");
        hardwareNo = new RuleVariable(rules, "");
        redesSi = new RuleVariable(rules, "");
        redesNo = new RuleVariable(rules, "");
        numerosSi = new RuleVariable(rules, "");
        numerosNo = new RuleVariable(rules, "");
        programacionSi = new RuleVariable(rules, "");
        programacionNo = new RuleVariable(rules, "");
        
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
                    new Clause(hardwareSi, igual, Variables.SI),
                    new Clause(hardwareNo, igual, Variables.NO)
                },
                new Clause(resultHard, igual, "hardSi")
        );
        Rule ruleHardNo = new Rule(
                rules,
                "ruleHardNo",
                new Clause[]{
                    new Clause(hardwareSi, igual, Variables.NO),
                    new Clause(hardwareNo, igual, Variables.SI)
                },
                new Clause(resultHard, igual, "hardNo")
        );
        Rule ruleRedSi = new Rule(
                rules,
                "ruleRedSi",
                new Clause[]{
                    new Clause(redesSi, igual, Variables.SI),
                    new Clause(redesNo, igual, Variables.NO)
                },
                new Clause(resultRed, igual, "redSi")
        );
        Rule ruleRedNo = new Rule(
                rules,
                "ruleRedNo",
                new Clause[]{
                    new Clause(redesSi, igual, Variables.NO),
                    new Clause(redesNo, igual, Variables.SI)
                },
                new Clause(resultRed, igual, "redNo")
        );
        Rule ruleNumSi = new Rule(
                rules,
                "ruleNumSi",
                new Clause[]{
                    new Clause(numerosSi, igual, Variables.SI),
                    new Clause(numerosNo, igual, Variables.NO)
                },
                new Clause(resultNum, igual, "numSi")
        );
        Rule ruleNumNo = new Rule(
                rules,
                "ruleNumNo",
                new Clause[]{
                    new Clause(numerosSi, igual, Variables.NO),
                    new Clause(numerosNo, igual, Variables.SI)
                },
                new Clause(resultNum, igual, "numNo")
        );
        Rule ruleProgSi = new Rule(
                rules,
                "ruleProgSi",
                new Clause[]{
                    new Clause(programacionSi, igual, Variables.SI),
                    new Clause(programacionNo, igual, Variables.NO)
                },
                new Clause(resultProg, igual, "progSi")
        );
        Rule ruleProgNo = new Rule(
                rules,
                "ruleProgNo",
                new Clause[]{
                    new Clause(programacionSi, igual, Variables.NO),
                    new Clause(programacionNo, igual, Variables.SI)
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
        
    }
    
}
