package com.school.ui;
import javax.swing.*;
import java.awt.*;
import com.school.model.Student;
import com.school.util.DBManager;
import com.school.util.DataManager;

public class FeeTrackerGUI extends JFrame {
    private DataManager<Student> data = new DataManager<>();
    private JTextField idField, nameField, gradeField, feeField, amountField;
    private JTextArea output;

    public FeeTrackerGUI() {
        setTitle("School Fees Payment Tracker");
        setSize(500,400);
        setLayout(new GridLayout(7,2,5,5));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        idField = new JTextField(); nameField = new JTextField();
        gradeField = new JTextField(); feeField = new JTextField();
        amountField = new JTextField(); output = new JTextArea();

        add(new JLabel("Student ID:")); add(idField);
        add(new JLabel("Name:")); add(nameField);
        add(new JLabel("Grade:")); add(gradeField);
        add(new JLabel("Total Fees:")); add(feeField);

        JButton addBtn = new JButton("Add Student");
        JButton payBtn = new JButton("Pay Fee");
        add(addBtn); add(payBtn);

        add(new JLabel("Amount to Pay:")); add(amountField);
        add(new JScrollPane(output));

        addBtn.addActionListener(e -> {
            try {
                Student s = new Student(Integer.parseInt(idField.getText()),
                        nameField.getText(), gradeField.getText(),
                        Double.parseDouble(feeField.getText()));
                data.add(s);
                DBManager.insertStudent(s.getId(), s.getName(), s.getGrade(),
                                        s.getTotalFees(), s.getFeesPaid());
                output.append("Added: "+s+"\n");
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input");
            }
        });

        payBtn.addActionListener(e -> {
            try {
                int sid = Integer.parseInt(idField.getText());
                double amt = Double.parseDouble(amountField.getText());
                for(Student s : data.getAll()) {
                    if(s.getId()==sid) {
                        s.payFee(amt);
                        DBManager.updateFeesPaid(sid, s.getFeesPaid());
                        output.append("Payment Successful for "+s.getName()+"\n");
                    }
                }
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(this, "Error Processing Payment!");
            }
        });
    }
}
