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
}
