/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/MacroCall.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

import be.ac.kuleuven.cs.drama.exception.AbnormalTerminationException;
import be.ac.kuleuven.cs.drama.vertalerpack.vertaler.StringUtils;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * MacroLine that represents the call 
 * to a macro.
 * 
 * @version 1.0.0 08/31/2000
 * @author  Tom Schrijvers
 */

public class MacroCall extends MacroLine {

   private final MacroDefinition _callee;
   private List _actualParameters;

   public MacroCall(MacroDefinition callee) {
      _callee = callee;
   }

   public void init(String parameters, MacroDefinition context)
   throws AbnormalTerminationException {
      _actualParameters = parseActualParameters(parameters, context);
      Iterator it = _actualParameters.iterator();

      while (it.hasNext()) {
         setAllLabelsPrintable((Printable) it.next());
      }

   }

   private List parseActualParameters(String params, MacroDefinition context)
   throws AbnormalTerminationException {
      StringTokenizer tokenizer = new StringTokenizer(params, ",");
      List formalParams = new ArrayList();

      while (tokenizer.hasMoreTokens()) {
         String param = StringUtils.trimSpaces(tokenizer.nextToken());
         formalParams.add(getExpression(param, context));
      }

      return formalParams;
   }


   protected String outString(MacroExpander expander) {
      return "";
   }

   protected void expandImpl(MacroExpander expander)
   throws AbnormalTerminationException {
      List actualParams = new ArrayList();
      Iterator it = _actualParameters.iterator();

      while (it.hasNext()) {
         actualParams.add(((Printable) it.next()).toString(expander));
      }

      expander.setLineNo(_callee.expand(expander.out(), actualParams, expander.getLineNo()));
   }

}
