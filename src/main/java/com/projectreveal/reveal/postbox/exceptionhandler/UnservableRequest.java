package com.projectreveal.reveal.postbox.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UnservableRequest extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String message;
	
}
