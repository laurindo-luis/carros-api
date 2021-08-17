package br.ufma.lsdi.api.carros;

import java.io.Serializable;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageOutput implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer totalPages;
	private Integer atualPage;
	private Integer sizeOfPage;
	private Object body;
}
