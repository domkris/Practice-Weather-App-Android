# Practice-Weather-App-Android

This project is an Android application "Weather Info" used as an weather forecast made in Android Studio.<br>
I have been doing this project to learn Android programming and how to make Android applications.

<h3><b>Interface</b></h3>

<table style="width:100%">
  <tr>
    <td><a href="http://s1294.photobucket.com/user/DoVBid/media/918d39e9-b7c9-42d9-85e0-5f0aa2db7c18_zpsofvdt2vp.png.html" target="_blank"><img src="http://i1294.photobucket.com/albums/b604/DoVBid/918d39e9-b7c9-42d9-85e0-5f0aa2db7c18_zpsofvdt2vp.png" border="0" alt=" photo 918d39e9-b7c9-42d9-85e0-5f0aa2db7c18_zpsofvdt2vp.png"/></a></td>
    <td><a href="http://s1294.photobucket.com/user/DoVBid/media/2468fa5f-c9a9-4215-a436-42cbe5d2cc3e_zpsc5qoomqf.png.html" target="_blank"><img src="http://i1294.photobucket.com/albums/b604/DoVBid/2468fa5f-c9a9-4215-a436-42cbe5d2cc3e_zpsc5qoomqf.png" border="0" alt=" photo 2468fa5f-c9a9-4215-a436-42cbe5d2cc3e_zpsc5qoomqf.png"/></a></td>		
    <td><a href="http://s1294.photobucket.com/user/DoVBid/media/7116f4b2-1895-4e1a-9bd7-57f7938e1c32_zpsxca7a5nv.png.html" target="_blank"><img src="http://i1294.photobucket.com/albums/b604/DoVBid/7116f4b2-1895-4e1a-9bd7-57f7938e1c32_zpsxca7a5nv.png" border="0" alt=" photo 7116f4b2-1895-4e1a-9bd7-57f7938e1c32_zpsxca7a5nv.png"/></a></td>
    <td><a href="http://s1294.photobucket.com/user/DoVBid/media/065afc33-48ce-4df0-8556-6bcdb638999a_zpsbo3sqstq.png.html" target="_blank"><img src="http://i1294.photobucket.com/albums/b604/DoVBid/065afc33-48ce-4df0-8556-6bcdb638999a_zpsbo3sqstq.png" border="0" alt=" photo 065afc33-48ce-4df0-8556-6bcdb638999a_zpsbo3sqstq.png"/></a></td>
  </tr>
</table>

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

<h3>Code</h3>

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
    <td>This class is used to read the XML file from the <a href=http://openweathermap.org/api>openweathermap.org/api</a>, in a separate thread, and save the desired informations from the page into the MyDBHandler database, instantiated in the MainActivity class.
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

<h4></h4>
