package com.devonfw.devonlocale.translator;

import java.io.File;
import java.io.OutputStream;
import java.util.Map;

import com.devonfw.devonlocale.common.Node;

/**
 * This interface contains methods for translation target. GenerateStream will print output to console and generateFile
 * prints output to file.
 *
 * @author ssarmoka
 */
public interface TranslationTarget {

  /**
   * This method will convert map<String,Node> to corresponding output string. Resulted string will be printed to
   * console.
   *
   * @param root
   * @param out
   */
  void generateStream(Map<String, Node> root, OutputStream out);

  /**
   * This method will convert map<String,Node> to corresponding output string. Resulted string will be saved in output
   * file.
   *
   * @param root
   * @param out
   */
  void generateFile(Map<String, Node> root, File out);
}
