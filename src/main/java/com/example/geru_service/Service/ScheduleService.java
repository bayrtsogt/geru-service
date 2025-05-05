package com.example.geru_service.Service;

import com.example.geru_service.DAO.OrderDAO;
import com.example.geru_service.Entity.Orders;
import com.example.geru_service.Model.Message;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    RestService restService;

    @Autowired
    OrderDAO orderDAO;

    @Scheduled(fixedRate = 1000)
    public void checkInvoice() {
        try {
            List<Orders> list = orderDAO.getNotPaid();
            list.forEach( item -> {
                Message msg = restService.checkInvoice(item.getInvoiceId());
                if(msg.getStatus() == 200L){
                    List<Orders> orders = orderDAO.findByInvoiceId(item.getInvoiceId());
                    orders.forEach( order -> {
                        order.setIsPaid(1L);
                        orderDAO.save(order);
                    });
                }
            });
        } catch (Exception e) {
            // handle or log
            e.printStackTrace();
        }
    }
}
