/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tableFormating;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

/**
 *
 * @author Stefan
 */
public class CaretListenerForDate implements CaretListener
{       
    private JTextField textField;
    private TableDateCellEditor dateEditor;

    public CaretListenerForDate(TableDateCellEditor dateEditor, JTextField textField)
    {
        this.textField = textField;
        this.dateEditor = dateEditor;
    }

    public void caretUpdate(CaretEvent e) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm"); 
        try {
            dateEditor.setCellEditorValue(dateFormat.parse(textField.getText()));
        } catch (ParseException e1) {
            System.err.println(String.format("Worng date format! [%s] Error is [%s]", textField.getText(), e1.getMessage() ));
        }

    }                               
}