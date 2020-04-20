package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.external.shared;

import java.io.Serializable;

public class LoginDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4943601301410384266L;
	
	private String password;
	private Boolean rememberMe;
	private String username;
		
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getRememberMe() {
		return rememberMe;
	}
	public void setRememberMe(Boolean rememberMe) {
		this.rememberMe = rememberMe;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((rememberMe == null) ? 0 : rememberMe.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		LoginDTO other = (LoginDTO) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (rememberMe == null) {
			if (other.rememberMe != null)
				return false;
		} else if (!rememberMe.equals(other.rememberMe))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "LoginDTO [password=" + password + ", rememberMe=" + rememberMe + ", username=" + username + "]";
	}	
}
