package com.spring.starter.pdfGenerator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.spring.starter.DTO.CustomerFilesDTO;
import com.spring.starter.DTO.DailyPdfReportDTO;
import com.spring.starter.DTO.TifGenarateDTO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TifReports {

    public static ByteArrayInputStream report(TifGenarateDTO tifGenarateDTO) {

        int count=1;
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            //number Of Columns
            PdfPTable table = new PdfPTable(8);
            // width of Table
            table.setWidthPercentage(90);
            // To set Width Of Columns
            table.setWidths(new int[]{2, 2, 2, 2, 2, 2, 2, 2});

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("Customer Transaction RequestId", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("dig Form Type", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Customer Identification", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Staff User EPF Number", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);


            hcell = new PdfPCell(new Phrase("Staff User Name", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Customer Signature URL", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Document Type", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Document URL", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            List<CustomerFilesDTO> filesDTOS = tifGenarateDTO.getList();

            for (CustomerFilesDTO dto: filesDTOS){


                PdfPCell cell;

                if (count==1){

                    cell = new PdfPCell(new Phrase(tifGenarateDTO.getCustomerTransactionRequestId()));
                    cell.setPaddingLeft(2);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(tifGenarateDTO.getTransactionRequestName()));
                    cell.setPaddingLeft(2);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(tifGenarateDTO.getIdentification()));
                    cell.setPaddingLeft(2);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(tifGenarateDTO.getEpfNumber()));
                    cell.setPaddingLeft(2);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(tifGenarateDTO.getName()));
                    cell.setPaddingLeft(2);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(tifGenarateDTO.getSignatureURL()));
                    cell.setPaddingLeft(2);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(cell);


                }else{

                    cell = new PdfPCell(new Phrase(" "));
                    cell.setPaddingLeft(2);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(" "));
                    cell.setPaddingLeft(2);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(" "));
                    cell.setPaddingLeft(2);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(" "));
                    cell.setPaddingLeft(2);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(" "));
                    cell.setPaddingLeft(2);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase(" "));
                    cell.setPaddingLeft(2);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(cell);

                }

                cell = new PdfPCell(new Phrase(dto.getType()));
                cell.setPaddingLeft(2);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(dto.getUrl()));
                cell.setPaddingLeft(2);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                count++;

        }



            PdfWriter.getInstance(document, out);
            document.open();
            document.add(table);

            document.close();

        } catch (DocumentException ex) {

            Logger.getLogger(DailyReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

}
