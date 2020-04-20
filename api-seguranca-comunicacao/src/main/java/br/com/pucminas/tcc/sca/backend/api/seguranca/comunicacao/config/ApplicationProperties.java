package br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Tcc Sca Puc Minas Back End Api Seguranca Comunicacao.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
	
	private final TCCSca tCCSca = new TCCSca();
	
	public TCCSca getTCCSca() {
		return tCCSca;
	}
	
	public static class TCCSca {
		
		private final ServicesURL servicesURL = new ServicesURL();
		
		public ServicesURL getServicesURL() {
			return servicesURL;
		}
		
		public static class ServicesURL {
			
			private String apiMonitoramento;
			private String apiSeguranca;
			private String apiFrontEndGateway;

			public String getApiMonitoramento() {
				return apiMonitoramento;
			}
			
			public void setApiMonitoramento(String apiMonitoramento) {
				this.apiMonitoramento = apiMonitoramento;
			}
			
			public String getApiSeguranca() {
				return apiSeguranca;
			}

			public void setApiSeguranca(String apiSeguranca) {
				this.apiSeguranca = apiSeguranca;
			}	
			
			public String getApiFrontEndGateway() {
				return apiFrontEndGateway;
			}

			public void setApiFrontEndGateway(String apiFrontEndGateway) {
				this.apiFrontEndGateway = apiFrontEndGateway;
			}
		}
	}
}
