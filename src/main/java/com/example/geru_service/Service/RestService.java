package com.example.geru_service.Service;

import com.example.geru_service.DAO.ItemDAO;
import com.example.geru_service.DAO.OrderDAO;
import com.example.geru_service.Entity.Orders;
import com.example.geru_service.Model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
public class RestService {
    @Autowired
    UserService userService;
    @Autowired
    ItemDAO itemDAO;
    @Autowired
    OrderDAO orderDAO;

    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RestService.class);

    @Autowired
    private MailService mailService;

    public RestService() {
        this.restTemplate = new RestTemplate();
    }
    @Value("${api.apiUrl}")
    private String apiUrl;

    @Value("${api.token}")
    private String token;
    public InvoiceResponseModel createInvoice(Long amount, String description) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("amount", amount);
        requestBody.put("description", description);
        requestBody.put("auto_advance", true);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                entity,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Map to wrapper class first
            InvoiceResponseWrapper wrapper = objectMapper.readValue(response.getBody(), InvoiceResponseWrapper.class);
            return wrapper.getData(); // Extract the actual invoice data
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse invoice response", e);
        }
    }



    public InvoiceResponse getInvoiceById(Long invoiceId) {
        String url = apiUrl + "/" + invoiceId;  // Construct the URL with invoiceId

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);  // Set authorization token
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            // Perform the HTTP GET request to fetch the invoice details
            ResponseEntity<InvoiceResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    InvoiceResponse.class
            );

            // Return the response body containing the InvoiceResponse
            return response.getBody();
        } catch (RestClientException e) {
            logger.error("Error fetching invoice with ID: {}", invoiceId, e);
            return null;  // Handle the error accordingly (you may want to throw an exception here)
        }
    }



    public ResponseEntity<String> deleteInvoiceById(Long invoiceId) {
        String url = apiUrl + "/" + invoiceId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    entity,
                    String.class
            );
            return ResponseEntity.ok("Нэхэмжлэх амжилттай устгалаа");
        } catch (RestClientException e) {
            return ResponseEntity.status(500).body("Нэхэмжлэх устгах явцад алдаа гарлаа.");
        }
    }

    public Message checkInvoice(Long invoiceId) {
        Message message = new Message();
        InvoiceResponseData data = getInvoiceById(invoiceId).getData();
        if(Objects.equals(data.getStatus(), "paid")){
            message.setMessage("Төлбөр төлөгдсөн");
            message.setStatus(200L);
            return message;
        }
        if(Objects.equals(data.getStatus(), "open")){
            //Төлөгдөөгүй
            message.setMessage("Төлбөр төлөгдөөгүй байна.");
            message.setStatus(400L);
            return message;
        }

        message.setData(null);
        message.setMessage("Тодорхойгүй");
        message.setStatus(500L);
        return message;
    }

    public ResponseEntity<Message> createOPI(List<OrderModel> list) {
        Message message = new Message();

        AtomicReference<Long> amount = new AtomicReference<>(0L);
        list.forEach(item -> {
            Long price = itemDAO.findById(item.getProductId()).getPrice();
            amount.updateAndGet(v -> v + price * item.getCount());
        });

        String invoiceValue = String.valueOf(list.get(0).getRestaurantId() + 'R' + list.get(0).getTableId() + 'T');

                //Төлбөр төлөх хуудас үүсгэх
        InvoiceResponseModel invoiceResponseModel = createInvoice(amount.get(), invoiceValue);


        Long invoiceId = invoiceResponseModel.getId();
        list.forEach(item -> {
            Orders order = new Orders();
            order.setRestaurantId(item.getRestaurantId());
            order.setProductId(item.getProductId());
            order.setTableId(item.getTableId());
            order.setCount(item.getCount());
            order.setInvoiceId(invoiceId);
            order.setIsPaid(0L);
            order.setStatus(1L);
            orderDAO.save(order);
        });

        message.setStatus(200L);
        message.setMessage("Захиалга бүртгэгдлээ одоо төлбөр шалгана аа бронжиии.");
        message.setData(invoiceResponseModel);
        return ResponseEntity.ok(message);
    }

}
