package com.example.blockPorn.block_porn.service

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class BlockPornService : AccessibilityService() {

    private val nomeDoMeuApp = "block_porn"

    private val listaParaBloqueiar = listOf(
        "porn", "porno", "sexo", "mia khalifa", "buceta", "novinha pelada",
        "fodendo", "fodi", "safada", "pussy", "naked young girl", " fucking",
        "fucked", "slut", "comendo a madastra", "porno legendado",
        "porno com legenda", "xhamster", "novinha bucetuda",
        "pornhub.com", "xnxx.com", "xvideos.com", "redtube.com", "sex.com", 
        "beeg.com", "youporn.com"
    )

    private val impedirADesistalacao = listOf(
        "desinstalar", "uninstall", "remover", "desativar", "disable",
        "force stop", "forçar parada", "pausar", "pause"
    )

    private val bloquearSistema = listOf(
        "modo de segurança", "safe mode", "reiniciar", "restart"
    )

    private val bloquearConfiguracoes = listOf(
        "acessibilidade", "accessibility", "dns"
    )

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        val telaAtual = rootInActiveWindow
        val nomeDoPacote = event.packageName?.toString() ?: ""

        // 1. Bloqueio de palavras proibidas e Sites
        for (palavra in listaParaBloqueiar) {
            if (telaAtual?.findAccessibilityNodeInfosByText(palavra)?.isNotEmpty() == true) {
                performGlobalAction(GLOBAL_ACTION_BACK)
                return
            }
        }

        // 2. Proteção contra Desinstalação e Pausa do próprio App
        if (telaAtual?.findAccessibilityNodeInfosByText(nomeDoMeuApp)?.isNotEmpty() == true) {
            for (termo in impedirADesistalacao) {
                if (telaAtual.findAccessibilityNodeInfosByText(termo)?.isNotEmpty() == true) {
                    performGlobalAction(GLOBAL_ACTION_BACK)
                    return
                }
            }
        }

        // 3. Bloquear Sistema (Reiniciar/Modo de Segurança)
        if (nomeDoPacote == "android" || nomeDoPacote == "com.android.systemui") {
            for (termo in bloquearSistema) {
                if (telaAtual?.findAccessibilityNodeInfosByText(termo)?.isNotEmpty() == true) {
                    performGlobalAction(GLOBAL_ACTION_BACK)
                    return
                }
            }
        }

        // 4. Bloquear Configurações (Acessibilidade/DNS)
        if (nomeDoPacote == "com.android.settings") {
            for (termo in bloquearConfiguracoes) {
                if (telaAtual?.findAccessibilityNodeInfosByText(termo)?.isNotEmpty() == true) {
                    performGlobalAction(GLOBAL_ACTION_BACK)
                    return
                }
            }
        }
    }

    override fun onInterrupt() {}
}
