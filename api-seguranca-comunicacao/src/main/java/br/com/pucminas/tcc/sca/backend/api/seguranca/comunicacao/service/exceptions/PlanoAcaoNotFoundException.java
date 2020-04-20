package br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.service.exceptions;

public class PlanoAcaoNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6522454245997654209L;
	
	public PlanoAcaoNotFoundException(String errorMessage) {
		super(errorMessage);
	}
	
	public PlanoAcaoNotFoundException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}

}
