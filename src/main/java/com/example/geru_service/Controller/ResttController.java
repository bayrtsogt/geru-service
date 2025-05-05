package com.example.geru_service.Controller;

import com.example.geru_service.Model.Message;
import com.example.geru_service.Model.OrderModel;
import com.example.geru_service.Service.RestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/rest")
public class ResttController {

    private final RestService restService;

    public ResttController(RestService restService) {
        this.restService = restService;
    }


    @PostMapping("/createInvoice")
    public ResponseEntity<Message> createInvoice(@RequestBody List<OrderModel> list) throws Exception {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");

        return restService.createOPI(list);
    }

    @GetMapping("/getInvoiceById")
    public Message getInvoiceById(@RequestParam("invoiceId") String invoiceId) throws Exception {
        return restService.checkInvoice(Long.valueOf(invoiceId));
    }

    @GetMapping("/deleteInvoiceById")
    public ResponseEntity<String> deleteInvoiceById(@RequestParam("invoiceId") String encryptedInvoiceId) throws Exception {
        // Decrypt the invoiceId before passing it to the service
//        Long invoiceId = Long.valueOf(AESUtil.decrypt(encryptedInvoiceId));
        return restService.deleteInvoiceById(Long.valueOf(encryptedInvoiceId));
    }
}
