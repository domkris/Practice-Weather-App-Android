# Practice-Weather-App-Android

This project is an Android application "Weather Info" used as an weather forecast made in Android Studio.<br>
I have been doing this project to learn Android programming and how to make Android applications.

<h3><b>Interface</b></h3>

![promisechains](https://github.com/domkris/files/blob/master/and5.png?raw=true)

<br>
From left to right:<br>
<ul>
  <li>First image shows the user interface when the application is launched, <br>
      in the <i>City selection</i> field user can type in the name of desired city,<br>
      in this case <b>Helsinki</b> is typed in.</li>
  <li>Second picture shows the display when user types in the name of the city and clicks <b>Check City</b> button,<br>
    in this case the weather forecast for Helsinki is shown for today's date and 4 other dates.</li>
  <li>Third picture: User can click on one of displayed dates for a detailed forecast ( in second image ),<br>
   which leads to another display where user can see the weather forecast per every three hours on a specific date.</li>
  <li>Forth picture: if user wants to see the location of the place, he or she can click on the <b>Google Map</b> button ( in second image ),<br>
  user can click the marker on the map to see the name of the city, country code and the temperature</li>
</ul>
<br>
Clicking the back button will lead to previous display.

<h3>Code description</h3>

<h4>Java classes</h4>
<table>
  <tr>
    <th>MainActivity</th>
    <th>DetailedActiviy</th>
    <th>MapsActivity</th>
    <th>MyDBHandler</th>
    <th>MyGrid</th>
    <th>XMLParserJava</th>
  </tr>
  <tr>
    <td>The main activity, created when the application is launched, first image.</td>
    <td>Second activity, created when user clicks on a specific date to see detailed forecast, third image.</td>
    <td>Third activity, shows the location of the place using the <a href=https://developers.google.com/maps/>developers.google.com/maps API</a>, created when user clicks on a Google Map button, forth image.</td>
    <td>This class is used to handle the database (SQLite) of the application that contains descriptions, temperatures and the dates of a selected place, instantiated in the MainActivity class </td>
    <td>This class contains the data for the GridView, it gets the data from the MyDBHandler database, instantiated in the MainActivity class.</td>
    <td>This class is used to read the XML file from the <a href=http://openweathermap.org/api>openweathermap.org/api</a>, in a separate thread, and to save the desired informations from the page into the MyDBHandler database instantiated in the MainActivity class.
    </td>
  </tr>
  <tr>
    <td><b>Layouts:</b><br> activity_main.xml, content_main.xml</td>
    <td><b>Layouts:</b><br> activity_detailed.xml</td>		
    <td><b>Layouts:</b><br> activity_maps.xml</td>
    <td></td>
    <td><b>Layouts:</b><br> grid_single.xml</td>
    <td></td>
  </tr>
</table>
All weather images  in <i>res/drawable</i> are downloaded from <a href=http://openweathermap.org/api>openweathermap.org/api</a>.
<br/>
<h3>Possible improvements</h3>
<ul>
  <li>Instead of Google Map button, the map could be displayed in the main class and contain all of descriptions and images</li>
  <li>Adding a slider to the Google Map that will show the weather decsriptions for a specific date</li>
  <li>Improvement of the user interface (design & colors)</li>
  <li> The size of weather image is automaticaly adjusted according to the device, one of the improvements would be to design images in various (hdpi, xhdpi, etc..) sizes</li>
</ul>
<br/>

