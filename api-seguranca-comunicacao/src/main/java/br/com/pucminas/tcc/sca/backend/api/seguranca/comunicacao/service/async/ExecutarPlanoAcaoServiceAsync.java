package br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.service.async;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.security.SecurityUtils;
import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.config.ApplicationProperties;
import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.domain.enumeration.TipoPlanoAcao;
import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.service.dto.PlanoAcaoDTO;
import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.service.dto.external.AfetadoDTO;
import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.service.dto.external.DadosExecucaoPlanoAcaoDTO;
import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.service.dto.external.IncidenteResultadoProcessamentoDTO;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@Service
public class ExecutarPlanoAcaoServiceAsync {

	private final Logger log = LoggerFactory.getLogger(ExecutarPlanoAcaoServiceAsync.class);
	
	private final String TWILIO_PHONE_NUMBER = "";
	private final String TWILIO_ACCOUNT_SID = "";
	private final String TWILIO_AUTH_TOKEN = "";
	
	private final String SEND_GRID_API_KEY = "";
	
	//@Autowired
    private final ApplicationProperties applicationProperties;
	
	private IncidenteResultadoProcessamentoDTO tratarResultadoProcessamento(String mensagem, List<String> logProcessamento, DadosExecucaoPlanoAcaoDTO dadosExecucaoPlanoAcaoDTO) {
		IncidenteResultadoProcessamentoDTO incidenteResultadoProcessamentoDTO = new IncidenteResultadoProcessamentoDTO();
		
		Optional<String> result;
		result = Optional.ofNullable(logProcessamento.toString());				
		if (result.isPresent()) {					
			incidenteResultadoProcessamentoDTO.setSucesso(true);
			incidenteResultadoProcessamentoDTO.setIncidenteIdentificador(dadosExecucaoPlanoAcaoDTO.getIdentificador());
			incidenteResultadoProcessamentoDTO.setData(Instant.now());
			incidenteResultadoProcessamentoDTO.setIncidenteClassificacao(dadosExecucaoPlanoAcaoDTO.getClassificacao());
			incidenteResultadoProcessamentoDTO.setMensagem(
					mensagem + "\nDetalhes do processamento:\n" + result.get());															
		} else {															
			incidenteResultadoProcessamentoDTO.setSucesso(false);
			incidenteResultadoProcessamentoDTO.setIncidenteIdentificador(dadosExecucaoPlanoAcaoDTO.getIdentificador());
			incidenteResultadoProcessamentoDTO.setData(Instant.now());
			incidenteResultadoProcessamentoDTO.setIncidenteClassificacao(dadosExecucaoPlanoAcaoDTO.getClassificacao());
			incidenteResultadoProcessamentoDTO.setMensagem(
					mensagem + "\nSem detalhes do processamento.");					
		}
		
		return incidenteResultadoProcessamentoDTO;
	}
	
	private void notificarAPIMonitoramento(IncidenteResultadoProcessamentoDTO incidenteResultadoProcessamentoDTO) {
		try {			
			final String uri = applicationProperties
        			.getTCCSca()
        			.getServicesURL()
        			.getApiMonitoramento() + "/api/incidente-resultados-processamento";
			
			//log.info("URI: {}", uri);
        	
    		Optional<String> accessToken = SecurityUtils.getCurrentUserJWT();
    		//log.info("token acesso : {}", accessToken.get());
        	if (accessToken.isPresent()) {
        		
        		HttpHeaders httpHeaders = new HttpHeaders();
        		httpHeaders.setAccept(Arrays.asList(MediaType.ALL));
            	httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            	//httpHeaders.set("Accept-Encoding", "gzip, deflate, br");
            	//httpHeaders.set("Connection", "keep-alive");        	
            	httpHeaders.setBearerAuth(accessToken.get());
            	                                        	
            	HttpEntity<IncidenteResultadoProcessamentoDTO> entity = new HttpEntity<>(incidenteResultadoProcessamentoDTO, httpHeaders);
            	        	
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
            			                                
            	log.info("Resultado requisição: {}", response.getBody());            	
        	} else {
        		log.error("Resultado requisição: {}", "Não foi possível obter autorização para a requisição");            	
        	} 
    	} catch (Exception e) {
    		log.error(e.getMessage());
    	}
    }
	
	private void executarPlanoAcao(PlanoAcaoDTO planoAcaoDTO, DadosExecucaoPlanoAcaoDTO dadosExecucaoPlanoAcaoDTO) {
		
		List<String> logProcessamento = new ArrayList<>();
		String mensagem = "";
		int numeroExcecoes = 0;
		
		try {
			
			//log.info("Enviando alertas... Tipo plano ação : {}", planoAcaoDTO.getTipo());
			//log.info("Quantidade de afetados : {}", dadosExecucaoPlanoAcaoDTO.getBarragem().getAfetados().size());
			
			String textoMensagem = "Alerta!\nBarragem: " + dadosExecucaoPlanoAcaoDTO.getBarragem().getNome()  +
						"\nPlano de Ação: " + planoAcaoDTO.getDescricao() + " - " + planoAcaoDTO.getMensagemAlterta();
			
			if (planoAcaoDTO.getTipo().equals(TipoPlanoAcao.SMS)) {
				Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN); // Inicializar apenas uma vez
			}
			
			Iterator<AfetadoDTO> itAfetadosDTO = dadosExecucaoPlanoAcaoDTO
					.getBarragem()
					.getAfetados()
					.iterator();
			
			AfetadoDTO afetadoDTO = null;
			while (itAfetadosDTO.hasNext()) {
				
				afetadoDTO = itAfetadosDTO.next();

				switch (planoAcaoDTO.getTipo()) {
					case EMAIL: {
						//log.info("Enviando e-mail para o afetado: {}", afetadoDTO.getNome() + " / " + afetadoDTO.getEmail());
						
						// Utilizando API de terceiros (SendGrid)
						
						Email from = new Email("tccsca@pucminas.com", "TCC SCA Puc Minas");
					    String subject = 
					    		"Alerta da Barragem: " + dadosExecucaoPlanoAcaoDTO.getBarragem().getNome() +  
					    		" [Plano de Ação: " + planoAcaoDTO.getDescricao() + "]";					    
					    Email to = new Email(afetadoDTO.getEmail());
					    Content content = new Content("text/plain", textoMensagem);
					    Mail mail = new Mail(from, subject, to, content);

					    SendGrid sg = new SendGrid(SEND_GRID_API_KEY);
					    Request request = new Request();
					    
					    try {
					        request.setMethod(Method.POST);
					        request.setEndpoint("mail/send");
					        request.setBody(mail.build());
					        
					        Response response = sg.api(request);					        
					        
					        logProcessamento.add("* Nome: " + afetadoDTO.getNome() + 				        			
				        			"\nE-mail: " + afetadoDTO.getEmail() +
				        			"\nCód. Status do E-mail: (" + response.getStatusCode() +")" +
				        			"\nMensag. Status do E-mail: (" + response.getBody() + ")" +
				        			"\n");					       					        
					      } catch (IOException e) {
					    	  numeroExcecoes++;
					    	  logProcessamento.add("* ERRO AO ENVIAR ALTERTA (E-MAIL) PARA O AFETADO:" +
					    			  "\nNome: " + afetadoDTO.getNome() +
					    			  "\nE-mail: " + afetadoDTO.getEmail() +
					    			  "\nLog erro: " + e.getMessage() +
					    			  "\n");
					      }
	
						break;
					}
					case SMS: {
						//log.info("Enviando SMS (Telefone Principal) para o afetado: {}", afetadoDTO.getNome() + " / " + afetadoDTO.getTelefonePrincipal());
						//log.info("Enviando SMS (Telefone Reserva) para o afetado: {}", afetadoDTO.getNome() + " / " + afetadoDTO.getTelefoneReserva());
						
						 try {
							 
							 // Utilizando API de terceiros (Twilio)
							 
							 Message mensagem1 = Message
						                .creator(new PhoneNumber(afetadoDTO.getTelefonePrincipal()), // to
						                        new PhoneNumber(TWILIO_PHONE_NUMBER), // from
						                        textoMensagem)
						                .create();
							 
							 Message mensagem2 = Message
						                .creator(new PhoneNumber(afetadoDTO.getTelefoneReserva()), // to
						                        new PhoneNumber(TWILIO_PHONE_NUMBER), // from
						                        textoMensagem)
						                .create();
							
							 logProcessamento.add("* Nome: " + afetadoDTO.getNome() + 
					        			"\nTel. Principal: " + afetadoDTO.getTelefonePrincipal() + 
					        			"\nCód. SID do SMS (Tel. Principal): (" + mensagem1.getSid() +")" + " / " + 
					        			"Cód. Status do SMS (Tel. Principal): ("  + mensagem1.getStatus() + ")" +
					        			"\nTel. Reserva: " + afetadoDTO.getTelefoneReserva() +
					        			"\nCód. SID do SMS (Tel. Reserva): (" + mensagem2.getSid() +")" + 
					        			" Cód. Status do SMS (Tel. Reserva): ("  + mensagem2.getStatus() + ")" +
					        			// "\nE-mail: " + afetadoDTO.getEmail() + " (Plano de ação apenas do tipo SMS)" +
					        			"\n");
						 } catch (Exception e) {
							 // Aplicar política de retry?
							 numeroExcecoes++;
							 logProcessamento.add("* ERRO AO ENVIAR ALTERTA (SMS) PARA O AFETADO:" +
									 "\nNome: " + afetadoDTO.getNome() +
									 "\nTel. Principal: " + afetadoDTO.getTelefonePrincipal() +
									 "\nTel. Reserva: " + afetadoDTO.getTelefoneReserva() +
									 "\nLog erro: " + e.getMessage() +
									 "\n");
						 }
						 
						break;
					}					
				}
								
								
				if (Optional.ofNullable(logProcessamento.toString()).isPresent()) {					
					mensagem = (numeroExcecoes == 0) ? 
							"A API de Segurança e Comunicação finalizou o envio dos alertas." : 
							String.format("A API de Segurança e Comunicação finalizou parcialmente o envio dos alertas com %d erros.", numeroExcecoes); 
					
				} else {																				
					mensagem = "A API de Segurança e Comunicação não finalizou o envio dos alertas.";
				}
			}
		} catch (Exception e) {	
			log.error("Erro ao executar o Plano de Ação:  {}", e.getMessage());	
			mensagem = "A API de Segurança e Comunicação não finalizou o envio dos alertas." + "\nErro: " + e.getMessage();			
		} finally {
			this.notificarAPIMonitoramento(
					this.tratarResultadoProcessamento(
							mensagem, 
							logProcessamento, 
							dadosExecucaoPlanoAcaoDTO
						)
					);
			}
	} 
	
	public ExecutarPlanoAcaoServiceAsync(ApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}
		
	@Async("taskExecutorSecurityContext")
	public void executeAsync(PlanoAcaoDTO planoAcaoDTO, DadosExecucaoPlanoAcaoDTO dadosExecucaoPlanoAcaoDTO) {
		try {
			this.executarPlanoAcao(planoAcaoDTO, dadosExecucaoPlanoAcaoDTO);
		} catch (Exception e) {			
			log.error("Erro ao executar o Plano de Ação:  {}", e.getMessage());
		}		
	}	
}
