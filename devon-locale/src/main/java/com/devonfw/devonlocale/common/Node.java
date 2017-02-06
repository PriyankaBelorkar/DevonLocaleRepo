package com.devonfw.devonlocale.common;

import java.util.Map;

/**
 * General interface for listing tree node functionality.s
 *
 * @author ssarmoka
 */
public interface Node {

  /**
   * This method checks if tree node has children or is a leaf node.
   *
   * @return
   */
  boolean hasChildren();

  /**
   * If tree node is leaf node getText will return text of respective node else it will return null.
   *
   * @return
   */
  String getText();

  /**
   * This will return list of children for tree node.
   *
   * @return
   */
  Map<String, Node> getChildren();
}
