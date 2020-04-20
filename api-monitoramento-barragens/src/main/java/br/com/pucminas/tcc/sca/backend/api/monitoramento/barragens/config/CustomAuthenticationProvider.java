package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.config;

import java.time.Duration;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.security.jwt.TokenProvider;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.external.shared.JWTTokenDTO;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.external.shared.LoginDTO;


/**
 * Implementa um provedor de autenticação próprio para o microservico
 * para que após carga e configuração completa da aplicação os eventos recebidos do Hub IoT
 * possam ser tratados automaticamente.
 * 
 * Realiza o pedido de autenticação no gateway.
 * 
 * @author Leandro Araújo (leandro.araujo@outlook.com)
 *
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	private final Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	@Autowired
	private ApplicationProperties applicationProperties;
	@Autowired
	private TokenProvider tokenProvider;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		// Métodos específicos para automação do processo de eventos recebidos do Hub IoT
		
		// Autenticação autônoma para o microserviço realizar a comunicação dos eventos
		// de medição recebidos do Hub de IoT.
		// Necessário caso algum token de acesso ainda não tiver sido interceptado
		// o microserviço não vai conseguir registar e notificar os eventos recebidos.

		try {

			String name = authentication.getName();
			String password = authentication.getCredentials().toString();

			LoginDTO loginDTO = new LoginDTO();
			loginDTO.setPassword(password);
			loginDTO.setRememberMe(true);
			loginDTO.setUsername(name);

			// Realizar a autenticação solicitando no gateway

			final String uri = applicationProperties
					.getTCCSca()
					.getServicesURL()
					.getApiFrontEndGateway() + "/api/authenticate";

			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setAccept(Arrays.asList(MediaType.ALL));
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<LoginDTO> entity = new HttpEntity<>(loginDTO, httpHeaders);

			RestTemplate restTemplate = new RestTemplateBuilder()
					.setConnectTimeout(Duration.ofSeconds(500))
					.setReadTimeout(Duration.ofSeconds(500))
					.build();

			ResponseEntity<String> response = restTemplate
					.exchange(
							uri, 
							HttpMethod.POST, 
							entity, 
							String.class
						);

			if (response.getStatusCode().equals(HttpStatus.OK)) {

				int startToken = response.getBody().indexOf("{");
				int endToken = response.getBody().indexOf("}") + 1;
				String token = response.getBody().substring(startToken, endToken);

				try {
					ObjectMapper objectMapper = new ObjectMapper();
					JWTTokenDTO jWTTokenDTO = objectMapper.readValue(token, JWTTokenDTO.class);

					return tokenProvider.getAuthentication(jWTTokenDTO.getId_token());
				} catch (JsonProcessingException e) {
					log.error("Falha ao processar o token de autenticação para o microserviço : {}", e.getMessage());
				}
			}

		} catch (Exception e) {
			log.error("Falha ao processar autenticação para o microserviço : {}", e.getMessage());
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
