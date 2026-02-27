package com.example.blockPorn.block_porn.service

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class BlockPornService : AccessibilityService() {

    private val nomeDoMeuApp = "block_porn"

    private val listaParaBloqueiar = listOf(
        "porn", "porno", "sexo", "mia khalifa", "buceta", "novinha pelada",
        "fodendo", "fodi", "safada", "pussy", "naked young girl", " fucking",
        "fucked", "slut"
    )

    private val impedirADesistalacao = listOf(
        "desinstalar", "uninstall", "remover"
    )

    // Lista para impedir acesso ao Modo de Segurança e Reinicialização
    private val bloquearSistema = listOf(
        "modo de segurança", "safe mode", "reiniciar", "restart"
    )

    // Lista para bloquear o menu de Acessibilidade
    private val bloquearAcessibilidade = listOf(
        "acessibilidade", "accessibility"
    )

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        val eventText = event.source?.text.toString().lowercase()
        val eventText2 = event.text.toString().lowercase()
        val packageName = event.packageName?.toString() ?: ""

        // 1. Bloqueio de palavras proibidas (Navegador/Apps)
        for (bloqueio in listaParaBloqueiar) {
            if (eventText.contains(bloqueio.lowercase())) {
                performGlobalAction(GLOBAL_ACTION_BACK)
                return
            }
        }

        // 2. Proteção contra Desinstalação e Desativação (Acessibilidade)
        if (eventText.contains(nomeDoMeuApp.lowercase())) {

            // Verifica se está tentando desinstalar
            for (antiUnistaller in impedirADesistalacao) {
                if (eventText.contains(antiUnistaller.lowercase())) {
                    performGlobalAction(GLOBAL_ACTION_BACK)
                    return
                }
            }
        }

        // 3. Bloquear Modo de Segurança e Reiniciar
        if (packageName == "android" || packageName == "com.android.systemui") {
            for (termo in bloquearSistema) {
                if (eventText2.contains(termo)) {
                    performGlobalAction(GLOBAL_ACTION_BACK)
                    return
                }
            }
        }

        // 4. Bloquear acesso às Configurações de Acessibilidade
        if (packageName == "com.android.settings") {
            for (termo in bloquearAcessibilidade) {
                if (eventText.contains(termo) || eventText2.contains(termo)) {
                    performGlobalAction(GLOBAL_ACTION_BACK)
                    return
                }
            }
        }
    }

    override fun onInterrupt() {
        Log.e("BlockService", "Acessibilidade desativada")
    }
}
