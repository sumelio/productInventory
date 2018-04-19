This project implements  the following requirements:

# productInventory

You must only use the following technologies/frameworks: HTML, CSS, JQuery/Javascript, AngularJS,
Bootstrap, Java, Spring, Vaadin and the database of your preference. You are going to
be graded in a scale from 0 - 10 according to these criteria:
  * Good practices.
  * Quality of your code.
  * Documentation.
  * Decisions in the development process.
  * Performance of your application
## Exercise​ ​1​ ​Inventory

   You have been hired for developing a system for managing inventory, with the
   following specifications:
  * There are 2 profiles in the system, User who is capable of consulting the inventory and its products, Amin who is able to modify existences.
  * The product information for both updating and showing, is: Name, Description, Type, Unit Price
  * The items must be classified according to its type: Food, Technology, forniture.
  * There must be a main window where all the items must be shown and being able to be filtered by type.
  * The platform must be responsive and needs to work and see great on Mobile, Desktop and Tablets.

## Admin​ ​User:
  * There must be an option in the main window for adding a new item.
  * There must be an option to remove an item from the inventory because it was used or was sold.
  * There must be an option for editing an item.
  * When a new item is added a notification message in the main screen must be shown indicating that a category was updated and the total items in stock.
  This applies for all the users that are in the site.
  * **User**: demomanager
    **Password**: d3m0manag3r

## User:
  * The user can see the current status of the inventory, but cannot modify or add any item.

  * **User**: demouser
  * **Password**: d3m0us3r
  * The site must be multilanguage at least for english and spanish.
  
## Exercise​ ​2​ ​Massive​ ​load​ ​of​ ​products
Now that your system is in production, your are asked to create a massive load of
products where your application will have to upload a file with the following columns.
Please take into account the same restrictions from part 1.
  * Name
  * Description
  * Type
  * Unit price

And it needs to store all the information in your database.

Files will have at maximum 500 thousand records. Please consider that your system is
able to process up to three file at the time and this number must be parameterizable
inside your application.



Application URL

http://ec2-52-14-66-207.us-east-2.compute.amazonaws.com:8080/telintel-inventory-web/loginPage

