## General Information/Instructions:
- Clone the project
- Download soapUI
- Download baretail -> https://www.baremetalsoft.com/baretail (optional)
- Microsoft SQL Server 2019 is required
- Download apache karaf 4.3.6 -> https://karaf.apache.org/archives.html
- Go to your apache-karaf-4.3.6 folder, create a folder named "repo" (optional)
- There is a file called "myBank-1.0.0.kar" at the project root. Copy it to the repo folder on apache-karaf-4.3.6 (or at the root folder if you skiped the previous step)
- Create a database named "my_bank" (I like using heidisql or Microsoft SQL Server management studio)
- In case you want to see the logs: Open baretail -> open -> apache-karaf-4.3.6\data\log\karaf.log (optional)
- Got to apache-karaf-4.3.6\bin and open the cmd. write "karaf" and press enter
- After it opens, use the following command: kar:install file:repo/myBank-1.0.0.kar (note that im using the repo folder). This will install some dependecies needed.
- Open the cloned project, and compile the currentAccount module. (I use intelliJ as IDE).
- While its compiling it will generate classes using the openapi-generator plugin and the cxf-codegen-plugin.
- After that, go to karaf and enter the command: "feature:repo-add mvn:org.mybank/currentAccount-feature/1.0.0-SNAPSHOT/xml/features;"
- Then use the command "feature:install currentAccount-feature". It should install the feature containing the 4 bundles and create the datasource at "apache-karaf-4.3.6\etc" folder. There should be a org.ops4j.datasource-my_bank_ds.cfg file now. Open it and change the username and password. You can use the command "list" to see the state of the bundles. since it is needed to change the user and pass it should be on failure state. after changing the user and pass. using the command "restart" on the bundle ID of the config and persistence bundle should make it work. otherwise quit and start karaf again. Note that just in case something goes wrong and the DS is not created, it can be found on the config module -> META-INF. 
- After doing this, a table will be created in the my_bank database since I used liquibase to do that. and it should be populated with some data already. All the bundles should be now active too.
- ![image](https://github.com/Narayami/my_bank/assets/43100019/c144a601-1854-4d61-808f-e7ccaea27e71)
  
- With all setted up, you can go to http://localhost:8181/cxf/ to see the web-services available:
- ![image](https://github.com/Narayami/my_bank/assets/43100019/5df7c7ee-ded3-48f1-9368-d715f9fb331b)

-For soap services click on the WSDL link, dont forget to change the 0.0.0.0 to localhost. and copy the address. Should be http://localhost:8080/currentAccount?wsdl
-For rest services click on the WADL link and copy it. should be http://localhost:8181/cxf/currentAccount?_wadl
-Open soapUI. Click on SOAP and paste the link on "initial WSDL". And for rest click on rest, then "import WADL" and paste the link.
-It will create the project for rest/soap. In case of soap the request fields are already there by default and just need to fill the values. In case of rest you can use https://editor.swagger.io/ and paste the yaml content to see the fields/objects required. Note that I also have examples on the impl module -> resources -> rest-requests-example.
-Rest should be like this:
 ![image](https://github.com/Narayami/my_bank/assets/43100019/089dfa81-8b3b-40a1-a73b-3fc2d581bb92)

 Soap should be like this:
![image](https://github.com/Narayami/my_bank/assets/43100019/f890f996-f8eb-4f5e-8a95-fb3dfd93bb12)

-All done, you are free to perform the operations available for both rest and soap!

