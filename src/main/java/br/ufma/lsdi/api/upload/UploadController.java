package br.ufma.lsdi.api.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/upload")
public class UploadController {
	
	@Autowired
	private FirebaseStorageService firebaseStorageService; 
	
	@PostMapping
	public ResponseEntity upload(@RequestBody Upload upload) {
		String url = firebaseStorageService.upload(upload);
		return ResponseEntity.ok(new UploadMessage(url));
	}
}
