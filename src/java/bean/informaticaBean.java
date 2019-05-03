/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import Rule.*;
import Utilidades.Variables;
import dao.ResultadoDao;
import entidad.Resultados;
//
import java.io.File;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author macbookpro
 */
@Named(value = "inforB")
@SessionScoped
public class informaticaBean implements Serializable {

    //Redes 1, hardware 2, Programacion 3 y Numeros 4
    private static ResultadoDao dao;

    private Resultados r = new Resultados();

    BooleanRuleBase rules = new BooleanRuleBase("rules");

    //Varibles de Entrada
    RuleVariable 
            hardware,
            redes,
            numeros,
            programacion;

    //Variables de Salida
    RuleVariable 
            resultHard, 
            resultRed, 
            resultNum, 
            resultProg, 
            resultCareer;

    String resultado = "";

    //Obtener Carreras
    public String obtenerHard() {
        BaseConocimiento();
        hardware.setValue(r.getResult2());
        redes.setValue(r.getResult1());
        numeros.setValue(r.getResult4());
        programacion.setValue(r.getResult3());
        rules.forwardChain();
        if(resultCareer.getValue()==null){
            r.setCareer("No disponemos de una carrera para tu seleccion");
        }else{
            r.setCareer(resultCareer.getValue());
        }
        r.setArea("Tecnologia");

        //Mostrar en un modal
        
        guardar();
        return "/resultado.xhtml?faces-redirect=true";
    }

    //Guardar Resultado
    public void guardar() {
       
        dao = new ResultadoDao();
        dao.guardar(r);
    }
    
    public String settear(){
        r = new Resultados();
        return "/index.xhtml?faces-redirect=true";
    }

    //Base del Conocimiento
    public void BaseConocimiento() {

        //Inicializar las variables
        hardware = new RuleVariable(rules, "");
        redes = new RuleVariable(rules, "");
        numeros = new RuleVariable(rules, "");
        programacion = new RuleVariable(rules, "");

        resultHard = new RuleVariable(rules, "");
        resultRed = new RuleVariable(rules, "");
        resultNum = new RuleVariable(rules, "");
        resultProg = new RuleVariable(rules, "");
        resultCareer = new RuleVariable(rules, "");

        Condition igual = new Condition("=");

        Rule ruleHardSi = new Rule(
                rules,
                "ruleHardSi",
                new Clause[]{
                    new Clause(hardware, igual, Variables.SI),},
                new Clause(resultHard, igual, "hardSi")
        );
        Rule ruleHardNo = new Rule(
                rules,
                "ruleHardNo",
                new Clause[]{
                    new Clause(hardware, igual, Variables.NO),},
                new Clause(resultHard, igual, "hardNo")
        );
        Rule ruleRedSi = new Rule(
                rules,
                "ruleRedSi",
                new Clause[]{
                    new Clause(redes, igual, Variables.SI),},
                new Clause(resultRed, igual, "redSi")
        );
        Rule ruleRedNo = new Rule(
                rules,
                "ruleRedNo",
                new Clause[]{
                    new Clause(redes, igual, Variables.NO),},
                new Clause(resultRed, igual, "redNo")
        );
        Rule ruleNumSi = new Rule(
                rules,
                "ruleNumSi",
                new Clause[]{
                    new Clause(numeros, igual, Variables.SI),},
                new Clause(resultNum, igual, "numSi")
        );
        Rule ruleNumNo = new Rule(
                rules,
                "ruleNumNo",
                new Clause[]{
                    new Clause(numeros, igual, Variables.NO),},
                new Clause(resultNum, igual, "numNo")
        );
        Rule ruleProgSi = new Rule(
                rules,
                "ruleProgSi",
                new Clause[]{
                    new Clause(programacion, igual, Variables.SI),},
                new Clause(resultProg, igual, "progSi")
        );
        Rule ruleProgNo = new Rule(
                rules,
                "ruleProgNo",
                new Clause[]{
                    new Clause(programacion, igual, Variables.NO),},
                new Clause(resultProg, igual, "progNo")
        );
        Rule ruleIngSoft = new Rule(
                rules,
                "ruleIngSoft",
                new Clause[]{
                    new Clause(resultRed, igual, "redSi"),
                    new Clause(resultHard, igual, "hardSi"),
                    new Clause(resultProg, igual, "progSi"),
                    new Clause(resultNum, igual, "numSi"),},
                new Clause(resultCareer, igual, "Ing. en Informatica")
        );
        Rule ruleNo = new Rule(
                rules,
                "ruleIngSoft",
                new Clause[]{
                    new Clause(resultRed, igual, "redNo"),
                    new Clause(resultHard, igual, "hardNo"),
                    new Clause(resultProg, igual, "progNo"),
                    new Clause(resultNum, igual, "numNo"),},
                new Clause(resultCareer, igual, "Busca en otra area")
        );
        Rule rule1 = new Rule(
                rules,
                "rule1",
                new Clause[]{
                    new Clause(resultRed, igual, "redNo"),
                    new Clause(resultHard, igual, "hardNo"),
                    new Clause(resultProg, igual, "progNo"),
                    new Clause(resultNum, igual, "numSi"),},
                new Clause(resultCareer, igual, "Lic. en Matematica")
        );
        Rule rule2 = new Rule(
                rules,
                "rule2",
                new Clause[]{
                    new Clause(resultRed, igual, "redNo"),
                    new Clause(resultHard, igual, "hardSi"),
                    new Clause(resultProg, igual, "progSi"),
                    new Clause(resultNum, igual, "numSi"),},
                new Clause(resultCareer, igual, "Ing. en Sistemas")
        );
        Rule rule3 = new Rule(
                rules,
                "rule2",
                new Clause[]{
                    new Clause(resultRed, igual, "redNo"),
                    new Clause(resultHard, igual, "hardNo"),
                    new Clause(resultProg, igual, "progSi"),
                    new Clause(resultNum, igual, "numSi"),},
                new Clause(resultCareer, igual, "Ing. en Software")
        );
        Rule rule4 = new Rule(
                rules,
                "rule4",
                new Clause[]{
                    new Clause(resultRed, igual, "redNo"),
                    new Clause(resultHard, igual, "hardNo"),
                    new Clause(resultProg, igual, "progSi"),
                    new Clause(resultNum, igual, "numNo"),},
                new Clause(resultCareer, igual, "Tec. en Ing. de Software")
        );
        Rule rule5 = new Rule(
                rules,
                "rule5",
                new Clause[]{
                    new Clause(resultRed, igual, "redNo"),
                    new Clause(resultHard, igual, "hardSi"),
                    new Clause(resultProg, igual, "progNo"),
                    new Clause(resultNum, igual, "numNo"),},
                new Clause(resultCareer, igual, "Tec. en Mantenimiento de Comp.")
        );
        Rule rule6 = new Rule(
                rules,
                "rule6",
                new Clause[]{
                    new Clause(resultRed, igual, "redSi"),
                    new Clause(resultHard, igual, "hardSi"),
                    new Clause(resultProg, igual, "progSi"),
                    new Clause(resultNum, igual, "numNo"),},
                new Clause(resultCareer, igual, "Lic. en Computacion")
        );
        Rule rule7 = new Rule(
                rules,
                "rule7",
                new Clause[]{
                    new Clause(resultRed, igual, "redSi"),
                    new Clause(resultHard, igual, "hardSi"),
                    new Clause(resultProg, igual, "progNo"),
                    new Clause(resultNum, igual, "numNo"),},
                new Clause(resultCareer, igual, "Tec. en redes Computacionales")
        );
        Rule rule8 = new Rule(
                rules,
                "rule8",
                new Clause[]{
                    new Clause(resultRed, igual, "redSi"),
                    new Clause(resultHard, igual, "hardNo"),
                    new Clause(resultProg, igual, "progNo"),
                    new Clause(resultNum, igual, "numNo"),},
                new Clause(resultCareer, igual, "Tec. en Mantenimiento de Redes")
        );
        Rule rule9 = new Rule(
                rules,
                "rule9",
                new Clause[]{
                    new Clause(resultRed, igual, "redSi"),
                    new Clause(resultHard, igual, "hardNo"),
                    new Clause(resultProg, igual, "progNo"),
                    new Clause(resultNum, igual, "numSi"),},
                new Clause(resultCareer, igual, "Ing. en Software")
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
    
        //Report
    public void reporte() throws JRException, IOException{
            
        Map parametros = new HashMap();
        parametros.put("name", r.getNombre());
        parametros.put("result1", r.getResult1());
        parametros.put("result2", r.getResult2());
        parametros.put("result3", r.getResult3());
        parametros.put("result4", r.getResult4());
        parametros.put("career", r.getCareer());
        
        String resultado = "resultados.pdf";
        String reporte = "/recursos/reports/Tecnologia.jasper";
        
        GenerarReporte(parametros, reporte, null, resultado);
        
    }
    
    public void GenerarReporte(Map<String, Object> params, String jasperPath, List<?> dataSource, String fileName) throws JRException, IOException {
        String relativeWebPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(jasperPath);
        File file = new File(relativeWebPath);
        JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(dataSource);
        JasperPrint print = JasperFillManager.fillReport(file.getPath(), params, source);
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.setContentType("application/pdf");

        response.setHeader("Content-Disposition", "inline");
        ServletOutputStream stream = response.getOutputStream();

        JasperExportManager.exportReportToPdfStream(print, stream);

        FacesContext.getCurrentInstance().responseComplete();
    }

}
