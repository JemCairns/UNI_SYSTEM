# UNI_SYSTEM Running Instructions:

Open a MySQL terminal and run all SQL code within the sql_script.sql file in the Github repository.

Enable Chrome to accept a Self-Signed Certificate on localhost<br>
Open a new Chrome tab.<br>
Enter the following into the address bar: chrome://flags/#allow-insecure-localhost and enable the highlighted option.<br>
Click the three dots in the top-right hand corner of another tab.<br>
Click on Settings.<br>
Under the “Privacy and security” section, click on Manage certificates.<br>
Under the “Your certificates” tab, click the Import button.<br>
Find the uni_system.p12 under the keystore folder in the project.<br>
Enter the password “MyBigHappyKeystore!” into the appropriate field.

Run the web application and navigate to https://localhost:8443/login in your chrome browser.
