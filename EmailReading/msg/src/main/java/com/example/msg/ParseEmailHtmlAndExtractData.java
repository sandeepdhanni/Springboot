package com.example.msg;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseEmailHtmlAndExtractData {

    public static void main(String[] args) {
        String htmlContent =
                "\n" +
                "Hi team,\n" +
                "\n" +
                "  \t\tDate\n" +
                "  \t\t24/02/2025\n" +
                "\n" +
                "  \t\tEmp Id\n" +
                "  \t\t1215\n" +
                "\n" +
                "  \t\tEmp Name\n" +
                "  \t\tSandeep dhanni\n" +
                "\n" +
                "  \t\tDaily Work Status\n" +
                "  \t\tInprogress\n" +
                "\n" +
                "  \t\tWhat Task assigned\n" +
                "  \t\tCamunda 8 developer certification\n" +
                "\n" +
                "  \t\tWho assigned the task\n" +
                "  \t\tsrinivas pilli,vijay tatineni\n" +
                "\n" +
                "  \t\tTask Completed(Yes/No)No\n" +
                "  \t\tNo\n" +
                "\n" +
                "  \t\tif 'Yes', who monitored\n" +
                "\n" +
                "  \t\tif, 'No', Mention the reason\n" +
                "  \t\tcertification exam is pending\n" +
                "\n" +
                "Thanks & regards\n" +
                "\n" +
                "Sandeep dhanni\n" +
                "Content-Transfer-Encoding: quoted-printable\n" +
                "Content-Type: text/html; charset=UTF-8\n" +
                "\n" +
                "<html><head><meta http-equiv=3D\"Content-Type\" content=3D\"text/html; charset=\n" +
                "=3DUTF-8\" /></head><body style=3D'font-size: 10pt; font-family: Verdana,Gen=\n" +
                "eva,sans-serif'>\n" +
                "<div style=3D\"font-size: 10pt; font-family: Verdana,Geneva,sans-serif;\">\n" +
                "<p>Hi team,</p>\n" +
                "<table style=3D\"border-collapse: collapse; width: 100.229%; height: 198px;\"=\n" +
                " border=3D\"1\">\n" +
                "<tbody>\n" +
                "<tr style=3D\"height: 22px;\">\n" +
                "<td style=3D\"width: 54.9781%; height: 22px;\">Date</td>\n" +
                "<td style=3D\"width: 45.0237%; height: 22px;\">24/02/2025</td>\n" +
                "</tr>\n" +
                "<tr style=3D\"height: 22px;\">\n" +
                "<td style=3D\"width: 54.9781%; height: 22px;\">Emp Id</td>\n" +
                "<td style=3D\"width: 45.0237%; height: 22px;\">1215</td>\n" +
                "</tr>\n" +
                "<tr style=3D\"height: 22px;\">\n" +
                "<td style=3D\"width: 54.9781%; height: 22px;\">Emp Name</td>\n" +
                "<td style=3D\"width: 45.0237%; height: 22px;\">Sandeep dhanni</td>\n" +
                "</tr>\n" +
                "<tr style=3D\"height: 22px;\">\n" +
                "<td style=3D\"width: 54.9781%; height: 22px;\">Daily Work Status</td>\n" +
                "<td style=3D\"width: 45.0237%; height: 22px;\">Inprogress</td>\n" +
                "</tr>\n" +
                "<tr style=3D\"height: 22px;\">\n" +
                "<td style=3D\"width: 54.9781%; height: 22px;\">What Task assigned</td>\n" +
                "<td style=3D\"width: 45.0237%; height: 22px;\">Camunda 8 developer certificat=\n" +
                "ion</td>\n" +
                "</tr>\n" +
                "<tr style=3D\"height: 22px;\">\n" +
                "<td style=3D\"width: 54.9781%; height: 22px;\">Who assigned the task</td>\n" +
                "<td style=3D\"width: 45.0237%; height: 22px;\"><span style=3D\"color: #333333;=\n" +
                " font-size: 13.3333px; font-family: Verdana, Geneva, sans-serif; background=\n" +
                "-color: #ffffff;\">srinivas pilli,vijay tatineni</span></td>\n" +
                "</tr>\n" +
                "<tr style=3D\"height: 22px;\">\n" +
                "<td style=3D\"width: 54.9781%; height: 22px;\">Task Completed(Yes/No)No</td>\n" +
                "<td style=3D\"width: 45.0237%; height: 22px;\">No</td>\n" +
                "</tr>\n" +
                "<tr style=3D\"height: 22px;\">\n" +
                "<td style=3D\"width: 54.9781%; height: 22px;\">if 'Yes', who monitored</td>\n" +
                "<td style=3D\"width: 45.0237%; height: 22px;\">&nbsp;</td>\n" +
                "</tr>\n" +
                "<tr style=3D\"height: 22px;\">\n" +
                "<td style=3D\"width: 54.9781%; height: 22px;\">if, 'No', Mention the reason</=\n" +
                "td>\n" +
                "<td style=3D\"width: 45.0237%; height: 22px;\">certification exam is pending<=\n" +
                "/td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "<p>Thanks &amp; regards</p>\n" +
                "<p>Sandeep dhanni</p>\n" +
                "</div>\n" +
                "</body></html>\n" +
                "\n";

        // Parse the HTML
        Document doc = Jsoup.parse(htmlContent);

        // Select the table rows
        Elements rows = doc.select("table tr");

        // Iterate through the rows and extract key-value pairs
        for (Element row : rows) {
            Elements cols = row.select("td");
            if (cols.size() >= 2) {
                String key = cols.get(0).text().trim();
                String value = cols.get(1).text().trim();
                System.out.println(key + " => " + value);
            }
        }
    }
}
