package com.devonfw.devonlocale.translator;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import com.devonfw.devonlocale.common.Node;

/**
 * Interface representing general methods for sources.
 *
 * @author ssarmoka
 */
public interface TranslationSource {

  /**
   * This method reads input properties from console.
   *
   * @param in
   * @return
   */
  Map<String, Node> parseStream(InputStream in);

  /**
   * This method reads input properties from java property file.
   *
   * @param in
   * @return
   */
  Map<String, Node> parseFile(File file);
}
