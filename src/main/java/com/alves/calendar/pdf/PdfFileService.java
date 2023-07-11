package com.alves.calendar.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alves.calendar.entities.Holiday;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

@Service
public class PdfFileService {

	public void pdfCreator(Map<String, Holiday> holidayMap) {
	    String homeDirectory = System.getProperty("user.home");
	    String downloadsDirectory = homeDirectory + File.separator + "Downloads";
	    String filepath = downloadsDirectory + File.separator + "calendar.pdf";
	    
	    try {
	        PdfWriter writer = new PdfWriter(filepath);
	        
	        PdfDocument pdfDoc = new PdfDocument(writer);
	        pdfDoc.addNewPage();
	        
	        Document document = new Document(pdfDoc);

	        document.add(new Paragraph("Calendar")
	        		.setTextAlignment(TextAlignment.CENTER)
	        		.setFontSize(16)
	        		.setBold());
	        
	        for (Holiday holiday : holidayMap.values()) {
	            document.add(new Paragraph(holiday.getDate() + " - " + holiday.getDescription()));
	        }
	        
	        document.close();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
}
