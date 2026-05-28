package controller;

import DAO.athleteDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DashBoardAthleteRowMdl;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportExcelServlet extends HttpServlet {

    // COLORS — defined once, reused everywhere
    private static final byte[] NAVY    = {(byte)31,  (byte)59,  (byte)87 };
    private static final byte[] BLUE    = {(byte)37,  (byte)99,  (byte)235};
    private static final byte[] WHITE   = {(byte)255, (byte)255, (byte)255};
    private static final byte[] LGREY   = {(byte)241, (byte)245, (byte)249};
    private static final byte[] SGREY   = {(byte)100, (byte)116, (byte)139};
    private static final byte[] LBLUE   = {(byte)239, (byte)246, (byte)255};
    private static final byte[] BORDER  = {(byte)209, (byte)213, (byte)219};
    private static final byte[] ORANGE  = {(byte)251, (byte)146, (byte)60 };
    private static final byte[] GREEN   = {(byte)34,  (byte)197, (byte)94 };
    private static final byte[] RED     = {(byte)239, (byte)68,  (byte)68 };

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<DashBoardAthleteRowMdl> list =
                new athleteDAO().getDashboardAthleteList();

            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("Athletes");

            // BUILD ALL STYLES
            XSSFCellStyle titleStyle    = buildStyle(wb, NAVY,   WHITE, 16, true,  HorizontalAlignment.CENTER, false);
            XSSFCellStyle dateStyle     = buildStyle(wb, LGREY,  SGREY,  9, false, HorizontalAlignment.RIGHT,  true);
            XSSFCellStyle headerStyle   = buildStyle(wb, BLUE,   WHITE, 11, true,  HorizontalAlignment.CENTER, false);
            XSSFCellStyle evenStyle     = buildStyle(wb, LBLUE,  null,  10, false, HorizontalAlignment.CENTER, false);
            XSSFCellStyle oddStyle      = buildStyle(wb, WHITE,  null,  10, false, HorizontalAlignment.CENTER, false);
            XSSFCellStyle nameEvenStyle = buildStyle(wb, LBLUE,  null,  10, false, HorizontalAlignment.LEFT,   false);
            XSSFCellStyle nameOddStyle  = buildStyle(wb, WHITE,  null,  10, false, HorizontalAlignment.LEFT,   false);
            XSSFCellStyle pendingStyle  = buildStyle(wb, ORANGE, WHITE, 10, true,  HorizontalAlignment.CENTER, false);
            XSSFCellStyle approvedStyle = buildStyle(wb, GREEN,  WHITE, 10, true,  HorizontalAlignment.CENTER, false);
            XSSFCellStyle rejectedStyle = buildStyle(wb, RED,    WHITE, 10, true,  HorizontalAlignment.CENTER, false);

            // ADD BORDERS TO DATA STYLES
            XSSFColor borderColor  = new XSSFColor(BORDER);
            for (XSSFCellStyle s : new XSSFCellStyle[]{
                evenStyle, oddStyle, nameEvenStyle, nameOddStyle,
                headerStyle, pendingStyle, approvedStyle, rejectedStyle
            }) {
                setBorder(s, BorderStyle.THIN, borderColor);
            }

            // ROW 0 — TITLE
            addMergedRow(sheet, 0, 36, titleStyle,
                "Sports Ecosystem — Athlete Dashboard Report", 0, 6);

            // ROW 1 — DATE + COUNT
            addMergedRow(sheet, 1, 20, dateStyle,
                "Generated: " + java.time.LocalDate.now()
                + "   |   Total Athletes: " + list.size(), 0, 6);

            // ROW 2 — SPACER
            sheet.createRow(2).setHeightInPoints(8);

            // ROW 3 — HEADERS
            String[] headers = {"#", "Full Name", "Mobile", "Age", "Sport", "Category", "Status"};
            Row headerRow = sheet.createRow(3);
            headerRow.setHeightInPoints(28);
            for (int i = 0; i < headers.length; i++) {
                createCell(headerRow, i, headers[i], headerStyle);
            }

            // DATA ROWS
            int rowNum = 4;
            int serial = 1;
            for (DashBoardAthleteRowMdl a : list) {
                Row row = sheet.createRow(rowNum);
                row.setHeightInPoints(22);

                boolean even           = (rowNum % 2 == 0);
                XSSFCellStyle base     = even ? evenStyle     : oddStyle;
                XSSFCellStyle nameSty  = even ? nameEvenStyle : nameOddStyle;
                XSSFCellStyle statusSty = pickStatusStyle(a.getStatus(),
                    pendingStyle, approvedStyle, rejectedStyle, base);

                Object[]        values = {serial++, a.getFullName(), a.getMobile(),
                                          a.getAge(), a.getSportType(), a.getCategory(), a.getStatus()};
                XSSFCellStyle[] styles = {base, nameSty, base, base, base, base, statusSty};

                for (int i = 0; i < values.length; i++) {
                    Cell cell = row.createCell(i);
                    if (values[i] instanceof Integer) cell.setCellValue((Integer) values[i]);
                    else                              cell.setCellValue(values[i].toString());
                    cell.setCellStyle(styles[i]);
                }
                rowNum++;
            }

            // COLUMN WIDTHS
            int[] widths = {8, 28, 18, 8, 18, 14, 16};
            for (int i = 0; i < widths.length; i++) {
                sheet.setColumnWidth(i, widths[i] * 256);
            }

            // FREEZE HEADER ROWS
            sheet.createFreezePane(0, 4);

            // SEND AS DOWNLOAD
            String fileName = "Athletes_Report_" + java.time.LocalDate.now() + ".xlsx";
            response.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition",
                "attachment; filename=\"" + fileName + "\"");
            wb.write(response.getOutputStream());
            wb.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h2>Export Failed: " + e.getMessage() + "</h2>");
        }
    }

    // -------------------------------------------------------
    // HELPERS
    // -------------------------------------------------------

    private XSSFCellStyle buildStyle(XSSFWorkbook wb, byte[] bg, byte[] fg,
            int size, boolean bold, HorizontalAlignment align, boolean italic) {
        XSSFCellStyle style = wb.createCellStyle();
        if (bg != null) {
            style.setFillForegroundColor(new XSSFColor(bg));
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
        style.setAlignment(align);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFFont font = wb.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) size);
        font.setBold(bold);
        font.setItalic(italic);
        if (fg != null) font.setColor(new XSSFColor(fg));
        style.setFont(font);
        return style;
    }

    private void setBorder(XSSFCellStyle s, BorderStyle bs, XSSFColor c) {
        s.setBorderTop(bs);    s.setTopBorderColor(c);
        s.setBorderBottom(bs); s.setBottomBorderColor(c);
        s.setBorderLeft(bs);   s.setLeftBorderColor(c);
        s.setBorderRight(bs);  s.setRightBorderColor(c);
    }

    private void addMergedRow(XSSFSheet sheet, int rowIdx, float height,
            XSSFCellStyle style, String value, int c1, int c2) {
        Row row = sheet.createRow(rowIdx);
        row.setHeightInPoints(height);
        Cell cell = row.createCell(c1);
        cell.setCellValue(value);
        cell.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(rowIdx, rowIdx, c1, c2));
    }

    private void createCell(Row row, int col, String value, CellStyle style) {
        Cell cell = row.createCell(col);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private XSSFCellStyle pickStatusStyle(String status,
            XSSFCellStyle pending, XSSFCellStyle approved,
            XSSFCellStyle rejected, XSSFCellStyle def) {
        if (status == null) return def;
        switch (status.toUpperCase()) {
            case "PENDING":  return pending;
            case "APPROVED": return approved;
            case "REJECTED": return rejected;
            default:         return def;
        }
    }
}