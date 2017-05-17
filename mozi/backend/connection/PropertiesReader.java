/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mozi.backend.connection;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Util osztály, melynek segítségével be tudunk tölteni konfigurációval kapcsolatos kulcs-érték párokat egy szabványos properties file-ból.
 */
public final class PropertiesReader {

    private PropertiesReader(){
        throw new IllegalStateException("Util osztályból nem lehet példányt létrehozni!");
    }

    public static Properties readProperties(String path){
        try (final FileInputStream fis = new FileInputStream(new File(path))){
            final Properties properties = new Properties();
            properties.load(fis);
            return properties;
        } catch (Exception ex) {
            throw new IllegalStateException("A megadott utvonalon nem érhető el a konfigurációs file!", ex);
        }
    }

}

