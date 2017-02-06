package com.devonfw.devonlocale.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of Node interface. Every instance of TreeNode represents node in tree.
 *
 * @author ssarmoka
 */
public class TreeNode implements Node {

  // String text will be null for non-leaf nodes
  private String text = null;

  private Map<String, Node> childeNodeMap = new HashMap<>();

  /**
   * The constructor.
   */
  public TreeNode() {

  }

  /**
   * The constructor.
   *
   * @param text
   */
  public TreeNode(String text) {
    this.text = text;
  }

  /**
   * This method checks if node have any children.
   *
   * @return
   */
  public boolean hasChildren() {

    if (this.childeNodeMap.isEmpty())
      return false;
    else
      return true;
  }

  /**
   * This method will return text value of respective node. if node is leaf node then text will have non null value.
   * Else for other tree nodes text will contain null.
   *
   * @return
   */
  public String getText() {

    return this.text;
  }

  /**
   * This method will return map of children nodes.
   * 
   * @return
   */
  public Map<String, Node> getChildren() {

    // Do I need to return null if no children exists?
    return this.childeNodeMap;
  }

}
