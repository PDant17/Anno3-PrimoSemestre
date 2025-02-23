/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 *
 * @author CORSO_PD
 */
@javax.interceptor.Interceptor
public class Interceptor {
    private static HashMap<String, Integer> mappa = new HashMap<>();
    
    @AroundInvoke
    public Object interceptor(InvocationContext ic) throws Exception{
        String nomeMetodo = ic.getMethod().getName();
        Integer nInvocazioni = mappa.get(nomeMetodo);
        if(nInvocazioni == null){
            nInvocazioni = 1;
            mappa.put(nomeMetodo, nInvocazioni);
        } else {
            nInvocazioni++;
            mappa.put(nomeMetodo, nInvocazioni);
        }
        Object[] parameters = ic.getParameters();
        String params = (parameters != null && parameters.length > 0) ? Arrays.toString(parameters) : "Nessun parametro";
        
        System.out.println("Nome del metodo invocato: " + nomeMetodo);
        System.out.println("Numero di invocazioni: " + nInvocazioni);
        System.out.println("Orario di invocazione: " + new Date());
        System.out.println("Parametri passati: " + params);
        return ic.proceed();
    }
}