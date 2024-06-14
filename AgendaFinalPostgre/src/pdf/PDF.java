/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import modelos.Contato;
import java.sql.*;
import java.util.ArrayList;
import modelos.ConexaoPostgreSQL;
import modelos.Endereco;
import modelos.Telefone;

/**
 *
 * @author samue_99dfzlt
 */
public class PDF {
    public void abrirPdf(String filePath) throws Exception {
        try {
            File file = new File(filePath);
            if (!file.exists()) throw new IOException("Arquivo não encontrado: " + filePath);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            } else {
                throw new UnsupportedOperationException("Não foi possível abrir o PDF nesse sistema.");
            }
        } catch (IOException ex) {
            throw new Exception("Erro ao abrir PDF");
        }
    }

    public void gerarPDF(ArrayList<Contato> contatos, String filePath) throws Exception {
        Document documento = new Document();
        try {
            PdfWriter.getInstance(documento, new FileOutputStream(filePath));
            documento.open();
            for (Contato contato : contatos) {
                documento.add(new Paragraph(contato.toString()));
            }
            documento.close(); 
        } catch (FileNotFoundException ex) {
            throw new Exception("Arquivo não encontrado: " + ex.getMessage());
        } catch (DocumentException ex) {
            throw new Exception("Erro ao gerar PDF: " + ex.getMessage());
        } catch (Exception ex) {
            throw new Exception("Erro inesperado: " + ex.getMessage());
        }
    }

    public ArrayList<Contato> recuperarContatosDoBanco() throws Exception {
        ArrayList<Contato> contatos = new ArrayList<>();
        String sql = "SELECT nome, telefone, email, logradouro, numero, complemento, cep, cidade, estado FROM contatos";
        
        try (Connection conn = ConexaoPostgreSQL.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");
                String logradouro = rs.getString("logradouro");
                int numero = rs.getInt("numero");
                String complemento = rs.getString("complemento");
                String cep = rs.getString("cep");
                String cidade = rs.getString("cidade");
                String estado = rs.getString("estado");
                
                //o telefone é armazenado no formato "+DDI DDD NUMERO"
                 String ddi = telefone.substring(1, 3);
                 String ddd = telefone.substring(5, 7);
                 String numeroTelefone = telefone.substring(7).replaceAll("[^0-9]", "");

                Telefone tel = new Telefone(Integer.parseInt(ddi), Integer.parseInt(ddd), Integer.parseInt(numeroTelefone));
                Endereco endereco = new Endereco(logradouro, numero, complemento, cep, cidade, estado);
                Contato contato = new Contato(nome, tel, email, endereco);
                contatos.add(contato);
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao recuperar dados do banco: " + e.getMessage());
        }
        return contatos;
    }
}
