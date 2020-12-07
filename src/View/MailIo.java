/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.*;
import javax.mail.*;
import javax.mail.internet.*;
import com.barcode_coder.java_barcode.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import javax.activation.*;
import javax.mail.util.ByteArrayDataSource;

public class MailIo {

    private final String username = "UGCECE@gmail.com";
    private final String password = "ProjetInfo";
    private int i = 0;

    public void generer(String barcode, String movie, OutputStream outputStream, Timestamp date, int number_place, int room, double price) throws DocumentException, MalformedURLException, IOException {

        Image png = Image.getInstance("pdf.png");
        Image jpeg = Image.getInstance("./Movies/" + movie + ".jpg");
        Image jpg = Image.getInstance(barcode);

        png.setAbsolutePosition(100f, 200f);
        jpg.setAbsolutePosition(240f, 620f);
        jpeg.setAbsolutePosition(250f, 435f);

        jpg.scaleToFit(jpg.getWidth() / 2, jpg.getHeight() / 2);
        jpeg.scaleToFit(jpeg.getWidth() / 3, jpeg.getHeight() / 3);

        Document document = new Document();

        PdfWriter writer = PdfWriter.getInstance(document, outputStream);

        document.open();

        PdfContentByte contentByte = writer.getDirectContent();
        PdfTemplate template = contentByte.createTemplate(document.getPageSize().getWidth(), document.getPageSize().getHeight());

        template.beginText();

        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

<<<<<<< Updated upstream:src/View/MailIo.java
        template.setFontAndSize(bf, 9);
        template.setTextMatrix(220, 500);
        template.showText("Summary of your order : ");
        template.setTextMatrix(220, 320);
        template.showText(number_place + " tickets for " + movie);
        template.setTextMatrix(220, 310);
=======
        template.setFontAndSize(bf, 23);
        template.setTextMatrix(250, 80);
        template.showText(movie);
        template.setFontAndSize(bf, 12);
        template.setTextMatrix(8, 30);
        template.showText(number_place + " tickets");
        template.setTextMatrix(8, 20);
>>>>>>> Stashed changes:src/Controller/MailIo.java
        template.showText("Final price : " + price * number_place + "€");
        template.setTextMatrix(220, 300);
        template.showText("Projection Room : " + room + " , the " + date);
        template.endText();
        contentByte.addTemplate(template, 10, 100);

        document.add(png);
        document.add(jpeg);
        document.add(jpg);

        document.close();
    }

    /**
     *
     * @param mail
     * @param name
     * @param lastname
     * @return
     * @throws DocumentException
     */
    public int envoyer_confirmation(String mail, String name, String lastname) throws DocumentException {

        int nombreAleatoire = 1000 + (int) (Math.random() * ((9999 - 1000) + 1));

        // Etape 1 : Création de la session
        String Newligne = System.getProperty("line.separator");
        

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        javax.mail.Session session = javax.mail.Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            // Etape 2 : Création de l'objet Message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("UGCECE@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
            message.setSubject("Activation of your Account");

            BodyPart messageBodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();// create multipart message 
            messageBodyPart.setText("Hye, " + name + " " + lastname
                    + Newligne + "We are delighted to have you among our loyal members !"
                    + Newligne + "A last step to finalize your account. Please enter this code to validate your email address : "
                    + nombreAleatoire);

            // add the text message to the multipart 
            multipart.addBodyPart(messageBodyPart);

            // combine text and attachment 
            message.setContent(multipart);

            // Etape 3 : Envoyer le message
            Transport.send(message);
            System.out.println("Message_envoye");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return nombreAleatoire;
    }

    /**
     *
     * @param email
     * @param file_movie
     * @param date
     * @param place
     * @param room
     * @param price
     * @throws DocumentException
     */
    public void envoyer_reservation(String email, String id, String file_movie, Timestamp date, int place, int room, float price) throws DocumentException {

        // Etape 1 : Création de la session
        String Newligne = System.getProperty("line.separator");
        Barcode b = BarcodeFactory.createBarcode(BarcodeType.Code128, email);
        b.export("png", 1, 50, false, "./Barcode/barcode_"+id+"_"+i+".png");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        javax.mail.Session session = javax.mail.Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            // Etape 2 : Création de l'objet Message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("UGCECE@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Activation of your Account");

            BodyPart messageBodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();// create multipart message 
            messageBodyPart.setText("Hye " + id + ", "
                    + Newligne + "Thank you for being a fidele client !"
                    + Newligne + "You will find attached your tickets. "
                    + Newligne + "UGC ECE wishes you an excellent movie. "
                    + Newligne + Newligne + Newligne + "Your Movie Theater ");

            // add the text message to the multipart 
            multipart.addBodyPart(messageBodyPart);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            generer("./Barcode/barcode"+id+"_"+i+".png", file_movie, outputStream, date, place, room, price);
            byte[] bytes = outputStream.toByteArray();

            DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
            MimeBodyPart pdfBodyPart = new MimeBodyPart();
            pdfBodyPart.setDataHandler(new DataHandler(dataSource));
            pdfBodyPart.setFileName("ticket's_movie.pdf");
            multipart.addBodyPart(pdfBodyPart);

            // combine text and attachment 
            message.setContent(multipart);

            // Etape 3 : Envoyer le message
            Transport.send(message);
            System.out.println("Message_envoye");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException ex) {
            Logger.getLogger(MailIo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
