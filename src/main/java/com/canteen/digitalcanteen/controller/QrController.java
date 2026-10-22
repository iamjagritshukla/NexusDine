package com.canteen.digitalcanteen.controller;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.ByteArrayOutputStream;
import java.util.Map;
@RestController
public class QrController {
 @Value("${app.base-url:http://localhost:8080}") private String baseUrl;
 @GetMapping(value="/qr-code.png", produces=MediaType.IMAGE_PNG_VALUE)
 public ResponseEntity<byte[]> qrCode() throws Exception {
  String target=baseUrl.replaceAll("/+$","")+"/";
  BitMatrix matrix=new MultiFormatWriter().encode(target, BarcodeFormat.QR_CODE, 420, 420, Map.of(EncodeHintType.MARGIN,1));
  ByteArrayOutputStream out=new ByteArrayOutputStream(); MatrixToImageWriter.writeToStream(matrix,"PNG",out);
  return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(out.toByteArray());
 }
}
