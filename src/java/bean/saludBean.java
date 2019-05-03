/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import Rule.BooleanRuleBase;
import Rule.*;
import Utilidades.Variables;
import dao.ResultadoDao;
import entidad.Resultados;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Named;

import javax.enterprise.context.SessionScoped;
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
 * @author Yonatan-Segura
 */
@Named(value = "saludBean")
@SessionScoped
public class saludBean implements Serializable {

    private static ResultadoDao dao;
    private Resultados r = new Resultados();
    BooleanRuleBase rules = new BooleanRuleBase("rules");

    //Variables de entrada
    RuleVariable a3p1, a3p2, a3p3, a3p4;

    //Variables de salida
    RuleVariable a3r1, a3r2, a3r3, a3r4, resultCareer;
    String resultado = "";
 public String obtenerHard() {
        BaseConocimiento();
        a3p1.setValue(r.getResult2());
        a3p2.setValue(r.getResult1());
        a3p3.setValue(r.getResult4());
        a3p4.setValue(r.getResult3());
        rules.forwardChain();
        if(resultCareer.getValue()==null){
            r.setCareer("No disponemos de una carrera para tu selección");
        }else{
            r.setCareer(resultCareer.getValue());
        }
        r.setArea("Salud");

        //Mostrar en un modal
        
        guardar();
        return "/resultadoSalud.xhtml?faces-redirect=true";
    }

    //Guardar Resultado
    public void guardar() {
       
        dao = new ResultadoDao();
        dao.guardar(r);
       
    }
    
    public void BaseConocimiento() {
        //inicializar variables
        a3p1 = new RuleVariable(rules, "");
        a3p2 = new RuleVariable(rules, "");
        a3p3 = new RuleVariable(rules, "");
        a3p4 = new RuleVariable(rules, "");

        a3r1 = new RuleVariable(rules, "");
        a3r2 = new RuleVariable(rules, "");
        a3r3 = new RuleVariable(rules, "");
        a3r4 = new RuleVariable(rules, "");
        resultCareer = new RuleVariable(rules, "");
        Condition igual = new Condition("=");

        Rule rule1 = new Rule(
                rules,
                "rule1",
                new Clause[]{
                    new Clause(a3p1, igual, Variables.SI),},
                new Clause(a3r1, igual, "a3p1Si")
        );
        Rule rule2 = new Rule(
                rules,
                "rule2",
                new Clause[]{
                    new Clause(a3p1, igual, Variables.NO),},
                new Clause(a3r1, igual, "a3p1No")
        );
        Rule rule3 = new Rule(
                rules,
                "rule3",
                new Clause[]{
                    new Clause(a3p2, igual, Variables.SI),},
                new Clause(a3r2, igual, "a3p2Si")
        );
        Rule rule4 = new Rule(
                rules,
                "rule4",
                new Clause[]{
                    new Clause(a3p2, igual, Variables.NO),},
                new Clause(a3r2, igual, "a3p2No")
        );
        Rule rule5 = new Rule(
                rules,
                "rule5",
                new Clause[]{
                    new Clause(a3p3, igual, Variables.SI),},
                new Clause(a3r3, igual, "a3p3Si")
        );
        Rule rule6 = new Rule(
                rules,
                "rule6",
                new Clause[]{
                    new Clause(a3p3, igual, Variables.NO),},
                new Clause(a3r3, igual, "a3p3No")
        );
        Rule rule7 = new Rule(
                rules,
                "rule7",
                new Clause[]{
                    new Clause(a3p4, igual, Variables.SI),},
                new Clause(a3r4, igual, "a3p4Si")
        );
        Rule rule8 = new Rule(
                rules,
                "rule8",
                new Clause[]{
                    new Clause(a3p4, igual, Variables.NO),},
                new Clause(a3r4, igual, "a3p4No")
        );

        Rule ruleEnfermeria = new Rule(
                rules,
                "ruleEnfermeria",
                new Clause[]{
                    new Clause(a3r1, igual, "a3p1No"),
                    new Clause(a3r2, igual, "a3p2Si"),
                    new Clause(a3r3, igual, "a3p3Si"),
                    new Clause(a3r4, igual, "a3p4Si"),},
                new Clause(resultCareer, igual, "Lic. en enfermería")
        );
        
        Rule ruleTodoNo = new Rule(
                rules,
                "ruleTodoNo",
                new Clause[]{
                    new Clause(a3r1, igual, "a3p1No"),
                    new Clause(a3r2, igual, "a3p2No"),
                    new Clause(a3r3, igual, "a3p3No"),
                    new Clause(a3r4, igual, "a3p4No"),},
                new Clause(resultCareer, igual, "Busca en otra área")
        );
       Rule ruleLicFisio = new Rule(
                rules,
                "ruleLicFisio",
                new Clause[]{
                    new Clause(a3r1, igual, "a3p1No"),
                    new Clause(a3r2, igual, "a3p2No"),
                    new Clause(a3r3, igual, "a3p3No"),
                    new Clause(a3r4, igual, "a3p4Si"),},
                new Clause(resultCareer, igual, "Licenciatura en Fisioterapia y Terapia Ocupacional")
        );
       
        Rule ruleLicLab = new Rule(
                rules,
                "Lab",
                new Clause[]{
                    new Clause(a3r1, igual, "a3p1No"),
                    new Clause(a3r2, igual, "a3p2Si"),
                    new Clause(a3r3, igual, "a3p3Si"),
                    new Clause(a3r4, igual, "a3p4Si"),},
                new Clause(resultCareer, igual, "Licenciatura en Laboratorio Clínico")
        );
        
         Rule rule9 = new Rule(
                rules,
                "rule9",
                new Clause[]{
                    new Clause(a3r1, igual, "a3p1No"),
                    new Clause(a3r2, igual, "a3p2No"),
                    new Clause(a3r3, igual, "a3p3Si"),
                    new Clause(a3r4, igual, "a3p4Si"),},
                new Clause(resultCareer, igual, "Licenciatura en Farmacología")
        );
         
           Rule rule10 = new Rule(
                rules,
                "rule10",
                new Clause[]{
                    new Clause(a3r1, igual, "a3p1No"),
                    new Clause(a3r2, igual, "a3p2No"),
                    new Clause(a3r3, igual, "a3p3Si"),
                    new Clause(a3r4, igual, "a3p4No"),},
                new Clause(resultCareer, igual, "Licenciatura en Química")
        );
           
         Rule rule11 = new Rule(
                rules,
                "rule11",
                new Clause[]{
                    new Clause(a3r1, igual, "a3p1No"),
                    new Clause(a3r2, igual, "a3p2Si"),
                    new Clause(a3r3, igual, "a3p3No"),
                    new Clause(a3r4, igual, "a3p4No"),},
                new Clause(resultCareer, igual, "Técnico en Enfermería")
        );
         Rule rule12 = new Rule(
                rules,
                "rule12",
                new Clause[]{
                    new Clause(a3r1, igual, "a3p1Si"),
                    new Clause(a3r2, igual, "a3p2Si"),
                    new Clause(a3r3, igual, "a3p3Si"),
                    new Clause(a3r4, igual, "a3p4Si"),},
                new Clause(resultCareer, igual, "Doctor en medicina general")
        );
         Rule rule13 = new Rule(
                rules,
                "rule13",
                new Clause[]{
                    new Clause(a3r1, igual, "a3p1Si"),
                    new Clause(a3r2, igual, "a3p2Si"),
                    new Clause(a3r3, igual, "a3p3Si"),
                    new Clause(a3r4, igual, "a3p4No"),},
                new Clause(resultCareer, igual, "Ingenería en Biología")
        );
         Rule rule14 = new Rule(
                rules,
                "rule14",
                new Clause[]{
                    new Clause(a3r1, igual, "a3p1Si"),
                    new Clause(a3r2, igual, "a3p2Si"),
                    new Clause(a3r3, igual, "a3p3No"),
                    new Clause(a3r4, igual, "a3p4No"),},
                new Clause(resultCareer, igual, "Técnico en Farmacología")
        );
          Rule rule15 = new Rule(
                rules,
                "rule15",
                new Clause[]{
                    new Clause(a3r1, igual, "a3p1Si"),
                    new Clause(a3r2, igual, "a3p2No"),
                    new Clause(a3r3, igual, "a3p3No"),
                    new Clause(a3r4, igual, "a3p4No"),},
                new Clause(resultCareer, igual, "Licenciatura en nutrición")
        );
           Rule rule16 = new Rule(
                rules,
                "rule16",
                new Clause[]{
                    new Clause(a3r1, igual, "a3p1Si"),
                    new Clause(a3r2, igual, "a3p2No"),
                    new Clause(a3r3, igual, "a3p3No"),
                    new Clause(a3r4, igual, "a3p4Si"),},
                new Clause(resultCareer, igual, "Neurólogo")
        );
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
        String reporte = "/recursos/reports/Salud.jasper";
        
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
    
    public String settear(){
        r = new Resultados();
        return "/index.xhtml?faces-redirect=true";
    }
}
