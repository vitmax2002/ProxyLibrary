package com.esempla.proxy.service;


import com.esempla.proxy.model.Book;
import com.esempla.proxy.model.Token;
import com.esempla.proxy.model.dto.BookSaveDto;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class BookService {
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.key}")
    private String key;

    private final RabbitTemplate rabbitTemplate;
    private final RestTemplate restTemplate;
    private final Logger log= LoggerFactory.getLogger(BookService.class);


    @Value("${name.service.url}")
    private String paymentsServiceUrl;

    public BookService(RabbitTemplate rabbitTemplate, RestTemplate restTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.restTemplate = restTemplate;
    }

    public List<Book> getAll() {
        String uri = paymentsServiceUrl + "/books/show";
        ResponseEntity<List<Book>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @RabbitListener(queues = {"${rabbitmq.queue}"})
public void showBooks2(Book book){
        List<Book> books=new ArrayList<>();
        books.add(book);
        log.debug("Book: {}",books);
}

  public Book addBook(BookSaveDto dto)
  {
      String uri = paymentsServiceUrl + "/books/add";
      HttpHeaders headersBook = new HttpHeaders();
      headersBook.setContentType(MediaType.APPLICATION_JSON);
      headersBook.set("Authorization", "Bearer " + Token.token);

      HttpEntity<BookSaveDto> requestEntityBook = new HttpEntity<>(dto, headersBook);

      ResponseEntity<Book> responseEntityBook = restTemplate.exchange(uri, HttpMethod.POST, requestEntityBook, Book.class);

       return responseEntityBook.getBody();
  }

  public Book getBookByIsbn(String isbn)
  {
      String uri = paymentsServiceUrl + "/books/get/"+isbn;
      HttpEntity<String> requestEntity=new HttpEntity<>(isbn);
      ResponseEntity<Book> responseEntityBook = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, Book.class);
      rabbitTemplate.convertAndSend(exchange, key, responseEntityBook.getBody());
      return responseEntityBook.getBody();
  }

    public void downloadBookAsPdf() throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(System.getProperty("user.home") +"/Downloads/book.pdf"));
        document.open();

        PdfPTable pdfPTable = new PdfPTable(6);

        PdfPCell pdfPCell1 = new PdfPCell(new Paragraph("isbn"));
        PdfPCell pdfPCell2 = new PdfPCell(new Paragraph("name"));
        PdfPCell pdfPCell3 = new PdfPCell(new Paragraph("publisherId"));
        PdfPCell pdfPCell4 = new PdfPCell(new Paragraph("publisherYear"));
        PdfPCell pdfPCell5 = new PdfPCell(new Paragraph("copies"));
        PdfPCell pdfPCell6 = new PdfPCell(new Paragraph("picture"));
        pdfPTable.addCell(pdfPCell1);
        pdfPTable.addCell(pdfPCell2);
        pdfPTable.addCell(pdfPCell3);
        pdfPTable.addCell(pdfPCell4);
        pdfPTable.addCell(pdfPCell5);
        pdfPTable.addCell(pdfPCell6);

        for(Book book2:getAll()){
            PdfPCell pdfPCell11 = new PdfPCell(new Paragraph(book2.getIsbn()));
            PdfPCell pdfPCell22 = new PdfPCell(new Paragraph(book2.getName()));
            PdfPCell pdfPCell33 = new PdfPCell(new Paragraph(String.valueOf(book2.getPublisher().getId())));
            PdfPCell pdfPCell44 = new PdfPCell(new Paragraph(book2.getPublishYear()));
            PdfPCell pdfPCell55 = new PdfPCell(new Paragraph(String.valueOf(book2.getCopies())));
            PdfPCell pdfPCell66 = new PdfPCell(new Paragraph(book2.getPicture()));
            pdfPTable.addCell(pdfPCell11);
            pdfPTable.addCell(pdfPCell22);
            pdfPTable.addCell(pdfPCell33);
            pdfPTable.addCell(pdfPCell44);
            pdfPTable.addCell(pdfPCell55);
            pdfPTable.addCell(pdfPCell66);
        }
        document.add(pdfPTable);
        document.close();
        writer.close();
    }

  public Book updateBook(String isbn,BookSaveDto bookSaveDto){
      String uri = paymentsServiceUrl + "/books/update/"+isbn;
      HttpHeaders headers=new HttpHeaders();
      headers.set("Authorization", "Bearer " + Token.token);
      HttpEntity<BookSaveDto> requestEntity=new HttpEntity<>(bookSaveDto,headers);
      ResponseEntity<Book> responseEntity= restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, Book.class);
      return responseEntity.getBody();
  }

    public void deleteBook(String isbn){
        String uri = paymentsServiceUrl + "/books/delete/"+isbn;
        HttpHeaders headers=new HttpHeaders();
        headers.set("Authorization", "Bearer " + Token.token);
        HttpEntity<String> requestEntity=new HttpEntity<>(isbn,headers);
        ResponseEntity<Void> responseEntity= restTemplate.exchange(uri, HttpMethod.DELETE,requestEntity,Void.class);
    }

}

