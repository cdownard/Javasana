Javasana
========

Java class to access the Asana API for project management (http://www.asana.com)

Use
========

Javasana depends on the JSONObject class (https://github.com/douglascrockford/JSON-java/blob/master/JSONObject.java and http://www.json.org/javadoc/org/json/JSONObject.html)

The Javasana object implements the methods of accessing the Asana API. The contructor takes your key as an argument to build its basic auth for making API calls.

For example, to list all projects one need only the following:

Javasana asana = new Javasana(yourKeyAsAString);

try {  
&nbsp;&nbsp;&nbsp;&nbsp;JSONObject projects = asana.getProjects();  
&nbsp;&nbsp;&nbsp;&nbsp;// returns a JSON representation of all objects (i.e. {"data":[{"id":654321979546213,"name":"Project number one"},{"id":1234567989465,"name":"Project number two"}]}  
} catch (JSONException e) {  
&nbsp;&nbsp;&nbsp;&nbsp;    // handle your exception  
}  

The error handling may occur within the method in the future. For now it is outside it.