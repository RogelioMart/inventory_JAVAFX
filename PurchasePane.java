//Assignment from a previous Java class
//  Description: PurchasePane displays a list of available laptops
//  from which a user can select to buy. It also shows how many
//  laptops are selected and what is the total amount.


import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;	//**Need to import to handle event
import javafx.event.EventHandler;	//**Need to import to handle event

import java.util.ArrayList;
import java.text.DecimalFormat;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.geometry.Pos;


//import all other necessary javafx classes
//----

public class PurchasePane extends VBox
{
	
   //GUI components
   private ArrayList<Laptop> laptopList, selectedList, tempArray;

   //laptopLV for top ListView; selectedLV for bottom ListView
   private ListView<Laptop> laptopLV, selectedLV;
   
   public ObservableList<Laptop> laptopData, selectedData;
   
   //used to format numbers that will be displayed
   DecimalFormat fmt = new DecimalFormat("#.00");
   DecimalFormat fmt2 = new DecimalFormat("0.00");

   //declare and initialize variables used in the code
   int numQtyVar = 0;
   Double totalAmtVar = .00;
   int index;
   int addQty = 0;
   Double toAmt = 0.0;
   int i = 0;
   Boolean test = false;
   
   //GUI components and variables
   public Label available = new Label ("Available Laptops");
   public Button pick = new Button("Pick Laptop");
   public Button remove = new Button("Remove Laptop");
   public Label slct = new Label("Selected Laptops");
   public Label qty = new Label("Qty Selected: ");
   public Label total = new Label("Total Amt: ");
   public Label numQty = new Label(Integer.toString(numQtyVar));
   public Label totalAmt = new Label(Double.toString(totalAmtVar)) ; 
   

 //constructor
   public PurchasePane(ArrayList<Laptop> list)
   {
   	  //initialize instance variables
      this.laptopList = list;
      selectedList = new ArrayList<Laptop>();
      tempArray = new ArrayList<Laptop>(); //used to compare to other objects in order to avoid adding duplicate values in the 
       
     //initializes the values needed in order to display inside the listviews
     laptopData = FXCollections.observableArrayList(laptopList);
 	 selectedData = FXCollections.observableArrayList(selectedList);
     laptopLV = new ListView<>(laptopData);
     selectedLV = new ListView<>(selectedData);
 	 laptopLV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
 	 selectedLV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

      //set up the layout
      //sets up the color of the labels
      available.setTextFill(Color.BLUE);
      slct.setTextFill(Color.BLUE);
      qty.setTextFill(Color.BLUE);
      total.setTextFill(Color.BLUE);
      numQty.setTextFill(Color.BLUE);
      totalAmt.setTextFill(Color.BLUE);
      laptopLV.setMinWidth(599);
      selectedLV.setMinWidth(599);
      //makes the gridpane and hboxxes that will be inside the gridpane
      GridPane bigPPane = new GridPane();
      HBox hoBox1 = new HBox(pick, remove);
      HBox subBox1 = new HBox(qty, numQty);
      HBox subBox2 = new HBox(total, totalAmt);
      HBox hoBox2 = new HBox(subBox1, subBox2);
      //sets the position and spacing inside the gridbox
      hoBox1.setAlignment(Pos.CENTER);
      hoBox1.setPadding(new Insets(15,0,0,15));
      hoBox2.setAlignment(Pos.CENTER);
      hoBox1.setSpacing(25);
      hoBox2.setSpacing(50);
      
      //puts all the GUI components in the gridpane in the correct order to later be displayed
      bigPPane.add( available, 0, 0);
      bigPPane.add(laptopLV, 0, 1);
      bigPPane.add(hoBox1, 0, 2);
      bigPPane.add( slct, 0, 3);
      bigPPane.add(selectedLV, 0, 4);
      bigPPane.add(hoBox2, 0, 5);

   	 //PurchasePane is a VBox - add the components here
   	 //adds the gridPane to the vbox in order to be display
      getChildren().addAll(bigPPane);
      
	  //Step #3: Register the button with its handler class
	  //registers each button with its appropriate button handler
      ButtonHandler2 secondHandler = new ButtonHandler2();
      pick.setOnAction(secondHandler);
      remove.setOnAction(secondHandler);
      
   } //end of constructor

 //This method refresh the ListView whenever there's new laptop added in InputPane
 //you will need to update the underline ObservableList object in order for ListView
 //object to show the updated laptop list
 public void updateLaptopList(Laptop newLaptop)
 {
     //adds a new element to laptop data in order to display it on the listview
	 laptopData.add(newLaptop);
	 
	 
 }
 
//Step #2: Create a ButtonHandler class
 private class ButtonHandler2 implements EventHandler<ActionEvent>
 {
  	//Override the abstact method handle()
   public void handle(ActionEvent e)
    {//begins handle(ActionEvent e)
	   
		//When "Pick a Laptop" button is pressed and a laptop is selected from
		//the top list
	   	//creates the source to distinguish the buttons
	   Object source = e.getSource();
	   
	   //initializes local variables or refreshes the variable to be used again
	   test = false;
	   i = 0;
	   toAmt = 0.0;
	   
	   //if the pick Laptop is pressed this if statement will be executed
        if (source == pick)
        {//begins the if statement for the pick button
			//checks if the selected list is empty
    	    if(selectedList.isEmpty())
    	    {//begins if statement for if(selectedList.isEmpty()) 
    	    
    	    	//if empty it will add the selected item from the top listview and input it onto the bottom listview it also updates the quantity and total amounts accordingly
    	    	selectedData = laptopLV.getSelectionModel().getSelectedItems();
    	     	selectedList.addAll(selectedData);
    	     	selectedLV.getItems().addAll(selectedData);
    	     	totalAmtVar = selectedList.get(0).getPrice();
    	     	numQtyVar = numQtyVar + 1;
    	    	
    	    }//ends if statement for if(selectedList.isEmpty())
    	    
    	    //if the selected list is not empty this else statement will be executed
    	    else
    	    {//begins the else statement 
    	    	
    	    	//gets the selected items from laptopLV and puts them in selectedData
    	    	selectedData = laptopLV.getSelectionModel().getSelectedItems();
    	    	tempArray.addAll(selectedData);
    	    	
    	    	//the while loop looks for a duplicate in the selectedList
    	    	while(i < (selectedList.size()))
    	    	{//while statement for comparing tempArray and selectedList ends here
    	    		
    	    		//if a duplicate is found the loop will be exited and test will be set to true
    	    		//if a duplicate is not found the program will continue add the items to the bottom lisview and update the quantity and total accordingly
    	    		if(selectedList.get(i).equals(tempArray.get(0)))
    	    		{//begins if statement inside while loop
    	    			
    	    			test = true;
    	    			i = selectedList.size() + 3;
    	    			
    	    		}//ends if statement inside while loop
    	    		
    	    		i++;
    	    		
    	    	}//while statement for comparing tempArray and selectedList ends here
    	    	
    	    	//a duplicate has not been found this statement will be to update the bottom listview, quantity, and total amount accordingly
    	    	if(test != true)
    	    	{//if statement for if(test != true) begins here
    	    		
    	    		selectedList.addAll(selectedData);
    	    		selectedLV.getItems().addAll(selectedData);
    	    		toAmt = tempArray.get(0).getPrice();
    	    		totalAmtVar = totalAmtVar + toAmt;
    	    		numQtyVar = numQtyVar + 1;
    	    		
    	    	}//if statement for if(test != true) ends here 
    	    	
    	    	//tempArray is cleared to be able to add new values into for comparing without having to worry about any previous value that was inputted into tempArray
    	    	tempArray.clear();
    	    	
    	    }//else statement ends here
      
        }//ends the if statement for the pick button
        
        //when "Remove a Laptop" button is pushed and a laptop is selected from
        //the bottom list
        //this statement is executed when the "Remove a Laptop" button is pushed
        else if (source == remove)
         {//begins the else if statement
        	
        	//used to get the appropriate value of the item selected in the bottom list view and update the bottom listview, quantity, and total appropriately 
        	int indice = selectedLV.getSelectionModel().getSelectedIndex(); 
        	toAmt = selectedList.get(indice).getPrice();
        	totalAmtVar = totalAmtVar - toAmt;
        	numQtyVar = numQtyVar - 1;
        	selectedList.remove(indice);
        	selectedLV.getItems().clear();
        	selectedLV.getItems().addAll(selectedList);
        	
         }//ends the else if statement
         

         else //for all other cases
		 {
		 	 //All invalid action, do nothing here;
	     }
      	 //update the QtySelect and AmtSelect labels
        //updates the labels that display the quantity and total amount
        totalAmt.setText((fmt.format(totalAmtVar)));
        numQty.setText(Integer.toString(numQtyVar));
        
       }//ends handle(ActionEvent e)
   
   } //end of ButtonHandler class

} //end of PurchasePane class