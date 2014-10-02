/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/vertalerpack/macro/VariableTable.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.vertalerpack.macro;

import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

/**
 * Table of key value pairs that are keys.
 * A table can have a parent table that is
 * consulted if a key cannot be resolved.
 *
 * @version 1.0.0 08/29/2000
 * @author  Tom Schrijvers
 */

public class VariableTable {

   private final VariableTable _parent;
   private final Map _map;

   /**
    * Initialize.
    *
    * @parent parent the table to query if this one does not define a key, null if none
    */

   public VariableTable(VariableTable parent) {
      _parent = parent;
      _map = new HashMap();
   }

   /**
    * Initialize without parent.
    */
   public VariableTable() {
      this(null);
   }

   /**
    * Initialize.
    *
    * @param keys the initial keys in the table
    * @param values the initial values in the table
    * @parent parent the table to query if this one does not define a key, null if none
    */
   public VariableTable(List keys, List values, VariableTable parent) {
      this(parent);

      for (int i = 0; i < keys.size(); i++) {
         putPrimitive((String) keys.get(i), (String) values.get(i));
      }

   }

   /**
    * @return the value for given key
    */
   public String get(String key) {
      if (containsPrimitive(key)) {
         return getPrimitive(key);
      } else if (_parent != null) {
         return _parent.get(key);
      } else {
         return null;
      }

   }

   public void put(MacroVariable key, String value) {
      put(key.getName(), value);
   }

   /**
    * Put the given key value pair in the table.
    * If the key is defined in the table or 
    * it is not defined in the parent table 
    * the pair is put in this table. Otherwise it
    * is put in the parent table.
    */
   public void put(String key, String value) {
      if (containsPrimitive(key)) {
         putPrimitive(key, value);
      } else if (_parent != null && _parent.contains(key)) {
         _parent.put(key, value);
      } else {
         putPrimitive(key, value);
      }

   }

   private void putPrimitive(String key, String value) {
      _map.put(key, value);
   }

   private String getPrimitive(String key) {
      return (String) _map.get(key);
   }

   /**
    * @return wether this table or its parent contains the given key
    */
   public boolean contains(String key) {
      return containsPrimitive(key) || ( _parent != null && _parent.contains(key));
   }

   private boolean containsPrimitive(String key) {
      return _map.containsKey(key);
   }



}
