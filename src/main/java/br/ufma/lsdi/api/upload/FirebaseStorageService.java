package br.ufma.lsdi.api.upload;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;

@Service
public class FirebaseStorageService {
	
	@PostConstruct //Chamado apenas uma vez quando subir o SpringBoot
	private void init() throws IOException {
		if(FirebaseApp.getApps().isEmpty()) {
			FileInputStream in = new FileInputStream("src/main/resources/serviceAccountKey.json");
			
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(in))
					.setStorageBucket("carros-api-544f3.appspot.com")
					.build();
			
			FirebaseApp.initializeApp(options);
		}
	}
	
	public String upload(Upload upload) {
		Bucket bucket = StorageClient.getInstance().bucket();
		byte[] base64 = Base64.getDecoder().decode(upload.getBase64());
		
		Blob blob = bucket.create(upload.getFileName(), base64, upload.getMimeType());
		
		//Deixar a URL pública
		blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
		
		return String.format("https://storage.googleapis.com/%s/%s", bucket.getName(), upload.getFileName());
	}
}
