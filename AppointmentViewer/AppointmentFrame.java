/*
Name: Prabagar Sivakumar
*/
import java.awt.event.*;
import javax.swing.*;
import java.text.*;
import java.util.*;
import javax.swing.border.*;
import java.awt.*;

public class AppointmentFrame extends JFrame
{
   private static final int FRAME_WIDTH = 360, FRAME_HEIGHT = 650;
   private static final int AREA_ROWS = 7, AREA_COLUMNS = 32;
   private int count = 0;

   private JLabel dateLabel, day, month, year, hour, minute;
   private JTextArea appointmentArea, description;
   private JTextField daytxt, monthtxt, yeartxt, hourtxt, minutetxt;
   private JButton prev, next, show, create, cancel;

   Calendar calendar = new GregorianCalendar(2017,2,20);
   SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd, yyyy");

   ArrayList<Appointment> appointments = new ArrayList<Appointment>();
   Border border = BorderFactory.createLineBorder(Color.BLACK);

   public AppointmentFrame() //JFrame of program
   {
      createControlPanel();
      setResizable(false);
      setSize(FRAME_WIDTH, FRAME_HEIGHT);
      appointmentArea.setBorder(border);
      description.setBorder(border);
   }

   private void createControlPanel() //adding all subpanels into mainFrame using GridLayout
   {
     JPanel headerPanel = createHeader(), datePanel = dateSubpanel();
     JPanel actionPanel = actionSubpanel(),descriptionPanel = descriptionSubpanel();
     JPanel controlPanel = new JPanel();
     
     controlPanel.setLayout(new GridLayout(4,1));
     controlPanel.add(headerPanel);
     controlPanel.add(datePanel);
     controlPanel.add(actionPanel);
     controlPanel.add(descriptionPanel);

     add(controlPanel, BorderLayout.NORTH);
   }

   public JPanel createHeader() //dispaying appointment objects and current date
   {
        dateLabel = new JLabel(sdf.format(calendar.getTime())); 
        appointments.add(new Appointment(2017,2,20,11,0,"Dentist Appointment"));
        appointments.add(new Appointment(2017,2,20,18,5,"Study for Midterm"));
        appointmentArea = new JTextArea(AREA_ROWS, AREA_COLUMNS);
        for(Appointment a : appointments){
            appointmentArea.append(a + "\n");
            appointmentArea.setEditable(false);
        }
            
        appointments.add(new Appointment(2017,2,19,8,30,"Drop off sister")); //Several appointments have been added
        appointments.add(new Appointment(2017,2,19,13,0,"Visit cousins house"));
        appointments.add(new Appointment(207,2,19,22,45,"Call dad"));
        appointments.add(new Appointment(2017,2,21,14,30,"Visit bank"));
        appointments.add(new Appointment(2017,2,21,16,50,"Buy mom groceries"));
        appointments.add(new Appointment(2017,2,16,9,00,"Assignment Due"));
        appointments.add(new Appointment(2017,2,23,12,00,"Visit cousin at Eaton Center"));
      
        JPanel panel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(appointmentArea);
        panel.add(dateLabel); panel.add(scrollPane);
        return panel;
   }

   class findAppointmentBefore implements ActionListener //subtracts current date by 1 to show previous date
   {
       public void actionPerformed(ActionEvent event)
       {
           calendar.add(Calendar.DAY_OF_MONTH, -1);
           PrintAppointment();
	   }
    }
   class findAppointmentAfter implements ActionListener //adds current date by 1 to show nexy date
   {
       public void actionPerformed(ActionEvent event)
       {
           calendar.add(Calendar.DAY_OF_MONTH, +1);
           PrintAppointment();
	   }
    }

  class findAppointment implements ActionListener //finds appointments that have the same date 
   {
       public void actionPerformed(ActionEvent event)
       {
           int year = Integer.parseInt(yeartxt.getText()), month = Integer.parseInt(monthtxt.getText());
           month = month - 1;
           int day = Integer.parseInt(daytxt.getText());

           calendar = new GregorianCalendar(year,month,day);
           PrintAppointment();
       }
   }

   class createAppointment implements ActionListener //Creates an appointment based on user input
   {
       public void actionPerformed(ActionEvent event)
       {
           int hour = Integer.parseInt(hourtxt.getText()), minute = Integer.parseInt(minutetxt.getText());
           String descriptionText = description.getText();
           Appointment newTask = new Appointment(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),hour,minute, descriptionText);
           int numTest = 0;
           
           for (int i = 0; i < appointments.size(); i++){ //Checking if appointment already exists
               Appointment task = appointments.get(i);
               String time = new DecimalFormat("00").format(hour) + ":" + new DecimalFormat("00").format(minute);
               if(task.toString().contains(time) && (task.checking(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)) == true)){
                    description.setText("CONFLICT"); 
                    numTest = 1;
                    break;
               }
                else {numTest = 0;}
            } 
           
           if (numTest == 0){ //adds and prints the new appointment since it does not currently exist
            appointments.add(newTask);
            PrintAppointment();
           } 
       }
   }

   class cancelAppointment implements ActionListener
   {
       public void actionPerformed(ActionEvent event)
       {
           int hour = Integer.parseInt(hourtxt.getText()), minute = Integer.parseInt(minutetxt.getText());
           String time = new DecimalFormat("00").format(hour) + ":" + new DecimalFormat("00").format(minute);
           String dayy = new DecimalFormat("00").format(calendar.get(Calendar.DAY_OF_MONTH));
           description.setText(null);
           for (int i = 0; i < appointments.size(); i++) //cancels if an appointment exists
           {
               Appointment task = appointments.get(i);
               String fulltask = time + " " + task.getDescription().toString();
               if(task.toString().matches(fulltask) && (task.checking(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)) == true))
                    appointments.remove(task); 
           } 
           PrintAppointment();
       }
   }

   public JPanel dateSubpanel() //gui for users to see appointments
   {
       prev = new JButton("<");
       ActionListener previous = new findAppointmentBefore();
       prev.addActionListener(previous);
       next = new JButton(">");
       ActionListener nextDate = new findAppointmentAfter();
       next.addActionListener(nextDate);
       day = new JLabel("Day");
       day.setAlignmentX(JLabel.CENTER_ALIGNMENT);
       daytxt = new JTextField(2);
       month = new JLabel("Month");
       monthtxt = new JTextField(2);
       year = new JLabel("Year");
       yeartxt = new JTextField(4);
       show = new JButton("Show");
       ActionListener showDate = new findAppointment();
       show.addActionListener(showDate);

       JPanel panel = new JPanel();
       panel.add(prev); panel.add(next); 
       panel.add(day);panel.add(daytxt);
       panel.add(month);panel.add(monthtxt);
       panel.add(year);panel.add(yeartxt);
       panel.add(show);
       panel.setBorder(new TitledBorder(new EtchedBorder(), "Date"));

       return panel;
       
   }

   public JPanel actionSubpanel() //gui for users to create or cancel an appointment
   {
       hour = new JLabel("Hour");
       hourtxt = new JTextField(2);
       minute = new JLabel("Minute");
       minutetxt = new JTextField(2);
       create = new JButton("Create");
       ActionListener createAppoint = new createAppointment();
       create.addActionListener(createAppoint);
       cancel = new JButton("Cancel");
       ActionListener cancelA = new cancelAppointment();
       cancel.addActionListener(cancelA);
       JPanel panel = new JPanel();
       panel.add(hour); panel.add(hourtxt); 
       panel.add(minute);panel.add(minutetxt);
       panel.add(create);panel.add(cancel);
       panel.setBorder(new TitledBorder(new EtchedBorder(), "Action"));

       return panel;
   }

   public JPanel descriptionSubpanel() //gui for users to write task
   {
      description = new JTextArea(8, 15);

       JPanel panel = new JPanel();
       panel.add(description); 
       panel.setBorder(new TitledBorder(new EtchedBorder(), "Description"));
       panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
       return panel;
   }

   public void PrintAppointment() //prints appointments to the TextArea
   {
       dateLabel.setText(sdf.format(calendar.getTime()));
       appointmentArea.setText(null);
       Collections.sort(appointments);
       for (Appointment temp : appointments) 
            if (temp.checking(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)) == true)
                appointmentArea.append(appointments.get(appointments.indexOf(temp)).toString() + "\n");
   }
}
