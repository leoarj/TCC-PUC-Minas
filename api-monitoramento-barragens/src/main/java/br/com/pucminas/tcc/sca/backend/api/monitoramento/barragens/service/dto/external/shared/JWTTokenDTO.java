package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.external.shared;

import java.io.Serializable;

public class JWTTokenDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2623152268038601261L;
	
	private String id_token;

	public String getId_token() {
		return id_token;
	}

	public void setId_token(String id_token) {
		this.id_token = id_token;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_token == null) ? 0 : id_token.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JWTTokenDTO other = (JWTTokenDTO) obj;
		if (id_token == null) {
			if (other.id_token != null)
				return false;
		} else if (!id_token.equals(other.id_token))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JWTTokenDTO [id_token=" + id_token + "]";
	}
}
