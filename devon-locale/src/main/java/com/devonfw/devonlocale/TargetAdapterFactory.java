package com.devonfw.devonlocale;

import com.devonfw.devonlocale.common.Constant;
import com.devonfw.devonlocale.translator.ExtJsTargetAdapter;
import com.devonfw.devonlocale.translator.JsonTargetAdapter;
import com.devonfw.devonlocale.translator.TranslationTarget;

/**
 * TODO ssarmoka This type ...
 *
 * @author ssarmoka
 */
public class TargetAdapterFactory extends AbstractTargetAdapterFactory {

  /**
   * The constructor.
   */
  public TargetAdapterFactory() {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TranslationTarget getTranslationTarget(String outputFormat) {

    TranslationTarget target = null;
    switch (outputFormat.toLowerCase()) {
    case Constant.EXTJS:
      target = new ExtJsTargetAdapter();
      break;
    case Constant.ANGULAR:
      target = new JsonTargetAdapter();
      break;
    default:
      target = new JsonTargetAdapter();
      break;
    }
    return target;
  }

}
