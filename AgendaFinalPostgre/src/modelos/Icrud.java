/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelos;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author samue_99dfzlt
 */
public interface Icrud {
    void incluir(Contato objeto) throws Exception;
    void excluir (int Id) throws Exception;
    void alterar (Contato objeto) throws Exception;
    Contato consultar (String telefoneOuEmail) throws Exception;
    LinkedList<Contato> listar() throws Exception;
}
