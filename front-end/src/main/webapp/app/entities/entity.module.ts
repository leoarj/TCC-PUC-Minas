import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'barragem',
        loadChildren: () =>
          import('./ApiMonitoramentoBarragens/barragem/barragem.module').then(
            m => m.ApiMonitoramentoBarragensBarragemModule
          )
      },
      {
        path: 'sensor',
        loadChildren: () =>
          import('./ApiMonitoramentoBarragens/sensor/sensor.module').then(
            m => m.ApiMonitoramentoBarragensSensorModule
          )
      },
      {
        path: 'evento-medicao',
        loadChildren: () =>
          import('./ApiMonitoramentoBarragens/evento-medicao/evento-medicao.module').then(
            m => m.ApiMonitoramentoBarragensEventoMedicaoModule
          )
      },
      {
        path: 'incidente',
        loadChildren: () =>
          import('./ApiMonitoramentoBarragens/incidente/incidente.module').then(
            m => m.ApiMonitoramentoBarragensIncidenteModule
          )
      },
      {
        path: 'afetado',
        loadChildren: () =>
          import('./ApiMonitoramentoBarragens/afetado/afetado.module').then(
            m => m.ApiMonitoramentoBarragensAfetadoModule
          )
      },
      {
        path: 'evento-medicao-classificacao-alerta',
        loadChildren: () =>
          import(
            './ApiMonitoramentoBarragens/evento-medicao-classificacao-alerta/evento-medicao-classificacao-alerta.module'
          ).then(m => m.ApiMonitoramentoBarragensEventoMedicaoClassificacaoAlertaModule)
      },
      {
        path: 'incidente-resultado-processamento',
        loadChildren: () =>
          import(
            './ApiMonitoramentoBarragens/incidente-resultado-processamento/incidente-resultado-processamento.module'
          ).then(m => m.ApiMonitoramentoBarragensIncidenteResultadoProcessamentoModule)
      },
      {
        path: 'plano-acao',
        loadChildren: () =>
          import('./ApiSegurancaComunicacao/plano-acao/plano-acao.module').then(
            m => m.ApiSegurancaComunicacaoPlanoAcaoModule
          )
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class FrontEndEntityModule {}
