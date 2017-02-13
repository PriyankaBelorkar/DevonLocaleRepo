package com.devonfw.devonlocale.translator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.devonfw.devonlocale.common.Constant;
import com.devonfw.devonlocale.common.Node;

/**
 * This is implementation class for ExtJs.This class converts map<String,Node> to Ext js format.
 *
 * @author ssarmoka
 */
public class ExtJsTargetAdapter implements TranslationTarget {

  private StringBuilder prefix = new StringBuilder(Constant.PREFIX_EXTJS);

  private StringBuilder postfix = new StringBuilder(Constant.POSTFIX_EXTJS);

  private StringBuilder startJsStringBuilder = new StringBuilder();

  private StringBuilder endJsStringBuilder = new StringBuilder();

  private StringBuilder completeJsString = new StringBuilder();

  private boolean sibling = false;

  /**
   * {@inheritDoc}
   */
  @Override
  public void generateStream(Map<String, Node> root, OutputStream out) {

    System.out
        .println("Complete js String is -- " + this.prefix.append(createJsString(root, true)).append(this.postfix));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void generateFile(Map<String, Node> root, File out) {

    try {
      if (!out.exists()) {
        out.createNewFile();
      }
      FileWriter fw = new FileWriter(out.getAbsoluteFile());
      BufferedWriter bw = new BufferedWriter(fw);

      bw.write(this.prefix.append(createJsString(root, true)).append(this.postfix).toString());

      bw.close();
    } catch (IOException e) {
      System.out.println("ERROR:: " + e.getMessage());

    }

  }

  /**
   * This methods create extjs output string.
   *
   * @param root This contains content of input properties files in form of Map.
   * @param flag specifies if its called recursively or not.
   * @return string which is in format of JS.
   */
  public StringBuilder createJsString(Map<String, Node> root, boolean flag) {

    String key, childKey;
    Node node, childNode;
    Map<String, Node> childMap;

    Iterator<Map.Entry<String, Node>> itr = root.entrySet().iterator();

    while (itr.hasNext()) {

      Entry<String, Node> entry = itr.next();
      key = entry.getKey();
      node = entry.getValue();
      childMap = node.getChildren();

      if (!childMap.isEmpty()) {
        this.startJsStringBuilder.append(key + ": {").append(Constant.NEW_LINE_CHAR);
        Iterator<Map.Entry<String, Node>> childMapItr = childMap.entrySet().iterator();
        while (childMapItr.hasNext()) {
          Entry<String, Node> childEntry = childMapItr.next();
          childKey = childEntry.getKey();
          childNode = childEntry.getValue();
          if (childNode.getText() != null) {
            // leaf node
            this.startJsStringBuilder.append(childKey + " : " + "\'" + childNode.getText() + "\'");

            childMapItr.remove();

          } else {
            this.startJsStringBuilder.append(childKey + ": {");

            createJsString(childNode.getChildren(), false);
          }

          if (childMapItr.hasNext()) {
            this.startJsStringBuilder.append(",");

          } else {
            this.startJsStringBuilder.append("}");

          }

        }

      } else {
        this.startJsStringBuilder.append(key + " : " + "\'" + node.getText() + "\'");

      }

      if (itr.hasNext()) {

        this.startJsStringBuilder.append(",");

      } else {
        if (!flag)
          this.startJsStringBuilder.append("}");

      }
      itr.remove();
    }

    return this.startJsStringBuilder;
  }

}
