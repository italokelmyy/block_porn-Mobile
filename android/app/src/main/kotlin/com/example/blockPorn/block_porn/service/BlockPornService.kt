package com.example.blockPorn.block_porn.service

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class BlockPornService : AccessibilityService() {

    private val TAG = "BlockPornService"
    private val nomeDoMeuApp = "Blocker"

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

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d(TAG, "Serviço de Acessibilidade Conectado")
    }

    private fun isBlockEnabled(): Boolean {
        val sharedPreferences = getSharedPreferences("FlutterSharedPreferences", Context.MODE_PRIVATE)
        val enabled = sharedPreferences.getBoolean("flutter.block_porn_enabled", false)
         Log.d(TAG, "Bloqueio ativado no SharedPreferences? $enabled")
        return enabled
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {

        if (!isBlockEnabled()) return

        val telaAtual = rootInActiveWindow ?: return
        val nomeDoPacote = event.packageName?.toString() ?: ""



        // 2. Proteção contra Desinstalação e Pausa do próprio App
        val nodeMeuApp = telaAtual.findAccessibilityNodeInfosByText(nomeDoMeuApp)
        if (nodeMeuApp != null && nodeMeuApp.isNotEmpty()) {
            for (termo in impedirADesistalacao) {
                if (telaAtual.findAccessibilityNodeInfosByText(termo)?.isNotEmpty() == true) {
                    Log.d(TAG, "Bloqueando tentativa de desinstalar/parar app: $termo")
                    performGlobalAction(GLOBAL_ACTION_BACK)
                    return
                }
            }
        }

        // 3. Bloquear Sistema (Reiniciar/Modo de Segurança)
        if (nomeDoPacote == "android" || nomeDoPacote == "com.android.systemui") {
            for (termo in bloquearSistema) {
                if (telaAtual.findAccessibilityNodeInfosByText(termo)?.isNotEmpty() == true) {
                    Log.d(TAG, "Bloqueando menu do sistema: $termo")
                    performGlobalAction(GLOBAL_ACTION_BACK)
                    return
                }
            }
        }

        // 4. Bloquear Configurações (Acessibilidade/DNS)
        if (nomeDoPacote == "com.android.settings") {
            for (termo in bloquearConfiguracoes) {
                if (telaAtual.findAccessibilityNodeInfosByText(termo)?.isNotEmpty() == true) {
                    Log.d(TAG, "Bloqueando configurações sensíveis: $termo")
                    performGlobalAction(GLOBAL_ACTION_BACK)
                    return
                }
            }
        }
    }

    override fun onInterrupt() {
        Log.d(TAG, "Serviço Interrompido")
    }
}
