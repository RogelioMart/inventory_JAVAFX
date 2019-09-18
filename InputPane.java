// Assignment from Java Class
// Description: InputPane generates a pane where a user can enter
//  a laptop information and create a list of available laptops.

//All necessary imports
import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent; 
import javafx.event.EventHandler; 
import javafx.geometry.Insets;
import javafx.scene.layout.Region;


public class InputPane extends HBox
{
	//variables
	int i = 0;
	int check = 0;
	
	//GUI components
   private ArrayList<Laptop> laptopList;
   public Label lBrand;
   public Label lModel;
   public Label lCPU;
   public Label lRAM;
   public Label lPrice;
   public Label errField;
   public TextField tfBrand;
   public TextField tfModel;
   public TextField tfCPU;
   public TextField tfRAM;
   public TextField tfPrice;
   public Button enter;
   public TextArea dispLaptop;
   //The relationship between InputPane and PurchasePane is Aggregation
   private PurchasePane purchasePane;
   //----
   private Assignment6 assignment6;
   
   DecimalFormat pricey = new DecimalFormat("0.00");
   DecimalFormat dot1 = new DecimalFormat("0.0");

	//constructor
	public InputPane(ArrayList<Laptop> list, PurchasePane pPane)
	{
		this.laptopList = list;
		this.purchasePane = pPane;

		//Step #1: initialize each instance variable and sets up the layout
		//set ups each label
		lBrand = new Label ("Brand");
		lModel = new Label ("Model");
		lCPU = new Label ("CPU(GHz)");
		lCPU.setMinWidth(Region.USE_PREF_SIZE);
		lRAM = new Label ("RAM(GB)");
		lPrice  = new Label ("Price($)");
		errField = new Label("");
		errField.setMinWidth(Region.USE_PREF_SIZE);
		
		//set up a text field for for each label
		tfBrand = new TextField();
		tfModel = new TextField();
		tfCPU = new TextField();
		tfRAM = new TextField();
		tfPrice = new TextField();
		tfPrice.setMinWidth(150);
		
		//Made a button so that the user can enter all his information
		enter = new Button("Enter a Laptop Info.");
		
		//text field to display the laptop specs that the user enters
		dispLaptop = new TextArea();
		dispLaptop.setPromptText("No laptops");
		dispLaptop.setPrefColumnCount(40);
		dispLaptop.setPrefRowCount(3);
		

		//create a GridPane hold those labels & text fields
		//consider using .setPadding() or setHgap(), setVgap()
		//to control the spacing and gap, etc.
		
		//create the grid pan for input
		GridPane iPane = new GridPane();
		GridPane lilPane = new GridPane();
		iPane.setHgap(8);//spacing between labels and text fields horizontally
		iPane.setVgap(8);//spacing between labels vertically
		                          //(top,right,bottom,left)
		lilPane.setPadding(new Insets(40,0,0,30)); //using for padding
		lilPane.add(errField, 0, 0);
		lilPane.add(iPane, 0 ,1);
		iPane.add(lBrand, 0,1);
		iPane.add(lModel, 0, 2);
		iPane.add(lCPU, 0, 3);
		iPane.add(lRAM, 0, 4);
		iPane.add(lPrice, 0,5);
		iPane.add(tfBrand, 1,1);
		iPane.add(tfModel, 1, 2);
		iPane.add(tfCPU, 1, 3);
		iPane.add(tfRAM, 1, 4);
		iPane.add(tfPrice, 1,5);	
		iPane.add(enter,0,6,2,1);
		
   		//Set up the layout for the left half of the InputPane.
		//sets the padding and adds text area to textpane
		HBox textPane = new HBox(15);
		textPane.setPadding(new Insets(20,20,20,20));
		textPane.getChildren().addAll(dispLaptop);
		
		//adds teh gridPane lilPane to the Vbox
   		VBox vbox = new VBox();
   		vbox.getChildren().addAll(lilPane);  		

   		//adds all the GUI components to the the HBox to be displayed
   		getChildren().addAll(vbox, textPane);
   		
 	  //Step #3: register source object with event handler
   		ButtonHandler someHandler = new ButtonHandler();
   		enter.setOnAction(someHandler);
   		
	} //end of constructor

  //Step #2: Create a ButtonHandler class
  //ButtonHandler listens to see if the button "Enter a Laptop Info." is pushed or not,
  //When the event occurs, it get a laptop's brand, model, CPU, RAM and price
  //information from the relevant text fields, then create a new Laptop object and add it inside
  //the laptopList. Meanwhile it will display the laptop's information inside the text area.
  //It also does error checking in case any of the textfields are empty or wrong data was entered.
   private class ButtonHandler implements EventHandler<ActionEvent>
   	 {
	  
   	    //Override the abstact method handle()
   	    public void handle(ActionEvent d)
        {
			//declare any necessary local variables here
			//updates the label field
   	    	errField.setText("");

			//checks if all the textfields are empty
			if ( (tfBrand.getText().trim().isEmpty())  || (tfModel.getText().trim().isEmpty()) || (tfCPU.getText().trim().isEmpty()) || (tfRAM.getText().trim().isEmpty()) || (tfPrice.getText().trim().isEmpty()) )
			{//begins the if statement to find the empty one
				
				//if any textfields are empty the following message will be displayed				
				errField.setTextFill(Color.RED);
				errField.setText("Please fill in all the Fields");

			}//ends the if statement to find the empty one
			
			else//for all other cases
         	{
				
				//try statement begins here		
				try 
				{
					//creates a new laptop object
					Laptop laptop = new Laptop(tfBrand.getText(), tfModel.getText(), Double.parseDouble(tfCPU.getText()), Double.parseDouble(tfRAM.getText()), Double.parseDouble(tfPrice.getText()));
					
					//if statement to check if the laptopList is empty
					if(!(laptopList.isEmpty())) 
					{//if statement begins here
						
						//temporary variables to compare the laptop objects						
						String tBrand = tfBrand.getText();
						String tModel = tfModel.getText();
						Double tCPU = Double.parseDouble(tfCPU.getText());
						Double tRAM = Double.parseDouble(tfRAM.getText());
						
						String aBrand;
						String aModel;
						Double aCPU;
						Double aRAM;
						
						while(i < laptopList.size() )
						{//while statement begins here
							
							//Initializes the temporary variables to compare them
							aBrand = laptopList.get(i).getBrand();
							aModel = laptopList.get(i).getModel();
							aCPU = Double.parseDouble(laptopList.get(i).getCPU()+"");
							aRAM = Double.parseDouble(laptopList.get(i).getRAM()+"");
							
							
							if((tBrand.equals(aBrand)) && (tModel.equals(aModel)) && (tCPU.equals(aCPU)) && (tRAM.equals(aRAM)))
							{
								
								check = 1;
								i = laptopList.size() + 1;
								
							}
							
							i++;
						}//while statement ends here
						
						if (check != 1) 
						{//begins the if check != 1 
							
							//inputs the new laptop into the text area and update laptopList
							laptopList.add(laptop);
							dispLaptop.appendText("Brand:\t" + tfBrand.getText() + "\nModel:\t" + tfModel.getText() + "\nCPU:\t\t" + dot1.format(Double.parseDouble(tfCPU.getText())) + "\nRAM:\t" + dot1.format(Double.parseDouble(tfRAM.getText())) + "\nPrice:\t" + "$" + pricey.format(Double.parseDouble(tfPrice.getText())) + "\n\n");
							errField.setTextFill(Color.BLACK);
							errField.setText("Laptop added");
							purchasePane.updateLaptopList(laptop);
							
						}//ends the if check != 1 
						
					}//ends if statement for the repeating laptopList error
					
					else
					{//begins the else statement
						
						//adds a laptop to the adds a laptop to the laptopList
						laptopList.add(laptop);
						dispLaptop.appendText("Brand:\t" + tfBrand.getText() + "\nModel:\t" + tfModel.getText() + "\nCPU:\t\t" + dot1.format(Double.parseDouble(tfCPU.getText())) + "\nRAM:\t" + dot1.format(Double.parseDouble(tfRAM.getText())) + "\nPrice:\t" + "$" + pricey.format(Double.parseDouble(tfPrice.getText())) + "\n\n");
						errField.setTextFill(Color.BLACK);
						errField.setText("Laptop added");
						purchasePane.updateLaptopList(laptop);
						
					}//ends the else statement
					
					if(check == 1)
					{//begins check == 1 if statement
						//if there is a duplicate the label will display the following message
						errField.setTextFill(Color.RED);
						errField.setText("Laptop not added - duplicate");
						check = 0;
					}//ends check == 1 if statement
					
				}//try statement ends here
				
				catch(NumberFormatException nfe)
				{//catch begins here
					
					errField.setTextFill(Color.RED);
					errField.setText("Incorrect data format");
					
				}//catch ends here
				
				
            }
      } //end of handle() method
   } //end of ButtonHandler class  

	
}