package br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.service.exceptions;

public class ExecutarPlanoAcaoException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3216943489116081578L;

	public ExecutarPlanoAcaoException(String errorMessage) {
		super(errorMessage);
	}
	
	public ExecutarPlanoAcaoException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}

}
