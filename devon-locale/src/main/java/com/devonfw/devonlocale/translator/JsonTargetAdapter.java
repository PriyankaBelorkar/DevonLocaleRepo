package com.devonfw.devonlocale.translator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.devonfw.devonlocale.common.Constant;
import com.devonfw.devonlocale.common.Node;

/**
 * This is implementation class for ExtJs.This class converts map<String,Node> to Json format.
 *
 * @author ssarmoka
 */
public class JsonTargetAdapter implements TranslationTarget {

  private StringBuilder startJsonStringBuilder = new StringBuilder("{");

  /**
   * {@inheritDoc}
   */
  public void generateStream(Map<String, Node> root, OutputStream out) {

    System.out.println("Complete json String is -- " + createJsonString(root));

  }

  /**
   * {@inheritDoc}
   */
  public void generateFile(Map<String, Node> root, File out) {

    try {
      if (!out.exists()) {
        out.createNewFile();
      }
      FileWriter fw = new FileWriter(out.getAbsoluteFile());
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write(createJsonString(root).toString());
      bw.close();
    } catch (IOException e) {

      System.out.println("ERROR:: " + e.getMessage());

    }
  }

  /**
   * This methods create json output string.
   *
   * @param root
   * @return
   */
  public StringBuilder createJsonString(Map<String, Node> root) {

    String key;
    Node node;
    Map<String, Node> childMap;
    Iterator<Map.Entry<String, Node>> itr = root.entrySet().iterator();
    while (itr.hasNext()) {
      Map.Entry<String, Node> entry = itr.next();
      key = entry.getKey();
      node = entry.getValue();
      childMap = node.getChildren();
      int counter = 1;
      int leafNodeCounter = 1;
      if (childMap.isEmpty()) {
        // leaf-nodes
        this.startJsonStringBuilder.append("\"" + key + "\" : " + "\"" + node.getText() + "\"");
        if (itr.hasNext()) {
          this.startJsonStringBuilder.append(",");
          counter = 2;
        } else {
          if (counter > 1) {
            counter = 1;
          }
          this.startJsonStringBuilder.append("}");
        }
      } else {
        this.startJsonStringBuilder.append("\"" + key + "\": {").append(Constant.NEW_LINE_CHAR);
        Set<String> newKeySet = childMap.keySet();
        for (String newKey : newKeySet) {
          if (!childMap.get(newKey).getChildren().isEmpty()) {
            counter++;
          } else {
            leafNodeCounter++;
          }
        }

        if (leafNodeCounter > 1 && counter > 1) {
          counter--;
        }
        createJsonString(childMap);
        if (itr.hasNext()) {
          this.startJsonStringBuilder.append(",");
        } else if (!itr.hasNext()) {

          this.startJsonStringBuilder.append("}");

        }

      }
    }
    return this.startJsonStringBuilder;
  }

}
