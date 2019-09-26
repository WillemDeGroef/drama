package be.ac.kuleuven.cs.drama.gui.codecoloring;

import java.awt.Color;
import java.util.Hashtable;

import be.ac.kuleuven.cs.drama.gui.editor.ColorLineTextPane;

public class TextDecorator {

    private final ColorLineTextPane textPane;
    private final IdeColorSet colorSet;

    private final Hashtable baseTable;

    public TextDecorator(ColorLineTextPane textPane, IdeColorSet colorSet) {
        this.textPane = textPane;
        this.colorSet = colorSet;

        textPane.setBackground(colorSet.backCol);
        textPane.setForeground(colorSet.normalCol);
        textPane.setCaretColor(colorSet.normalCol);

        this.baseTable = new Hashtable(55);

        this.baseTable.put("AFT", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("BIG", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("DEL", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("DRS", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("DRU", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("HIA", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("HIB", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("KTG", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("KTO", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("LEZ", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("MKH", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("MKL", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("MOD", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("NTS", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("NWL", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("OND", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("OPT", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("SBR", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("SGI", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("SGU", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("SPR", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("STP", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("TSM", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("TSO", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("VER", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("VGL", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("VSP", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("HST", new IdeTableElement(this.colorSet.commandCol, true));
        this.baseTable.put("BST", new IdeTableElement(this.colorSet.commandCol, true));

        this.baseTable.put("MEVA", new IdeTableElement(this.colorSet.preprocessorCol, true));
        this.baseTable.put("MNTS", new IdeTableElement(this.colorSet.preprocessorCol, true));
        this.baseTable.put("MSPR", new IdeTableElement(this.colorSet.preprocessorCol, true));
        this.baseTable.put("MVGL", new IdeTableElement(this.colorSet.preprocessorCol, true));
        this.baseTable.put("MVSP", new IdeTableElement(this.colorSet.preprocessorCol, true));
        this.baseTable.put("MFOUT", new IdeTableElement(this.colorSet.preprocessorCol, true));

        this.baseTable.put("R0", new IdeTableElement(this.colorSet.registerCol, true));
        this.baseTable.put("R1", new IdeTableElement(this.colorSet.registerCol, true));
        this.baseTable.put("R2", new IdeTableElement(this.colorSet.registerCol, true));
        this.baseTable.put("R3", new IdeTableElement(this.colorSet.registerCol, true));
        this.baseTable.put("R4", new IdeTableElement(this.colorSet.registerCol, true));
        this.baseTable.put("R5", new IdeTableElement(this.colorSet.registerCol, true));
        this.baseTable.put("R6", new IdeTableElement(this.colorSet.registerCol, true));
        this.baseTable.put("R7", new IdeTableElement(this.colorSet.registerCol, true));
        this.baseTable.put("R8", new IdeTableElement(this.colorSet.registerCol, true));
        this.baseTable.put("R9", new IdeTableElement(this.colorSet.registerCol, true));

        this.baseTable.put("KL", new IdeTableElement(this.colorSet.comparisonCol, true));
        this.baseTable.put("GR", new IdeTableElement(this.colorSet.comparisonCol, true));
        this.baseTable.put("GEL", new IdeTableElement(this.colorSet.comparisonCol, true));
        this.baseTable.put("KLG", new IdeTableElement(this.colorSet.comparisonCol, true));
        this.baseTable.put("GRG", new IdeTableElement(this.colorSet.comparisonCol, true));
        this.baseTable.put("NGEL", new IdeTableElement(this.colorSet.comparisonCol, true));
        this.baseTable.put("SO", new IdeTableElement(this.colorSet.comparisonCol, true));
        this.baseTable.put("GSO", new IdeTableElement(this.colorSet.comparisonCol, true));
        this.baseTable.put("OVL", new IdeTableElement(this.colorSet.comparisonCol, true));
        this.baseTable.put("GOVL", new IdeTableElement(this.colorSet.comparisonCol, true));

        this.baseTable.put("EINDPR", new IdeTableElement(this.colorSet.keywordCol, true));
        this.baseTable.put("RESGR", new IdeTableElement(this.colorSet.keywordCol, true));
        this.baseTable.put("MACRO", new IdeTableElement(this.colorSet.keywordCol, true, new IdeState(IdeState.STATE_MACRO_HEADER)));
        this.baseTable.put("MCREINDE", new IdeTableElement(this.colorSet.keywordCol, true));

        this.decorate();
    }

    public void decorate() {
        this.colorPane();
    }

    private void colorPane() {
        if (this.colorSet.id == 0) {
            this.textPane.setBackgroundColor(this.colorSet.backCol, 0, this.textPane.getText().length());
            this.textPane.setForegroundColor(this.colorSet.normalCol, 0, this.textPane.getText().length());
            return;
        }

        IdeState cState = IdeState.getDefault().copy();
        Hashtable table = (Hashtable) this.baseTable.clone();

        String text = this.textPane.getText();
        int p = 0;
        int l = text.length();
        int e;

        while (p < l) {
            e = text.indexOf('\n', p);
            if (e < 0)
                e = l;

            boolean isNumber = false;
            IdeTableElement cElement = null;
            Color ccol = this.colorSet.normalCol;
            for (int i = p; i <= e; i++) {
                String substr = text.substring(p, i).toUpperCase();
                if (isNumber = (p < i && text.charAt(i - 1) <= '9' && text.charAt(i - 1) >= '0' && (isNumber || p + 1 == i)))
                    ccol = this.colorSet.numberCol;
                else {
                    Object val = table.get(substr);
                    if (val != null) {
                        cElement = (IdeTableElement) val;
                        ccol = cElement.getColor();
                    } else {
                        cElement = null;
                        ccol = this.colorSet.normalCol;
                    }
                }
                if (i < l) {
                    if (text.charAt(i) == '|') {
                        this.textPane.setForegroundColor(ccol, p, i - p);
                        this.textPane.setForegroundColor(this.colorSet.commentCol, i, e - i);
                        break;
                    }
                    if (text.charAt(i) == '"') {
                        int j = i;
                        while (++j < e) {
                            if (text.charAt(j) == '\\') {
                                j++;
                                continue;
                            }
                            if (text.charAt(j) == '"') {
                                j++;
                                break;
                            }
                        }
                        this.textPane.setForegroundColor(ccol, p, i - p);
                        this.textPane.setForegroundColor(this.colorSet.stringCol, i, j - i);
                        i = j;
                        p = i;
                        continue;
                    }
                }
                if (i == e || " \t\r,.()<>+-*;".indexOf(text.charAt(i)) >= 0) {
                    if (cState.getState() == IdeState.STATE_MACRO_HEADER && i != p) {
                        cElement = null;
                        ccol = this.colorSet.selfDefinedCol;
                        table.put(text.substring(p, i), new IdeTableElement(ccol, false));
                        cState.apply(IdeState.getDefault());
                    } else
                        cState.apply(cElement == null ? IdeState.getUnchanged() : cElement.getNewState());
                    this.textPane.setForegroundColor(ccol, p, i - p);
                    cElement = null;
                    ccol = this.colorSet.normalCol;
                    this.textPane.setForegroundColor(ccol, i, 1);
                    p = i + 1;
                }
            }

            p = e + 1;
        }
        this.textPane.setBackgroundColor(this.textPane.getBackground(), 0, text.length());
    }
}
