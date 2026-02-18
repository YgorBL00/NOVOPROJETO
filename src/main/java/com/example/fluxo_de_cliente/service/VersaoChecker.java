package com.example.fluxo_de_cliente.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.example.fluxo_de_cliente.Launcher;
import javafx.scene.control.Alert;

public class VersaoChecker {

    public static boolean verificarVersao(Connection conn) {
        String sql = "SELECT versao_minima FROM versao_app ORDER BY data_atualizacao DESC LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String versaoMinima = rs.getString("versao_minima");
                if (!compararVersao(Launcher.AppInfo.VERSAO_ATUAL, versaoMinima)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Atualização necessária");
                    alert.setHeaderText("Versão desatualizada");
                    alert.setContentText("Você está usando uma versão antiga do aplicativo. Atualize para continuar.");
                    alert.showAndWait();
                    return false; // versão desatualizada
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true; // versão ok ou erro ao verificar
    }

    private static boolean compararVersao(String atual, String minima) {
        String[] vAtual = atual.split("\\.");
        String[] vMinima = minima.split("\\.");

        for (int i = 0; i < vMinima.length; i++) {
            int a = Integer.parseInt(vAtual[i]);
            int m = Integer.parseInt(vMinima[i]);
            if (a < m) return false; // precisa atualizar
            if (a > m) return true;
        }
        return true;
    }
}