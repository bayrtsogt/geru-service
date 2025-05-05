package com.example.geru_service.Controller;

import com.example.geru_service.Service.QRCodeService;
import com.example.geru_service.Service.ServerIpProvider;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/api/qrcode")
@CrossOrigin(origins = "*")
public class QRCodeController {

    private final ServerIpProvider ipProvider;
    private final QRCodeService qrCodeService;

    public QRCodeController(ServerIpProvider ipProvider, QRCodeService qrCodeService) {
        this.ipProvider = ipProvider;
        this.qrCodeService = qrCodeService;
    }

    @GetMapping("/{restaurantId}/{tableNumber}")
    public String generateQRCode(
            @PathVariable Long restaurantId,
            @PathVariable int tableNumber
    ) throws Exception {
        String hostIp = ipProvider.getPrimaryIp();
        String url    = String.format("http://%s:4200/%d/%d", hostIp, restaurantId, tableNumber);
        byte[] png    = qrCodeService.generateQRCode(url, 300, 300);

        return Base64.getEncoder().encodeToString(png);
    }

}

