# Research-Web-Applications - Molecular Interaction

Molecular Interaction is an interactive website where you can interact with molecules by rotating and zooming through your hands by using the leap motion.   
This project is a part of theme 10, Research Web-Applications of the study Bioinformatics at Hanze University of Applied Science year 3.


### Requirements
* A Leap Motion Controller
* Download *[Leap Motion software](https://www.techspot.com/downloads/6701-leap-motion.html)*
* Run the downloaded application
* And to fully connect the leap motion, follow the next steps in the video *[leap motion](https://www.youtube.com/watch?v=KlZc9MR13wM&t=114s)* `[1:14  ~ 2:05]`  
* IntelliJ IDEA 2021.2.4 (Ultimate Edition)
* Java 17.0.1 
* Tomcat 9.0.55 or *[Tomcat 9.0.58](https://tomcat.apache.org/)*

### Installation
* Clone this project for own use
* Edit the paths in `web.xml` located in `/src/main/webapp/WEB-INF/web.xml` and make sure you have a `/tmp` directory like the paths in `web.xml`
* Build the `build.gradle`, this is also done automated after the project is loaded into Intellij
* Add Tomcat Server/Local as run configuration under `Run - Edit configurations` and use this address `http://localhost:8080/home`
* Note the Warning: no artifacts marked for deployment. Click `Fix` and select `/Gradle___nl_bioinf_group5___Research_Web_Applications_1_0_SNAPSHOT_war` and fill in `/` instead of `/Gradle___nl_bioinf_group5___Research_Web_Applications_1_0_SNAPSHOT_war`  
For help please visit *[JetBrains](https://www.jetbrains.com/help/idea/run-debug-configuration-tomcat-server.html)*

### Usage
* Run Tomcat which brings you to `http://localhost:8080/home` and have a look at the website
* Plug the leap motion in
* Under `input` choose a pdb file of your choice or visit the `examples`
* Now you can interact with molecule by making hand movements

### Supported Gestures:
* Standard Leap gestures:
  - Swipe (left or right): enable spin
  - Circle (left or right): reset zoom level to initzoom
* Custom gestures:
  - Rotate
    * X-axis: right-hand roll control
    * Y-axis: both hands, pointing upwards as if grabbing a cube
    and rotating it
    * Z-axis: left-hand roll control
  - Zoom
    * Pitch controlled for both hands (but only one at a time)
    
### Acknowledgment
We would like to thank Marcel Kempenaar for helping us to connect the leap motion and for making the javascript that contains the hand gestures to interact with a molecule.
And we would also like to thank Michiel Noback for helping us understand how to make a web application and for helping us make the file upload.

### Authors & Support
Akastia Christo and Rose Hazenberg  
Bioinformatics at Hanze University of Applied Science  
For help or questions you can email to: m.a.christo@st.hanze.nl or c.r.hazenberg@st.hanze.nl