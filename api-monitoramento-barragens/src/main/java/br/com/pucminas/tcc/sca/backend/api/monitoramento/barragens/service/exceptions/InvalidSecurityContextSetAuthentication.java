package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.exceptions;

public class InvalidSecurityContextSetAuthentication extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7933569621345483198L;

	public InvalidSecurityContextSetAuthentication(String errorMessage) {
		super(errorMessage);
	}
	
	public InvalidSecurityContextSetAuthentication(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}

}
