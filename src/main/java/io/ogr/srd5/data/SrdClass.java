/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.ogr.srd5.data;

import java.util.List;

/**
 *
 * @author Olivier Grégoire <ogregoire@users.noreply.github.com>
 */
public class SrdClass {

  private String id;
  private int hitDice;
  private int maxLevel;

  public String getId() {
    return id;
  }

  static class Holder {

    List<SrdClass> classes;

    List<SrdClass> getClasses() {
      return classes;
    }
  }
}
