/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.ogr.srd5;

import com.google.common.io.Resources;
import io.ogr.srd5.data.JsonDataParser;
import io.ogr.srd5.data.SrdClass;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Olivier Grégoire <ogregoire@users.noreply.github.com>
 */
public class Main {
  public static void main(String[] args) throws IOException {
    
    JsonDataParser parser = new JsonDataParser();
    List<SrdClass> classes = parser.readClasses(Resources.asByteSource(Resources.getResource("data/classes.json")));
    classes.stream().map(SrdClass::getId).forEach(System.out::println);
  }
}
