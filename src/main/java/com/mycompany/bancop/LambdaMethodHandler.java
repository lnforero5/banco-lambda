/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bancop;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class LambdaMethodHandler implements RequestHandler<LambdaRequest, LambdaResponse> {
    
    @Override
    public LambdaResponse handleRequest(LambdaRequest request, Context context) {

        ArrayList<Integer> n_grupos = new ArrayList();
        ArrayList<Integer> tam_f = new ArrayList();
        int suma_total = 0;

        LambdaResponse response = new LambdaResponse();

        String[] split = request.getGroups().split(",");
        for (String n_numero : split) {
            n_grupos.add(Integer.parseInt(n_numero));
        }

        suma_total = n_grupos.stream().map((i) -> i).reduce(suma_total, Integer::sum);

        for (int j = 1; j <= suma_total; j++) {
            if (suma_total % j == 0) {
                if (validar_n(n_grupos, j)) {
                    tam_f.add(j);
                }
            }
        }
        
        response.setSizes(String.valueOf(tam_f).replace("[", "").replace("]", "").replace(",", ""));

        return response;

    }

    static boolean validar_n(ArrayList<Integer> grupo_n, int tam) {
        int x = tam;
        for (int n_capacidad : grupo_n) {
            if (x == n_capacidad) {
                x = tam;
            } else if (x < n_capacidad) {
                return false;
            } else {
                x = x - n_capacidad;
            }
        }
        return x == tam;
    }

}
