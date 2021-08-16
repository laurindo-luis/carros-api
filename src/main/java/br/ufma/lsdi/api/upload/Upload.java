package br.ufma.lsdi.api.upload;

import lombok.Data;

@Data
public class Upload {
	private String fileName;
	private String mimeType;
	private String base64;
}
