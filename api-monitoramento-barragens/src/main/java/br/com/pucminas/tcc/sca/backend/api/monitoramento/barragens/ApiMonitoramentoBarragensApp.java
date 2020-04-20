package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.config.ApplicationProperties;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.config.CustomAuthenticationProvider;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.EventoMedicaoService;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.external.azure.iothub.ReceptorEventoMedicaoAzureIoTHubService;
import io.github.jhipster.config.DefaultProfileUtil;
import io.github.jhipster.config.JHipsterConstants;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

@SpringBootApplication
@EnableConfigurationProperties({LiquibaseProperties.class, ApplicationProperties.class})
@EnableDiscoveryClient
@EnableAsync
public class ApiMonitoramentoBarragensApp {

    private static final Logger log = LoggerFactory.getLogger(ApiMonitoramentoBarragensApp.class);

    private final Environment env;

    public ApiMonitoramentoBarragensApp(Environment env) {
        this.env = env;
    }

    /**
     * Initializes ApiMonitoramentoBarragens.
     * <p>
     * Spring profiles can be configured with a program argument --spring.profiles.active=your-active-profile
     * <p>
     * You can find more information on how profiles work with JHipster on <a href="https://www.jhipster.tech/profiles/">https://www.jhipster.tech/profiles/</a>.
     */
    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
            log.error("You have misconfigured your application! It should not run " +
                "with both the 'dev' and 'prod' profiles at the same time.");
        }
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_CLOUD)) {
            log.error("You have misconfigured your application! It should not " +
                "run with both the 'dev' and 'cloud' profiles at the same time.");
        }
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ApiMonitoramentoBarragensApp.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }
    
    // Necessário para executar threads usando o contexto de segurança correto
    // em chamada de api para outro microserviço requerendo autenticação.
    // A thread paralela precisa do contexto de segurança para recuperar o token de acesso.
    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
      ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
      executor.setCorePoolSize(1);
      executor.setMaxPoolSize(3);
      executor.setQueueCapacity(500);
      executor.setThreadNamePrefix("api-monitoramento-taskExecutorSecurityContext-");
      executor.initialize();
      
      /**return new DelegatingSecurityContextAsyncTaskExecutor(executor) {
          public void shutdown() {
              executor.destroy();
          }
      };*/
      
      return executor;
    }
   
    // O executor deve ser exposto como bean para ser gerenciado automaticamente pelo Spring (Disposable/Destroy)
    // Injeta pelo tipo, então não precisa de @Qualifier
    @Bean("taskExecutorSecurityContext")
    public DelegatingSecurityContextAsyncTaskExecutor taskExecutor(ThreadPoolTaskExecutor delegate) {
       return new DelegatingSecurityContextAsyncTaskExecutor(delegate);
    }
    
    // Métodos específicos para automação do processo de eventos recebidos do Hub IoT
    @Bean
    public ReceptorEventoMedicaoAzureIoTHubService receptorEventoMedicaoAzureIoTHubService(EventoMedicaoService eventoMedicaoService) {
    	return new ReceptorEventoMedicaoAzureIoTHubService(eventoMedicaoService);
    }
    
    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
    	return new CustomAuthenticationProvider();
    }
    //
    
    private static void logApplicationStartup(Environment env) {
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");
        if (StringUtils.isBlank(contextPath)) {
            contextPath = "/";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info("\n----------------------------------------------------------\n\t" +
                "Application '{}' is running! Access URLs:\n\t" +
                "Local: \t\t{}://localhost:{}{}\n\t" +
                "External: \t{}://{}:{}{}\n\t" +
                "Profile(s): \t{}\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            protocol,
            serverPort,
            contextPath,
            protocol,
            hostAddress,
            serverPort,
            contextPath,
            env.getActiveProfiles());

        String configServerStatus = env.getProperty("configserver.status");
        if (configServerStatus == null) {
            configServerStatus = "Not found or not setup for this application";
        }
        log.info("\n----------------------------------------------------------\n\t" +
                "Config Server: \t{}\n----------------------------------------------------------", configServerStatus);
    }
}
