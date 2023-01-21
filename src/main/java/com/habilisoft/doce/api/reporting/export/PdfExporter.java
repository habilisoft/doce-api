package com.habilisoft.doce.api.reporting.export;

import com.habilisoft.doce.api.reporting.domain.model.Report;
import com.habilisoft.doce.api.reporting.domain.model.ReportUIColumn;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayOutputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PdfExporter implements Exporter {
    private Document document;
    private PdfPTable table;
    private Exportable exportable;
    private static final Font captionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
    private static final Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
    private static final Font rowFont = FontFactory.getFont(FontFactory.HELVETICA);
    private static final Font filterFont = FontFactory.getFont(FontFactory.HELVETICA);

    @Override
    public Resource export(Exportable exportable) throws DocumentException {
        init(exportable);
        this.exportable = exportable;
        Report report = exportable.getReport();
        this.createCaption(report.getName());
        this.createUserFilters(exportable.getUserFilters());
        this.createHeaders();
        this.createRows();
        return this.generateResource();
    }

    private Resource generateResource() throws DocumentException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();
        document.add(table);
        document.close();
        return new ByteArrayResource(out.toByteArray());
    }

    @Override
    public void init(Exportable exportable) {
        this.document = new Document(PageSize.A4.rotate(), 10F, 10F, 10F, 10F);
        this.table = new PdfPTable(exportable.getReport().getUiColumns().size());
        this.table.setWidthPercentage(100);
        captionFont.setSize(16);
        headFont.setSize(8);
        rowFont.setSize(8);

        filterFont.setSize(7);
    }

    @Override
    public void createCaption(String caption) {
        PdfPCell cell = new PdfPCell(new Phrase(caption, captionFont));
        cell.setRowspan(2);
        cell.setColspan(this.table.getNumberOfColumns());
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(26);
        this.table.addCell(cell);
    }

    @Override
    public void createUserFilters(List<UserFilter> userFilters) {
        if (userFilters == null || userFilters.size() == 0) {
            return;
        }
        PdfPCell cell = new PdfPCell(new Phrase("Este reporte ha sido generado basado en los siguientes criterios:"));
        cell.setRowspan(2);
        cell.setColspan(this.table.getNumberOfColumns());
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(20);
        this.table.addCell(cell);
        userFilters.forEach(this::createFilter);
        PdfPCell filtersCell = new PdfPCell(new Phrase(" "));
        Integer columnsLeft = userFilters.size() > this.table.getNumberOfColumns()?this.table.getNumberOfColumns() - (userFilters.size() % this.table.getNumberOfColumns()):this.table.getNumberOfColumns()-userFilters.size();
        filtersCell.setColspan(columnsLeft);
        this.table.addCell(filtersCell);
        PdfPCell breakCell = new PdfPCell(new Phrase(" "));
        breakCell.setRowspan(2);
        breakCell.setColspan(this.table.getNumberOfColumns());
        this.table.addCell(breakCell);
    }

    @Override
    public void createFilter(UserFilter userFilter) {
        PdfPCell cell = new PdfPCell(
                new Phrase(String.format(
                        "%s:\n%s",
                        userFilter.getDisplayName(),
                        userFilter.getDisplayValue()), filterFont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        this.table.addCell(cell);
    }

    @Override
    public void createHeaders() {
        this.exportable.getReport().getUiColumns()
                .stream()
                .sorted(Comparator.comparing(ReportUIColumn::getDisplayOrder))
                .forEach(c -> createHeader(c.getHeader()));
    }

    @Override
    public void createHeader(String header) {
        PdfPCell cell = new PdfPCell(new Phrase(header, headFont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        this.table.addCell(cell);
    }

    @Override
    public void createRows() {
        this.exportable.getRecords()
                .forEach(this::createRow);
    }

    @Override
    public void createRow(Map<String, Object> record) {
        this.exportable.getReport().getUiColumns()
                .stream()
                .sorted(Comparator.comparing(ReportUIColumn::getDisplayOrder))
                .map(column -> Optional.ofNullable(record.getOrDefault(column.getField(), ""))
                        .map(Object::toString).orElse(""))
                .forEach(this::createCell);
    }

    @Override
    public void createCell(String value) {
        PdfPCell cell = new PdfPCell(new Phrase(value, rowFont));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        this.table.addCell(cell);
    }


}
