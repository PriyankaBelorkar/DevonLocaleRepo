package com.devonfw.devonlocale;

import com.devonfw.devonlocale.translator.TranslationTarget;

/**
 * Abstract class for target adapter factory.
 *
 * @author ssarmoka
 */
public abstract class AbstractTargetAdapterFactory {

  /**
   * @param outputFormat
   * @return
   */
  public abstract TranslationTarget getTranslationTarget(String outputFormat);
}
