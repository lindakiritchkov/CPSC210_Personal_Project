# GardenBuddy

## The ultimate planner for the garden hobbyist!

Plan out where veggies and other plants will go in your garden, get reminders when it's time to plant, track your
watering schedule, and much more!

***

### **User Stories:**

This app is designed for small gardeners and hobby plots.

**PHASE 1**

- As a user, I want to add a vegetable to my garden
- As a user, I want to remove a vegetable from my garden
- As a user, I want to select a vegetable and water it
- As a user, I want to select a vegetable and know when it needs to be watered next
- As a user, I want to see what vegetables I have added to my garden

**PHASE 2**

- As a user, I want to save my garden and created vegetables to file
- As a user, I want to load my garden and created vegetables from a file

***

### **"Why a gardening app?" and my final vision for this project:**

I love gardening. It would be great to plan my plots out before the gardening season. It's helpful to know when I should
begin planting certain plants and to have reminders for when they should be watered.

At the end of this project, I hope to have a beautiful garden visualizer that can give me reminders for planting and
watering, as well as suggesting companion plants for the types of plants I decide to put in my garden this year.

***

### **Phase 4: Task 2**


Tue Mar 29 19:44:16 PDT 2022 - Beet added to garden in position 1.

Tue Mar 29 19:44:17 PDT 2022 - Beet added to garden in position 2.

Tue Mar 29 19:44:17 PDT 2022 - Beet added to garden in position 3.

Tue Mar 29 19:44:18 PDT 2022 - Beet added to garden in position 4.

Tue Mar 29 19:44:21 PDT 2022 - Carrot added to garden in position 5.

Tue Mar 29 19:44:22 PDT 2022 - Carrot added to garden in position 6.

Tue Mar 29 19:44:23 PDT 2022 - Carrot added to garden in position 7.

Tue Mar 29 19:44:23 PDT 2022 - Carrot added to garden in position 8.

Tue Mar 29 19:44:25 PDT 2022 - Vegetable at position 4 removed from garden.

Tue Mar 29 19:44:26 PDT 2022 - Vegetable at position 3 removed from garden.

***

### **Phase 4: Task 3**

Overall, my UML diagram has some... interesting associations.
Both the application classes 
(GardenBuddyConsole/GardenBuddyGUI) and the Garden class are associated
with 1 or more vegetables. The CreateVegetableScreen and ChooseVegetableScreen
are associated with GardenBuddyGUI, but this association is not bidirectional.
GardenBuddyGui is associated with Garden and GardenPlot, and GardenPlot is also
associated with Garden.
If I were to start from scratch, I would make the following changes:
- Make only the Garden class be associated with Vegetable objects, and allow the application classes to access
Vegetable through the Garden.
- Rewrite the program to only call on JsonWriter and JsonReader when performing data persistence related tasks, since
there is no need for JsonWriter or JsonReader to be alive the entire time the application is running.
- Rewrite so that GardenBuddyGui only accesses the Garden through the GardenPlot. This was GardenBuddyGui would only
have an association with GardenPlot (which si already associated to Garden), rather than having
both associations.